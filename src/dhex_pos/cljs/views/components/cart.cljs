(ns dhex-pos.cljs.views.components.cart
  (:require ["lucide-react" :refer [Plus Minus Trash2 ShoppingCart ]]
            ["/components/ui/button.js" :refer [Button]]
            ["/components/ui/scroll-area.js" :refer [ScrollArea]]
            
            [re-frame.core :as rf]

            [dhex-pos.cljs.subs :as subs]
            [dhex-pos.cljs.events :as events]
            [dhex-pos.cljs.helpers.index :refer [format-currency]]
            ))


(defn cart-header
  []
  (let [cart-items @(rf/subscribe [::subs/cart-items])]
    [:div {:class "p-6 border-b border-border"}
     [:div {:class "flex items-center justify-between"}
      [:div {:class "flex items-center gap-2"}
       [:> ShoppingCart {:class "w-5 h-5 text-primary"}]
       [:h2 {:class "text-xl font-semibold text-foreground "} "Current Order"]]

      (when (seq cart-items)
        [:> Button {:variant "ghost"
                    :size "sm"
                    :className "text-destructive hover:text-destructive"
                    :on-click #(rf/dispatch [::events/clear-cart])}
         [:> Trash2 {:class "w-4 h-4 mr-1"}]
         "Clear"])
      ]]))

(defn empty-cart
  []
  [:div {:class "flex flex-col items-center justify-center h-full text-center py-12"}
   [:> ShoppingCart {:class "w-16 h-16 text-muted-foreground/30 mb-4"}]
   [:p {:class "text-muted-foreground"} "No items in cart"]
   [:p {:class "text-sm text-muted-foreground mt-1"} "Add items from the menu"]]
  )

(defn cart-item
  [{:keys [id name price quantity]}]
  (let [remove-item  #(rf/dispatch [::events/remove-item %])
        add-item  #(rf/dispatch [::events/add-item %])]

    [:div {:class "bg-muted/50 rounded-lg p-4 space-y-3"}
     [:div {:class "flex items-start justify-between"}
      [:div {:class "flex-1"}
       [:h3 {:class "font-medium text-foreground text-balance"}  name]
       [:p {:class "text-sm text-muted-foreground mt-1"} (str price " each")]]
      [:p {:class "font-semibold text-foreground"} (* price quantity)]]

     ;; item action butttons
     [:div {:class "flex items-center gap-2"}
      [:> Button {:variant "outline"
                  :size "sm"
                  :class "h-8 w-8 p-0"
                  :on-click #(remove-item id)}
       [:> Minus {:class "w-4 h-4"}]]

      [:div {:class "flex-1 text-center"}
       [:span {:class "text-sm font-medium text-foreground"} "Qty: " quantity]]

      [:> Button {:variant "outline"
                  :size "sm"
                  :class "h-8 w-8 p-0"
                  :disabled false ;; should be disabled when item quantity is greater than item stock
                  :on-click #(add-item id)}

       [:> Plus {:class "w-4 h-4"}]]]]))

(defn cart-summary
  []
  (let [tax-rate @(rf/subscribe [::subs/tax-rate])
        cart-tax-value @(rf/subscribe [::subs/cart-tax-value])
        cart-sub-total @(rf/subscribe [::subs/cart-sub-total])
        cart-total @(rf/subscribe [::subs/cart-total])
        complete-order #(js/console.log "Completing order")]

    [:div {:class "border-t border-border p-6 space-y-4"}
     [:div {:class "space-y-2"}
      [:div {:class "flex justify-between text-sm"}
       [:span {:class "text-muted-foreground"} "Subtotal"]
       [:span {:class "font-medium text-foreground"} (format-currency cart-sub-total)]]
      
      [:div {:class "flex justify-between text-sm"}
       [:span {:class "text-muted-foreground"} (str "Tax (" tax-rate ")%")]
       [:span {:class "font-medium text-foreground"} (format-currency cart-tax-value)]]
      
      [:div {:class "h-px bg-border"}]  ; This is the Separator component as a simple divider
      
      [:div {:class "flex justify-between"}
       [:span {:class "font-semibold  text-foreground"} "Total"]
       [:span {:class "text-2xl font-bold text-primary"}  (format-currency cart-total)]]

      ]

     [:> Button {:size "lg"
      :class "w-full h-12 text-base"
      :on-click #(complete-order)}

      "Complete Order"]]))

(defn cart-items-view
  []
  (fn []
    (let [cart-items @(rf/subscribe [::subs/cart-items])]
      [:div {:class "space-y-4"}
       (for [item cart-items]
         ^{:key (:id item)} [cart-item item])])))

(defn cart
  []
  (let [cart-items @(rf/subscribe [::subs/cart-items])]
    [:div {:class "h-full flex flex-col bg-card"}
     [cart-header]

     [:> ScrollArea {:class "flex-1 p-6"}
      (if (empty? cart-items) 
        [empty-cart]
        [cart-items-view])]

     (when (seq cart-items)
       [cart-summary])]))
