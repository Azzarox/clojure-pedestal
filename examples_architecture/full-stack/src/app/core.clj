(ns app.core
  (:require [app.connector :as connector])
  (:gen-class))

(defn -main [& _args]
  (connector/start))
