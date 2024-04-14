(ns com.sagar.casa.ui.header
  (:require ["react-bootstrap" :refer [Container Col Row Navbar Stack]]
            [com.sagar.casa.ui.routes :as routes]))


(def links
  (map-indexed
   (fn [index [key val]]
     {:index index
      :label key
      :url val})
   {"Clojure" "https://clojure.org/"
    "Electric" "https://github.com/hyperfiddle/electric"
    "Reagent" "https://reagent-project.github.io/"}))


(defn Header []
  [:> Navbar {:sticky :top
              :bg :dark
              :variant :dark}
   [:> Stack {:direction :horizontal :gap 3 :class-name "w-100"}
    [:> Container {:fluid true}
     [:> Row
      [:> Col
       [:> (.-Brand Navbar) {:class-name "mx-2 display-6"
                             :href routes/home}
        [:big "Casa 🏠"]]]
      [:> Col {:xs "auto" :class-name "d-none d-sm-block"}
       [:> (.-Text Navbar)
        [:small (str "Made with " \u2665 " using ")]]
       (for [{:keys [index label url]} links]
         [:a {:href url :target "_blank" :key index}
          [:small "[" label "]"]])]]]]])
