(ns com.sagar.casa.ui.home
  (:require ["react-bootstrap" :refer [Card Col Container ListGroup Row]]))


(defn Home []
  [:> Container {:data-theme :light
                 :class-name "p-5"
                 :style {:text-align :center}}
   [:> Row
    [:> Col
     [:h1 {:class-name "display-3"} "Hi. I'm Sagar."]]]
   [:> Row {:class-name "p-5"}
    [:> Col
     [:p "I'm a backend developer."]]
    #_[:> Col
     [:h1 {:class-name "display-3"} "Hi. I'm Sagar."]]]])
