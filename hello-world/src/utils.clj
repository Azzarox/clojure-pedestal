(ns utils
  (:require [clojure.string :as str]))

(def unmentionables
  #{"John" "George" "Michael"})

(defn greeting-for
  [greet-name]
  (let [trimmed (when greet-name (str/trim greet-name))]
    (cond
      (nil? trimmed) ;; We do not use (empty? because this checks for nil or "")
      "Hello, world!\n"

      (zero? (count trimmed)) ;; If we have query param but it is of 0 length 
      "400 Bad Request!\n"

      (unmentionables trimmed) nil

      :else
      (str "Hello, " trimmed "\n"))))

