(ns pedestal-app.main
  (:require [io.pedestal.connector :as connector]
            [io.pedestal.log :as log]
            [io.pedestal.http.http-kit :as hk]
            [pedestal-app.connector :refer [connector-map]]))

(defn- log-startup
  [connector-map]
  (log/info :msg "pedestal-app startup" :port (:port connector-map))
  connector-map)

(defn start-service
  [_]
  (-> (connector-map {:join? true})
      log-startup
      (hk/create-connector nil)
      connector/start!))

(defn -main [& _args]
  (start-service nil))
