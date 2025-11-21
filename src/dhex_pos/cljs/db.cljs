(ns dhex-pos.cljs.db)

(def app-db
  {:app {:tax-rate 8
         :active-category :all
         :company-info {:name "dhex-pos"
                        :office-phone "555-555-555"}

         :inventory {1 {:id 1
                        :name "French Fries"
                        :price 3.99
                        :stock 25
                        :image-url "/images/crispy-french-fries.png"
                        :category :sides}
                     
                     2 {:id 2
                        :name "Onion Rings"
                        :price 4.49
                        :stock 18
                        :image-url "/images/golden-onion-rings.jpg"
                        :category :sides}
                     
                     3 {:id 3
                        :name "Chicken Nuggets"
                        :price 5.99
                        :stock 20
                        :image-url "/images/crispy-chicken-nuggets.png"
                        :category :mains}
                     
                     4 {:id 4
                        :name "Coleslaw"
                        :price 2.99
                        :stock 15
                        :image-url "/images/fresh-coleslaw.jpg"
                        :category :sides}
                     
                     5 {:id 5
                        :name "Coca Cola"
                        :price 2.49
                        :stock 30
                        :image-url "/images/refreshing-cola.png"
                        :category :beverages}
                     
                     6 {:id 6
                        :name "Sprite"
                        :price 2.49
                        :stock 28
                        :image-url "/images/lemon-lime-soda.jpg"
                        :category :beverages}
                     
                     7 {:id 7
                        :name "Iced Tea"
                        :price 2.99
                        :stock 22
                        :image-url "/images/iced-tea.png"
                        :category :beverages}
                     
                     8 {:id 8
                        :name "Milkshake"
                        :price 4.99
                        :stock 12
                        :image-url "/images/vanilla-milkshake.png"
                        :category :beverages}
                     
                     9 {:id 9
                        :name "Classic Burger"
                        :price 8.99
                        :stock 15
                        :image-url "/images/classic-beef-burger.png"
                        :category :burgers}
                     
                     10 {:id 10
                         :name "Cheeseburger"
                         :price 9.99
                         :stock 12
                         :image-url "/images/cheeseburger-with-lettuce.jpg"
                         :category :burgers}
                     
                     11 {:id 11
                         :name "Bacon Burger"
                         :price 10.99
                         :stock 8
                         :image-url "/images/bacon-burger.png"
                         :category :burgers}
                     
                     12 {:id 12
                         :name "Veggie Burger"
                         :price 9.49
                         :stock 10
                         :image-url "/images/vegetarian-burger.png"
                         :category :burgers}}

         :cart {}}})


