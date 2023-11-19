(ns com.sagar.casa.ui.header
  (:require ["react-bootstrap" :refer [Navbar]]
            [com.sagar.casa.ui.routes :as routes]))


(defn Header []
  [:> Navbar {:fixed :top
              :bg :dark
              :variant :dark}
   [:> (.-Brand Navbar) {:class-name "mx-auto"
                         :href routes/home}
    [:big "Casa"]]])
