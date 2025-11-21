(ns dhex-pos.cljs.events
  (:require
   [re-frame.core :as rf]
   [dhex-pos.cljs.db :as db]
   [day8.re-frame.tracing :refer-macros [fn-traced]]
   ))

(rf/reg-event-db
 ::initialize-db
 (fn-traced [_ _]
            db/app-db))

(rf/reg-event-fx
 ::navigate
 (fn-traced [_ [_ handler]]
            {:navigate handler}))

(rf/reg-event-fx
 ::set-active-panel
 (fn-traced [{:keys [db]} [_ active-panel]]
            {:db (assoc db :active-panel active-panel)}))

(rf/reg-event-db
 ::clear-cart
 (fn-traced [db] 
            (assoc-in db [:app :cart] {})))

(rf/reg-event-db 
 ::add-item
 (fn-traced [db [_ item-id]]
            (let [cart-items (-> db :app :cart )
                  existing-item (get cart-items item-id)]
              (if existing-item
                (update-in db [:app :cart item-id :quantity] inc)
                (update-in db [:app :cart] conj {item-id {:id item-id :quantity 1}})))))

(rf/reg-event-db 
 ::remove-item
 (fn-traced [db [_ item-id]]
            (let [cart-items (-> db :app :cart)
                  existing-item (get cart-items item-id)]
              
              (if existing-item
                (if (> (:quantity existing-item) 1)
                  (update-in db [:app :cart item-id :quantity] dec)
                  (update-in db [:app :cart] dissoc item-id ))
                db))))

(rf/reg-event-db
 ::set-active-category
 (fn-traced [db [_ category]]
            (assoc-in db [:app :active-category] category)))
