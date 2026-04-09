(ns hello
  (:require
   [io.pedestal.connector :as conn]
   [io.pedestal.http.http-kit :as hk]
   [routes :refer [routes]]))

(def port 8890)

(defn create-connector
  []
  (-> (conn/default-connector-map port)
      (conn/with-default-interceptors)
      (conn/with-routes routes)
      (hk/create-connector nil)))

(defonce *connector (atom nil))

(defn start
  []
  (println "Server is starting...")
  (reset! *connector
          (conn/start! (create-connector)))
  (println "Server is listening on PORT:" port))

(defn stop
  []
  (println "Server is stopping...")
  (conn/stop! @*connector)
  (reset! *connector nil)
  (println "Server stopped!"))

(defn restart
  []
  (println "Server is restarting...")
  (when @*connector
    (stop))
  (start)
  (println "Server is ready!"))