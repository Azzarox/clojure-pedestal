(ns interceptors
  (:require [utils.response :refer :all]
            [utils :refer :all]
            [io.pedestal.http.content-negotiation :as content-negotiation]))

(def content-negotiation-interceptor
  (content-negotiation/negotiate-content supported-types))

(def coerce-body-interceptor
  {:name ::coerce-body
   :leave
   (fn [context]
     (cond-> context
       (missing-response-content-type? context)
       (update :response coerce-to (accepted-type context))))})

(defn greet-handler
  [request]
  (let [greet-name (get-in request [:query-params :name])
        message    (greeting-for greet-name)]
    (if message
      (ok message)
      (not-found))))

(def echo
  {:name :echo
   :enter #(assoc % :response (ok (:request %)))})

