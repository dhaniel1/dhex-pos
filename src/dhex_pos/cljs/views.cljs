(ns dhex-pos.cljs.views
  (:require
   [re-frame.core :as re-frame]
   [reagent.core :as reagent ]
   [dhex-pos.cljs.events :as events]
   [dhex-pos.cljs.routes :as routes]
   [dhex-pos.cljs.subs :as subs]
   ["/components/ui/button.js" :refer [Button]]
   ))


;; home

(defn home-panel []
  (let [name (re-frame/subscribe [::subs/name])]
    [:div
     [:h1
      (str "Hello from " @name ". This is the Home Page.")]

     [:div
      [:a {:on-click #(re-frame/dispatch [::events/navigate :about])}
       "go to About Page"]]

     [:> Button {:variant "outline"
                 :className "!bg-red-300"} "This is a test button"]
     ]))

(defmethod routes/panels :home-panel [] [home-panel])

;; about

(defn about-panel []
  [:div
   [:h1.text-pink-200 "This is the About Page."]

   [:div
    [:a {:on-click #(re-frame/dispatch [::events/navigate :home])}
     "go to Home Page"]]])

(defmethod routes/panels :about-panel [] [about-panel])

;; main

(defn main-panel []
  (let [active-panel (re-frame/subscribe [::subs/active-panel])]
    (routes/panels @active-panel)))
