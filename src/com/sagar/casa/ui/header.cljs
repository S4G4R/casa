(ns com.sagar.casa.ui.header
  (:require ["react-bootstrap" :refer [Navbar]]))


(defn Header []
  [:> Navbar {:fixed "top" :bg "dark" :variant "dark"
              :style {:justify-content "center"}}
   [:> (.-Brand Navbar) [:big "Casa"]]])
