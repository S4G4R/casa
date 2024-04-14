(ns com.sagar.casa.ui.home
  (:require ["react-bootstrap" :refer [Stack Container Button]]
            [com.sagar.casa.ui.routes :as routes]))


(defn Home []
  [:> Container {:data-theme :light
                 :fluid true
                 :style {:text-align :center}}
   [:div {:class-name "pt-3 col-xs-12 col-md-4 mx-auto"}
    [:h1 {:class-name "display-4"} "Hi. I'm Sagar."]
    [:p "I'm a Backend Developer @ "
     [:a {:href "https://unifize.com" :target "_blank"} "Unifize"]
     " where I'm working on making processes collaborative."]
    [:p "I use this space to "
     [:a {:href "https://addyosmani.com/blog/write-learn/" :target "_blank"}
      "write about the interestings things I learn"] " during my"
     " relaxing evening reading sessions, or cold, dark and rainy night"
     " debugging sessions, and everything in between."]
    [:> Stack {:class-name "pt-3" :gap 5}
     [:> Button {:href routes/blog
                 :variant :dark
                 :class-name "contrast mx-auto"}
      "Blog"]
     [:> Button {:href routes/literature
                 :variant :dark
                 :class-name "contrast mx-auto"}
      "Literature"]]]])
