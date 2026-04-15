(ns frontend.views
  (:require [re-frame.core :as rf]))

;; Views are pure functions of subscriptions — no direct state, no side effects.
;; They read from app-db via subs, dispatch events on user actions.

(defn feedback-item [{:keys [id title status]}]
  [:li {:key id}
   [:strong title]
   [:span {:style {:margin-left "1rem" :color "gray"}} status]])

(defn feedback-list []
  (let [items    @(rf/subscribe [:feedback/list])
        loading? @(rf/subscribe [:app/loading?])
        error    @(rf/subscribe [:app/error])]
    [:div
     [:h2 "Feedback"]
     (cond
       loading? [:p "Loading..."]
       error    [:p {:style {:color "red"}} error]
       :else    [:ul (map feedback-item items)])]))

(defn root []
  [:div
   [:h1 "Full Stack Re-frame + Pedestal"]
   [feedback-list]])
