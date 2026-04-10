(ns main
  (:require [io.pedestal.connector :as conn]
            [io.pedestal.http.http-kit :as hk]
            [io.pedestal.http.route :as route]
            [io.pedestal.connector.test :as test]))

(defn response [status body & {:as headers}]
  {:status status :body body :headers headers})

(def ok (partial response 200))

(def created (partial response 201))

(def accepted (partial response 202))

(def echo
  {:name :echo
   :enter
   (fn [context]
     (let [request  (:request context)
           response (ok request)]
       (assoc context :response response)))})

(defonce *database (atom {}))

(def db-interceptor
  {:name :db-interceptor
   :enter
   (fn [context]
     (update context :request assoc :database @*database))
   :leave
   (fn [context]
     (if-let [tx-data (:tx-data context)]
       (let [database' (apply swap! *database tx-data)]
         (assoc-in context [:request :database] database'))
       context))})

(defn make-list [list-name]
  {:name  list-name
   :items {}})

(defn make-list-item [item-name]
  {:name  item-name
   :done? false})

(def list-create
  {:name :list-create
   :enter
   (fn [context]
     (let [list-name (get-in context [:request :query-params :name] "Unnamed List")
           new-list  (make-list list-name)
           db-id     (str (gensym "l"))
           url       (route/url-for :list-view :params {:list-id db-id})]
       (assoc context
              :response (created new-list "Location" url)
              :tx-data [assoc db-id new-list])))})

(defn find-list-by-id [dbval db-id]
  (get dbval db-id))

(def list-view
  {:name :list-view
   :enter
   (fn [context]
     (let [db-id    (get-in context [:request :path-params :list-id])
           the-list (when db-id
                      (find-list-by-id
                       (get-in context [:request :database])
                       db-id))]
       (cond-> context
         the-list (assoc :result the-list))))})

(def entity-render
  {:name :entity-render
   :leave
   (fn [context]
     (if-let [item (:result context)]
       (assoc context :response (ok item))
       context))})

(def routes
  #{["/todo" :post [db-interceptor
                    list-create]]
    ["/todo" :get echo :route-name :list-query-form]
    ["/todo/:list-id" :get [entity-render 
                            db-interceptor 
                            list-view]]
    ["/todo/:list-id" :post echo :route-name :list-item-create]
    ["/todo/:list-id/:item-id" :get echo :route-name :list-item-view]
    ["/todo/:list-id/:item-id" :put echo :route-name :list-item-update]
    ["/todo/:list-id/:item-id" :delete echo :route-name :list-item-delete]})

(defn create-connector []
  (-> (conn/default-connector-map 8890)
      (conn/with-default-interceptors)
      (conn/with-routes routes)
      (hk/create-connector nil)))

;; For interactive development
(defonce *connector (atom nil))

(defn start []
  (reset! *connector
          (conn/start! (create-connector))))

(defn stop []
  (conn/stop! @*connector)
  (reset! *connector nil))

(defn restart []
  (stop)
  (start))

(defn test-request [verb url]
  (test/response-for @*connector verb url))
