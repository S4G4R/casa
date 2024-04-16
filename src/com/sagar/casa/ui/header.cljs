(ns com.sagar.casa.ui.header
  (:require ["react-bootstrap" :refer [Container Col Row Navbar Stack Nav]]
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
    [:> Container {:fluid true :class-name "align-items-center"}
     [:> Row
      [:> Col {:class-name "mx-2"}
       [:> (.-Brand Navbar) {:class-name "display-1"
                             :href routes/home}
        "Casa 🏠"]
       [:a {:href routes/blog
            :style {:text-decoration :none}}
        "[Blog]"]
       [:a {:href routes/literature
            :style {:text-decoration :none}}
        "[Literature]"]]
      [:> Col {:xs "auto" :class-name "d-none d-sm-block"}
       [:> (.-Text Navbar)
        [:small (str "Made with " \u2665 " using ")]]
       (for [{:keys [index label url]} links]
         [:a {:href url
              :target "_blank"
              :style {:text-decoration :none}
              :key index}
          [:small "[" label "]"]])]]]]])
