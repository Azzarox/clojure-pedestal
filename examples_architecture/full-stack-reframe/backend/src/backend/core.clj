(ns backend.core
  (:require [backend.connector :as connector])
  (:gen-class))

(defn -main [& _args]
  (connector/start))
