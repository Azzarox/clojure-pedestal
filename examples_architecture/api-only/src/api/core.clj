(ns api.core
  (:require [api.connector :as connector])
  (:gen-class))

(defn -main [& _args]
  (connector/start))
