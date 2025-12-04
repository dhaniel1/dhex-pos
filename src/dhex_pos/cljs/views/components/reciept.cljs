(ns dhex-pos.cljs.views.components.reciept
  (:require ["lucide-react" :refer [Download ReceiptIcon X]]
            ["/components/ui/button.js" :refer [Button]]
            ["/components/ui/separator.js" :refer [Separator]]
            ["/components/ui/dialog.js"   :refer [Dialog DialogContent DialogDescription
                                                  DialogHeader DialogTitle DialogTrigger]]
            
            [re-frame.core :as rf]

            [dhex-pos.cljs.subs :as subs]
            [dhex-pos.cljs.events :as events]
            [dhex-pos.cljs.helpers.index :refer [format-currency]]
            ))

(defn items-list
  []
  (let [ cart-items  @(rf/subscribe [::subs/cart-items])]
    [:<>
     (for [{:keys [id name price quantity]} cart-items]
       ^{:key id}[:div {:class "space-y-1"}
                  [:div {:class "flex justify-between"}
                   [:span {:class "text-foreground font-medium"} name ]
                   [:span {:class "text-foreground font-medium"} 
                    (-> (* price quantity) format-currency)]]
                  [:div {:class "flex justify-between text-sm text-muted-foreground pl-4"}
                   [:span (str quantity " ×  " (format-currency price))]]])]))

(defn reciept
  []
  (let [order-number-text "229394"
        tax-rate @(rf/subscribe [::subs/tax-rate])
        office-phone @(rf/subscribe [::subs/office-phone])
        company-street @(rf/subscribe [::subs/company-street])
        company-state @(rf/subscribe [::subs/company-state])
        company-country @(rf/subscribe [::subs/company-country])
        company-name @(rf/subscribe [::subs/company-name])
        cart-tax-value @(rf/subscribe [::subs/cart-tax-value])
        cart-sub-total @(rf/subscribe [::subs/cart-sub-total])
        cart-total @(rf/subscribe [::subs/cart-total])
        handle-print #(js/window.print)                     ;; register this as a side-effect
        toggle-reciept #(rf/dispatch [::events/toggle-receipt %])]
    
    [:div   
     ;; Receipt Content
     [:div {:class "pb-5 space-y-6 print:p-8"}

      
      ;; Store Info
      [:div {:class "text-center space-y-1"}
       [:h1 {:class "text-2xl font-bold text-foreground"} company-name]
       [:p {:class "text-sm text-muted-foreground"} company-street]
       [:p {:class "text-sm text-muted-foreground"} (str company-state company-country)]
       [:p {:class "text-sm text-muted-foreground"} (str "Tel: " office-phone)]]
      
      [:> Separator]

      ;; Order Info
      [:div {:class "space-y-1 text-center"}
       [:p {:class "text-sm text-muted-foreground"} order-number-text]
       [:p {:class "text-sm text-muted-foreground"} (.toString (.now js/Date))]
       [:p {:class "text-sm text-muted-foreground"} "Cashier: Station #1"]]
      
      [:div {:class "h-px bg-border"}]
      
      ;; Items
      [:div {:class "space-y-3"}
       [items-list]]
      
      [:> Separator]
      
      ;; Totals
      [:div {:class "space-y-2"}
       [:div {:class "flex justify-between text-sm"}
        [:span {:class "text-muted-foreground"} "Subtotal"]
        [:span {:class "text-foreground"}  (format-currency cart-sub-total)]]
       
       [:div {:class "flex justify-between text-sm"}
        [:span {:class "text-muted-foreground"} (str "Tax " tax-rate)]
        [:span {:class "text-foreground"} (format-currency  cart-tax-value)]]
       
       [:> Separator]


       
       [:div {:class "flex justify-between text-lg font-bold"}
        [:span {:class "text-foreground"} "Total"]
        [:span {:class "text-primary"} (format-currency cart-total)]]]
      
      [:> Separator]
      
      ;; Footer
      [:div {:class "text-center space-y-2"}
       [:p {:class "text-sm font-medium text-foreground"} "Thank you for your order!"]
       [:p {:class "text-xs text-muted-foreground"} "Please come again"]]]
     
     ;; Actions
     [:div {:class "border-t border-border p-6 pb-0 flex gap-3 print:hidden"}
      [:> Button {:variant "outline"
                  :class "whitespace-nowrap w-1/2"
                  :on-click handle-print}
       [:> Download {:class "w-4 h-4 mr-2"}]
       "Print"]
      
      [:> Button {:class "whitespace-nowrap w-1/2"
                  ;; :variant 
                  :on-click #(toggle-reciept false)}
       "Close"]]]))


(defn reciept-dialog
  [trigger-button]
  (let [show-reciept? @(rf/subscribe [::subs/show-reciept?])]
    [:> Dialog  {:open show-reciept?
                 :onOpenChange  #(rf/dispatch [::events/toggle-receipt %])}
     [:> DialogTrigger {:asChild true} trigger-button]
     [:> DialogContent {:class "bg-card border border-border rounded-lg w-full max-w-md shadow-2xl max-h-[94vh] overflow-auto"}
      [:> DialogHeader  
       [:> DialogTitle
        [:div {:class "flex items-center gap-2"}
         [:> ReceiptIcon {:class "w-5 h-5 text-primary"} ]
         [:h2 {:class "text-xl font-semibold text-foreground"} "Receipt"]]  ]
       [:> DialogDescription]]
      [reciept]
      ]])
  )
