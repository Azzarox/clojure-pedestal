(ns frontend.events
  (:require [re-frame.core :as rf]
            [ajax.core :as ajax]))

;; Loads the http-fx effect handler — enables :http-xhrio in events
(rf/reg-fx :http-xhrio (.-http-xhrio (js/require "day8.re-frame/http-fx")))

;; ── App init ──────────────────────────────────────────────────────────────────

(rf/reg-event-db
  :app/initialize
  (fn [_ _]
    ;; Initial state of the whole app
    {:feedback  []
     :loading?  false
     :error     nil}))

;; ── Fetch feedback list ───────────────────────────────────────────────────────

(rf/reg-event-fx
  :feedback/fetch
  (fn [{:keys [db]} _]
    {:db         (assoc db :loading? true)
     :http-xhrio {:method          :get
                  :uri             "/api/feedback"
                  :response-format (ajax/json-response-format {:keywords? true})
                  :on-success      [:feedback/fetch-success]
                  :on-failure      [:feedback/fetch-failure]}}))

(rf/reg-event-db
  :feedback/fetch-success
  (fn [db [_ response]]
    (-> db
        (assoc :feedback response)
        (assoc :loading? false))))

(rf/reg-event-db
  :feedback/fetch-failure
  (fn [db [_ error]]
    (-> db
        (assoc :error (str "Failed to load: " error))
        (assoc :loading? false))))

;; ── Create feedback ───────────────────────────────────────────────────────────

(rf/reg-event-fx
  :feedback/create
  (fn [_ [_ new-item]]
    {:http-xhrio {:method          :post
                  :uri             "/api/feedback"
                  :params          new-item
                  :format          (ajax/json-request-format)
                  :response-format (ajax/json-response-format {:keywords? true})
                  :on-success      [:feedback/create-success]
                  :on-failure      [:feedback/fetch-failure]}}))

(rf/reg-event-fx
  :feedback/create-success
  (fn [_ _]
    ;; Refresh the list after creating
    {:dispatch [:feedback/fetch]}))
