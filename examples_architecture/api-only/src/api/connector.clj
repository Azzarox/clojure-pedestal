(ns api.connector
  (:require [io.pedestal.connector :as connector]
            [io.pedestal.connector.dev :as dev]
            [io.pedestal.environment :as env]
            [io.pedestal.http.http-kit :as hk]
            [io.pedestal.log :as log]
            [api.routes :as routes]))

;; No resource-routes here — we only serve API endpoints
;; Frontend is a separate app on a different port/server
;; That's why handlers manually set Access-Control-Allow-Origin headers

(defn- log-startup [cm]
  (log/info :msg "API-only server started" :port (:port cm))
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
      (connector/with-routes (routes/routes))))

(defn start []
  (-> (connector-map {:join? true})
      log-startup
      (hk/create-connector nil)
      connector/start!))
