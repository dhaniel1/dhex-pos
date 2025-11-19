(ns dhex-pos.cljs.core
  (:require
   [reagent.dom :as rdom]
   [re-frame.core :as re-frame]
   [dhex-pos.cljs.events :as events]
   [dhex-pos.cljs.routes :as routes]
   [dhex-pos.cljs.views :as views]
   [dhex-pos.cljs.config :as config]
   [reagent.dom.client :as rdomc]
   ))


(defn dev-setup []
  (when config/debug?
    (println "dev mode")))

(defn ^:dev/after-load mount-root []
  (re-frame/clear-subscription-cache!)
  (let [root-el (rdomc/create-root (.getElementById js/document "app"))]
    (rdomc/render root-el [views/main-panel] )))

(defn init []
  (routes/start!)
  (re-frame/dispatch-sync [::events/initialize-db])
  (dev-setup)
  (mount-root))
