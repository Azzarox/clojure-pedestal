(ns hello
  (:require [io.pedestal.connector :as conn]
            [io.pedestal.http.http-kit :as hk]
            [clojure.string :as str]))

(def unmentionables
  #{"John" "George" "Michael"})

(defn not-found
  "Returns 404"
  []
  {:status 404 :body "Not Found!\n"})

(defn ok
  [message]
  {:status 200 :body message})

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

(defn greet-handler
  [request]
  (let [greet-name (get-in request [:query-params :name])
        message    (greeting-for greet-name)]
    (if message
      (ok message)
      (not-found))))

;; Using #' it evaluates the Var and not the function behind it ... this way i dont need to evaluate depending functions and stuff when i change something
(def routes
  #{["/greet" :get #'greet-handler :route-name :greet]})

(def port 8890)

(defn create-connector
  []
  (-> (conn/default-connector-map port)
      (conn/with-default-interceptors)
      (conn/with-routes routes)
      (hk/create-connector nil)))

(defonce *connector (atom nil))

(defn start
  []
  (println "Server is starting...")
  (reset! *connector
          (conn/start! (create-connector)))
  (println "Server is listening on PORT:" port))

(defn stop
  []
  (println "Server is stopping...")
  (conn/stop! @*connector)
  (reset! *connector nil)
  (println "Server stopped!"))

(defn restart
  []
  (println "Server is restarting...")
  (when @*connector
    (stop))
  (start)
  (println "Server is ready!"))