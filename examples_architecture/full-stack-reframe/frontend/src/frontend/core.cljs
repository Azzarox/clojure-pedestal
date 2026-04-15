(ns frontend.core
  (:require [reagent.dom :as rdom]
            [re-frame.core :as rf]
            [frontend.events]   ; loads all event handlers
            [frontend.subs]     ; loads all subscriptions
            [frontend.views :as views]))

(defn init []
  ;; Kick off the app — fetch feedback on startup
  (rf/dispatch-sync [:app/initialize])
  (rf/dispatch [:feedback/fetch])
  ;; Mount the re-frame app into the #app div in index.html
  (rdom/render [views/root] (.getElementById js/document "app")))
