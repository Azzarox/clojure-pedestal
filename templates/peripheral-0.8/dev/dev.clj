(ns dev
  (:require [io.pedestal.connector :as conn]
            [io.pedestal.http.http-kit :as hk]
            [pedestal-app.connector :refer [connector-map]]))

(defonce *connector (atom nil))

(defn init
  "Constructs the current Pedestal development connector."
  []
  (reset! *connector
    (-> (connector-map {:dev-mode? true})
        (hk/create-connector nil))))

(defn start!
  "Starts the current development connector."
  []
  (swap! *connector conn/start!))

(defn stop!
  "Shuts down the current development connector."
  []
  (swap! *connector
    (fn [c]
      (when c (conn/stop! c))
      nil)))

(defn go!
  "Initializes the current development connector and starts it running."
  []
  (init)
  (start!))

(comment
  (go!)
  (start!)
  (stop!)
  ;;
)
