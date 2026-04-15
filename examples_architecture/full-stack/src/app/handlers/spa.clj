(ns app.handlers.spa
  (:require [clojure.java.io :as io]))

(defn index
  "Serves index.html for all non-API routes.
   Re-frame handles client-side routing in the browser —
   so /dashboard, /feedback etc all get the same index.html
   and re-frame decides what to render based on the URL."
  [_request]
  {:status  200
   :headers {"Content-Type" "text/html"}
   :body    (slurp (io/resource "public/index.html"))})
