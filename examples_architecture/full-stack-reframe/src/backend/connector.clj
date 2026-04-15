(ns backend.connector
  (:require [io.pedestal.connector :as connector]
            [io.pedestal.connector.dev :as dev]
            [io.pedestal.environment :as env]
            [io.pedestal.http.http-kit :as hk]
            [io.pedestal.service.resources :as resources]
            [io.pedestal.log :as log]
            [backend.routes :as routes]))

(defn- log-startup [cm]
  (log/info :msg "Full-stack-reframe server started" :port (:port cm))
  cm)

(defn connector-map
  [{:keys [dev-mode? join?]
    :or   {dev-mode? env/dev-mode?
           join?     false}}]
  (-> (connector/default-connector-map 8080)
      (cond->
        join?     (assoc :join? true)
        dev-mode? (connector/with-interceptors dev/dev-interceptors))
      connector/with-default-interceptors
      (connector/with-routes
        (resources/resource-routes {:resource-root "public"})
        (routes/routes))))

(defn start []
  (-> (connector-map {:join? true})
      log-startup
      (hk/create-connector nil)
      connector/start!))
