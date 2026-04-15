(ns api.routes
  (:require [io.pedestal.http.route.definition.table :as table]
            [io.pedestal.http.body-params :as body-params]
            [api.handlers.feedback :as feedback]))

;; Only API routes — no HTML, no static files
;; Frontend runs on its own server (e.g. localhost:3000)
;; and calls these endpoints with fetch/axios

(defn routes []
  (table/table-routes
    [["/api/feedback" :get  [feedback/list-all]                    :route-name ::feedback-list]
     ["/api/feedback" :post [(body-params/body-params) feedback/create] :route-name ::feedback-create]]))
