(ns frontend.subs
  (:require [re-frame.core :as rf]))

;; Subscriptions — views use these to read from app-db.
;; Views never access app-db directly.

(rf/reg-sub :feedback/list
  (fn [db _] (:feedback db)))

(rf/reg-sub :app/loading?
  (fn [db _] (:loading? db)))

(rf/reg-sub :app/error
  (fn [db _] (:error db)))
