(ns com.sagar.casa.ui.home
  (:require ["react-bootstrap" :refer [Stack Container Button]]
            [com.sagar.casa.ui.routes :as routes]))


(defn Home []
  [:> Container {:data-theme :light
                 :fluid true
                 :class-name "p-5"
                 :style {:text-align :center}}
   [:div {:class-name "col-xs-12 col-md-6 mx-auto"}
    [:h1 {:class-name "display-4"} "Hi. I'm Sagar."]
    [:p "I'm currently a backend developer @ "
     [:a {:href "https://unifize.com" :target "_blank"} "Unifize"]
     " where I'm working on making processes collaborative."]
    [:p "I use this space to "
     [:a {:href "https://addyosmani.com/blog/write-learn/" :target "_blank"}
      "write about the interestings things I learn"] ", which may happen"
     " during my relaxing evening reading sessions, or cold, dark"
     " and rainy night debugging sessions, and everything in between."]
    [:> Stack {:class-name "p-5" :gap 5}
     [:> Button {:href routes/blog
                 :variant :dark
                 :class-name "contrast mx-auto"}
      "Blog"]]]])
