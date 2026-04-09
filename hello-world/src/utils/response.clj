(ns utils.response
  (:require [clojure.data.json :as json]))

(defn not-found
  "Returns 404"
  []
  {:status 404 :body "Not Found!\n"})

(defn ok
  [message]
  {:status 200
   :body message})


(defn missing-response-content-type?
  [context]
  (nil? (get-in context [:response :headers "Content-Type"])))


(def supported-types
  ["text/html" "application/edn" "application/json" "text/plain"])

(defn accepted-type
  [context]
  (get-in context [:request :accept :field] "text/plain"))

(defn transform-content
  [body content-type]
  (case content-type
    "text/html" body
    "text/plain" body
    "application/edn" (pr-str body)
    "application/json" (json/write-str body)))

(defn coerce-to
  [response content-type]
  (-> response
      (update :body transform-content content-type)
      (assoc-in [:headers "Content-Type"] content-type)))

