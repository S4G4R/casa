(ns com.sagar.casa.ui.footer
  (:require ["react-bootstrap" :refer [Container Col Row Navbar Stack]]))


(def links
  (map-indexed
   (fn [index [key val]]
     {:index index
      :label key
      :url val})
   {"Clojure" "https://clojure.org/"
    "Electric" "https://github.com/hyperfiddle/electric"
    "Reagent" "https://reagent-project.github.io/"}))


(defn Footer []
  [:> Navbar {:fixed :bottom :bg :dark :variant :dark}
   [:> Stack {:direction "horizontal" :gap 3 :class-name "w-100"}
    [:> Container {:fluid true}
     [:> Row
      [:> Col
       [:> (.-Text Navbar)
        [:small "Copyright " \u00A9 " 2023, Sagar Vrajalal"]]]
      [:> Col {:xs "auto" :class-name "d-none d-sm-block"}
       [:> (.-Text Navbar)
        [:small (str "Made with " \u2665 " using ")]]
       (for [{:keys [index label url]} links]
         [:a {:href url :target "_blank" :key index}
          [:small "[" label "]"]])]]]]])
