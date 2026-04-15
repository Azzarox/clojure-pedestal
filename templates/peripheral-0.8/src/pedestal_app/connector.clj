(ns pedestal-app.connector
  (:require [io.pedestal.connector :as connector]
            [io.pedestal.service.resources :as resources]
            [io.pedestal.environment :as env]
            [io.pedestal.connector.dev :as dev]
            [pedestal-app.routes :as routes]))

(defn connector-map
  "Creates a connector map for the pedestal-app service.

  Options:
  - dev-mode?: enables dev-interceptors and interceptor logging if true, defaults from
    Pedestal's development mode.
  - join?: if true, then the current thread will block when the connector is started (default is false)."
  [opts]
  (let [{:keys [dev-mode? join?]
         :or {dev-mode? env/dev-mode?
              join? false}} opts]
    (-> (connector/default-connector-map 8080)
        (cond->
          join? (assoc :join? true)
          dev-mode? (connector/with-interceptors dev/dev-interceptors))
        connector/with-default-interceptors
        (connector/with-routes
          (routes/routes)
          (resources/resource-routes {:resource-root "public"})))))
