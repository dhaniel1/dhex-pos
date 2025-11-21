(ns dhex-pos.cljs.core
  (:require
   [reagent.dom.client :as rdomc]
   [re-frame.core :as re-frame]
   [dhex-pos.cljs.events :as events]
   [dhex-pos.cljs.routes :as routes]
   [dhex-pos.cljs.views.view :as views]
   [dhex-pos.cljs.config :as config]
   ))


(defn dev-setup []
  (when config/debug?
    (println "dev mode")))

(defonce root (rdomc/create-root (.getElementById js/document "app")) )

(defn ^:dev/after-load mount-root []
  (re-frame/clear-subscription-cache!)
  (rdomc/render root [views/main-panel]))

(defn init []
  (routes/start!)
  (re-frame/dispatch-sync [::events/initialize-db])
  (dev-setup)
  (mount-root))


