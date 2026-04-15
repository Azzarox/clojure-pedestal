(ns backend.handlers.feedback
  (:require [clojure.data.json :as json]))

(def feedback-store
  (atom [{:id 1 :title "Bug on login page" :status "new"}
         {:id 2 :title "Add dark mode"      :status "new"}]))

(defn list-all [_request]
  {:status  200
   :headers {"Content-Type" "application/json"}
   :body    (json/write-str @feedback-store)})

(defn create [request]
  (let [body  (:json-params request)
        entry (assoc body :id     (inc (count @feedback-store))
                          :status "new")]
    (swap! feedback-store conj entry)
    {:status  201
     :headers {"Content-Type" "application/json"}
     :body    (json/write-str entry)}))
