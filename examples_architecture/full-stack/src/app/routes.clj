(ns app.routes
  (:require [io.pedestal.http.route.definition.table :as table]
            [io.pedestal.http.body-params :as body-params]
            [app.handlers.feedback :as feedback]
            [app.handlers.spa      :as spa]))

;; Route priority: more specific routes first, catch-all last.
;;
;; /api/*  → JSON handlers (Clojure)
;; /*      → serves index.html (re-frame takes over in the browser)
;;
;; Static files (app.js, app.css) are handled by resource-routes
;; in connector.clj — they never reach these routes.

(defn routes []
  (table/table-routes
    [["/api/feedback" :get  [feedback/list-all]                         :route-name ::feedback-list]
     ["/api/feedback" :post [(body-params/body-params) feedback/create] :route-name ::feedback-create]

     ;; Catch-all — serves index.html so re-frame handles routing
     ["/*"            :get  [spa/index]                                 :route-name ::spa]]))
