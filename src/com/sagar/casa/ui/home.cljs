(ns com.sagar.casa.ui.home
  (:require ["react-bootstrap" :refer [Container Stack Image]]))


(defn Home []
  [:> Container {:data-theme :light
                 :fluid true}
   [:div {:class-name "pt-3 col-xs-12 col-md-4 mx-auto"}
    [:h1 {:class-name "display-4"} "Hi. I'm Sagar."]
    [:p "I'm a Software Engineer @ "
     [:a {:href "https://teamohana.com" :target "_blank"} "TeamOhana"]
     " where I'm making headcount management and planning collaborative."]
    [:p
     "I was born and raised in " [:b "Lisbon, PT "]
     [:span {:class-name "fi fi-pt"}] " and I'm currently based in"
     [:b " Goa, IN "] [:span {:class-name "fi fi-in"}] "."]
    [:p "I use this space to "
     [:a {:href "https://addyosmani.com/blog/write-learn/" :target "_blank"}
      "write about the interestings things I learn"] " during my"
     " relaxing evening reading sessions, or cold, dark and rainy night"
     " debugging sessions, and everything in between."]
    [:p "When I'm not writing code, I enjoy going on a walk, doing some"
     " yoga, playing with my dog or swimming."]
    [:div {:class-name "text-center"}
     [:> Image
      {:roundedCircle true
       :fluid true
       :alt "Woof!"
       :width "60%"
       :height :auto
       :src "https://a.storyblok.com/f/259055/3072x3024/7939eee9a6/bubble.jpg"}]]
    [:p "Reach out:"
     [:> Stack {:style {:font-size "36px"}
                :direction :horizontal
                :gap 2}
      [:a {:href "https://github.com/S4G4R" :target "/_blank"}
       [:i {:class-name "fa fa-github"}]]
      [:a {:href "https://linkedin.com/in/sagar-vrajalal" :target "/_blank"}
       [:i {:class-name "fa fa-linkedin"}]]
      [:a {:href "mailto:sagarvrajalal@gmail.com"}
       [:i {:class-name "fa fa-envelope"}]]]]]])
