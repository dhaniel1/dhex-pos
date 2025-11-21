(ns dhex-pos.cljs.views.view
  (:require [re-frame.core :as rf]
            [reagent.core :as reagent ]
            [dhex-pos.cljs.events :as events]
            [dhex-pos.cljs.routes :as routes]
            [dhex-pos.cljs.subs :as subs]
            [dhex-pos.cljs.views.components.header :as header]
            [dhex-pos.cljs.views.components.cart :as cart-view]
            [dhex-pos.cljs.views.components.menu-grid :as menu]
            ))

;; home

(defn home-panel []
  [:div {:class "min-h-screen bg-background flex flex-col"}
   [header/header]
   
   [:div {:class "flex-1 flex overflow-hidden"}
    [:div {:class "flex-1 overflow-auto p-4 md:p-6"}
     
     [:div {:class "h-full" } 
      [menu/menu]]]

    [:div {:class "hidden lg:block w-[400px] border-l border-border"}
     [cart-view/cart]]]])

(defmethod routes/panels :home-panel [] [home-panel])
;; main

(defn main-panel []
  (let [active-panel (rf/subscribe [::subs/active-panel])]
    (routes/panels @active-panel)))
