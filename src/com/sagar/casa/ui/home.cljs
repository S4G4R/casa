(ns com.sagar.casa.ui.home
  (:require ["react-bootstrap" :refer [Stack Container Button]]
            [com.sagar.casa.ui.routes :as routes]))


(defn Home []
  [:> Container {:data-theme :light
                 :class-name "p-5"
                 :style {:text-align :center}}
   [:> Stack {:gap 2}
    ;; TODO: Store this stuff in StoryBlok?
    [:div
     [:h1 {:class-name "display-4"} "Hi. I'm Sagar."]
     [:p "I like to write about . . ."]]
    [:> Stack {:class-name "mx-auto p-5"}
     [:> Button {:href routes/blog
                 :variant :dark
                 :class-name :contrast}
      "Blog"]]]])
