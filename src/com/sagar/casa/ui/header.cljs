(ns com.sagar.casa.ui.header
  (:require ["react-bootstrap" :refer [Navbar]]
            [com.sagar.casa.ui.routes :as routes]))


(defn Header []
  [:> Navbar {:fixed :top
              :bg :dark
              :variant :dark
              :style {:justify-content :center}}
   [:> (.-Brand Navbar) {:href routes/home}
    [:big "Casa"]]])
