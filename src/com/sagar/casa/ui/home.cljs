(ns com.sagar.casa.ui.home
  (:require ["react-bootstrap" :refer [Container Stack]]))


(defn Home []
  [:> Container {:data-theme :light
                 :class-name "align-self-center"
                 :fluid true}
   [:div {:class-name "pt-3 col-xs-12 col-md-4 mx-auto"}
    [:h1 {:class-name "display-4"} "Hi. I'm Sagar."]
    [:p "I'm a Backend Engineer @ "
     [:a {:href "https://unifize.com" :target "_blank"} "Unifize"]
     " where I'm working on making processes collaborative."]
    [:p "I use this space to "
     [:a {:href "https://addyosmani.com/blog/write-learn/" :target "_blank"}
      "write about the interestings things I learn"] " during my"
     " relaxing evening reading sessions, or cold, dark and rainy night"
     " debugging sessions, and everything in between."]
    [:p "When I'm not writing code, I enjoy going on a walk, doing some"
     " yoga, playing with my dog or swimming."]
    [:p "You can reach out to me via the below links."
     [:> Stack {:style {:font-size "36px"}
                :direction :horizontal
                :gap 2}
      [:a {:href "https://github.com/S4G4R" :target "/_blank"}
       [:i {:class-name "fa fa-github"}]]
      [:a {:href "https://linkedin.com/in/sagar-vrajalal" :target "/_blank"}
       [:i {:class-name "fa fa-linkedin"}]]
      [:a {:href "mailto:sagarvrajalal@gmail.com"}
       [:i {:class-name "fa fa-envelope"}]]]]]])
