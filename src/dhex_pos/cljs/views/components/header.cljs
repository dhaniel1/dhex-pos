(ns dhex-pos.cljs.views.components.header
  (:require ["lucide-react" :refer [Store]])) 

(defn header 
  []
  [:header {:class "border-b border-border bg-card px-4 md:px-6 py-4"}
   [:div {:class "flex items-center justify-between"}
    [:div {:class "flex items-center gap-3"}
     [:div {:class "bg-primary text-primary-foreground p-2 rounded-lg"}
      [:> Store {:class "w-5 h-5 md:w-6 md:h-6"}]]
     [:div
      [:h1 {:class "text-lg md:text-xl font-semibold text-foreground"} 
       "FastBite POS"]
      [:p {:class "text-xs md:text-sm text-muted-foreground hidden sm:block"} 
       "Point of Sale System"]]]
    
    [:div {:class "hidden md:flex items-center gap-4"}
     [:div {:class "text-right"}
      [:p {:class "text-sm text-muted-foreground"} "Cashier"]
      [:p {:class "text-sm font-medium text-foreground"} "Station #1"]]]]])
