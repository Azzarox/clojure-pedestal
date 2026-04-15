(ns backend.handlers.spa
  (:require [clojure.java.io :as io]))

(defn index [_request]
  {:status  200
   :headers {"Content-Type" "text/html"}
   :body    (slurp (io/resource "public/index.html"))})
