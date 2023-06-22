(ns com.sagar.casa.ui.footer
  (:require ["react-bootstrap" :refer [Container Col Row Navbar Stack]]))


(defn Footer []
  [:> Navbar {:fixed "bottom" :bg "dark" :variant "dark"}
   [:> Stack {:direction "horizontal" :gap 3 :class-name "w-100"}
    [:> Container {:fluid true}
     [:> Row
      [:> Col
       [:> (.-Text Navbar)
        [:small "Copyright " \u00A9 " 2023, Sagar Vrajalal"]]]
      [:> Col {:xs "auto"}
       [:> (.-Text Navbar)
        [:small (str "Made with " \u2665 " using ")]]
       (for [link {"Clojure" "https://clojure.org/"
                   "Electric" "https://github.com/hyperfiddle/electric"
                   "Reagent" "https://reagent-project.github.io/"}]
         [:a {:href (val link) :target "_blank"}
          [:small "[" (key link) "]"]])]]]]])
