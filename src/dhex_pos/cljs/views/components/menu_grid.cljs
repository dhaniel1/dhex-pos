(ns dhex-pos.cljs.views.components.menu-grid
  (:require [re-frame.core :as rf]
            [clojure.string :as string]

            [dhex-pos.cljs.subs :as subs]
            [dhex-pos.cljs.events :as events]
            [dhex-pos.cljs.helpers.index :refer [format-currency]]

            ["/components/ui/button.js" :refer [Button]]
            ["/components/ui/badge.js" :refer [Badge]]
            ["lucide-react" :refer [Plus]]
            ))

(defn categories
  []
  (let [active-category @(rf/subscribe [::subs/active-category])
        menu-categories @(rf/subscribe [::subs/menu-categories])
        set-active-category #(rf/dispatch [::events/set-active-category %])]
    
    [:div {:class "flex gap-2 overflow-x-auto pb-2"}
     (for [category  menu-categories]
       ^{:key category} [:> Button {:class "whitespace-nowrap"
                                    :on-click #(set-active-category category)
                                    :variant (if (= active-category category) "default" "outline")} 
                         (-> category
                             name
                             string/capitalize)])]))

(defn menu-item-card [item]
  (let [[id item-value] item
        {:keys [name price stock image-url]} item-value
        is-out-of-stock? (= stock 0)
        add-item         #(rf/dispatch [::events/add-item id])
        cart-quantity    @(rf/subscribe [::subs/cart-item-qty id])]

    [:div {:class (str "bg-card border border-border rounded-lg overflow-hidden transition-all hover:border-primary/50"
                       (when is-out-of-stock? " opacity-50"))}

     [:div {:class "aspect-square bg-muted relative"}
      [:img {:src image-url :class "w-full h-full object-cover"}]

      (when (pos? cart-quantity)
        [:> Badge {:class "absolute top-2 right-2 bg-primary text-primary-foreground"} cart-quantity])

      (when (and (> stock 0) (<= stock 5))
        [:> Badge {:variant "destructive" :class "absolute top-2 left-2"} "Low Stock"])

      (when is-out-of-stock?
        [:> Badge {:variant "destructive" :class "absolute top-2 left-2"} "Out of Stock"])]

     [:div {:class "p-4 space-y-3"}
      [:div
       [:h3 {:class "font-semibold text-foreground text-balance"} name]

       [:div {:class "flex items-center justify-between mt-1"}
        [:p {:class "text-lg font-bold text-primary"} (format-currency price)]
        [:p {:class "text-sm text-muted-foreground"} (str "Stock: " stock)]]]

      [:> Button {:class "w-full"
                  :on-click #(add-item)
                  :disabled (or is-out-of-stock? (>= cart-quantity stock))}
       [:> Plus {:class "w-4 h-4 mr-2"}]
       "Add to Cart"]]]))

(defn menu-items []
  (fn []
    (let [active-menu-items @(rf/subscribe [::subs/active-menu-items])]
      (if (empty? active-menu-items)
        [:div {:class "col-span-full text-center py-12 text-muted-foreground"}
         "No items available at the moment."]

        [:div {:class "grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 xl:grid-cols-4 gap-6"}
         (for [item active-menu-items
               :let [[id _] item]]
           ^{:key id}
           [menu-item-card item])]))))

(defn menu
  []
  [:div {:class "space-y-6"}
   [categories]
   [menu-items]
   ])
