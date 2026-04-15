(ns api.handlers.feedback
  (:require [clojure.data.json :as json]))

;; Fake in-memory data — no database yet
(def feedback-store
  (atom [{:id 1 :title "Bug on login page"  :status "new"}
         {:id 2 :title "Add dark mode"       :status "new"}]))

(defn list-all
  "GET /api/feedback — returns all feedback as JSON"
  [_request]
  {:status  200
   :headers {"Content-Type"                "application/json"
             ;; CORS required — frontend is on a different origin (different port/server)
             "Access-Control-Allow-Origin" "*"}
   :body    (json/write-str @feedback-store)})

(defn create
  "POST /api/feedback — creates a new feedback item"
  [request]
  (let [body  (:json-params request)
        entry (assoc body :id (inc (count @feedback-store))
                          :status "new")]
    (swap! feedback-store conj entry)
    {:status  201
     :headers {"Content-Type"                "application/json"
               "Access-Control-Allow-Origin" "*"}
     :body    (json/write-str entry)}))
