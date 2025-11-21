(ns dhex-pos.cljs.helpers.index)

(defn format-currency [amount]
  (str "$" (.toFixed amount 2)))
