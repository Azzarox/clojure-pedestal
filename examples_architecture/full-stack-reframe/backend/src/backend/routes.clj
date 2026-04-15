(ns backend.routes
  (:require [io.pedestal.http.route.definition.table :as table]
            [io.pedestal.http.body-params :as body-params]
            [backend.handlers.feedback :as feedback]
            [backend.handlers.spa      :as spa]))

(defn routes []
  (table/table-routes
    [["/api/feedback" :get  [feedback/list-all]                         :route-name ::feedback-list]
     ["/api/feedback" :post [(body-params/body-params) feedback/create] :route-name ::feedback-create]
     ["/"            :get  [spa/index]                                 :route-name ::spa]]))
