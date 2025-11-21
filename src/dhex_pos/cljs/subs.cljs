(ns dhex-pos.cljs.subs
  (:require
   [clojure.string :as string]
   [re-frame.core :as rf]))

(defn map->vec
  "Convert map to a vector"
  [map-data]
  (vals map-data ))

(rf/reg-sub
 ::name
 (fn [db]
   (:name db)))

(rf/reg-sub
 ::tax-rate 
 (fn [db]
   (-> db :app :tax-rate)))

(rf/reg-sub
 ::active-category
 (fn [db]
   (-> db :app :active-category)))


(rf/reg-sub
 ::active-panel
 (fn [db _]
   (:active-panel db)))

(rf/reg-sub
 ::unsorted-cart-items
 (fn [db]
   (-> db :app :cart)))

(rf/reg-sub
 ::menu-items
 (fn [db]
   (-> db :app :inventory)))

(rf/reg-sub
 ::cart-items

 :<- [::menu-items]
 :<- [::unsorted-cart-items]

 (fn [[menu-items cart-items]]
   (reduce 
    (fn [acc item]
      (let [[id item-properties] item
            menu-item (-> (get menu-items id)
                          (assoc :quantity (:quantity item-properties)))]
        (conj acc menu-item))) 
    [] cart-items 
    )))

(rf/reg-sub
 ::cart-item-qty
 
 :<- [::unsorted-cart-items]

 (fn [cart-items [_ id]]
   (->> id
        (get cart-items)
        :quantity)))

(rf/reg-sub 
 ::cart-sub-total
 
 :<- [::cart-items]

 (fn [cart-items _]
   (reduce 
    (fn [acc item]
      (+ acc (* (:quantity item) (:price item))))  
    0 cart-items)))

(rf/reg-sub
 ::cart-tax-value

 :<- [::tax-rate]
 :<- [::cart-sub-total]

 (fn [[tax-rate cart-sub-total]]
   (-> (/ tax-rate 100)
       (* cart-sub-total))))

(rf/reg-sub
 ::cart-total
 
 :<- [::cart-sub-total]
 :<- [::cart-tax-value]

 (fn [[cart-sub-total cart-tax-value]]
   (+ cart-sub-total cart-tax-value)))

(rf/reg-sub
 ::sorted-menu

 :<- [::menu-items]

 (fn [menu-items _]
   (sort-by (comp :category val) menu-items)))

(rf/reg-sub
 ::grouped-menu

 :<- [::menu-items]

 (fn [menu-items _]
   (group-by (comp :category val) menu-items)))


(rf/reg-sub
 ::menu-categories

 :<- [::grouped-menu]

 (fn [grouped-menu]
   (-> grouped-menu
       keys
       sort
       (conj :all)
       ;;(map string/capitalize)
       )))

(rf/reg-sub
 ::active-menu-items
 
 :<- [::active-category]
 :<- [::grouped-menu]
 :<- [::sorted-menu]

 (fn [[active-category grouped-menu sorted-menu] _]
   (if (= active-category :all)
     sorted-menu
     (get grouped-menu active-category))))












#_(rf/reg-sub
   ::cart-items
   :<- [::menu-items]
   :<- [::unsorted-cart-items]
   (fn [[menu-items cart-items] _]
     (->> cart-items
          (keep (fn [[item-id {:keys [quantity]}]]
                  (when-let [item (get menu-items item-id)]
                    (assoc item :quantity (or quantity 0)))))
          vec)))
