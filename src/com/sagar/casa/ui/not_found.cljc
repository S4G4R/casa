(ns com.sagar.casa.ui.not-found
  (:require #?(:clj [com.sagar.casa.api.storyblok :as api])
            #?(:cljs ["react-bootstrap" :refer [Stack Container Button]])
            [com.sagar.casa.ui.reagent :refer [with-reagent]]
            [hyperfiddle.electric :as e])
  #?(:cljs (:require-macros com.sagar.casa.ui.reagent)))


(defn not-found [{:keys [image text]}]
  #?(:cljs
     [:> Container {:data-theme :light
                    :class-name "p-5"
                    :style {:text-align :center}}
      [:> Stack {:class-name "mx-auto p-5 gap-5"}
       [:img {:src image}]
       [:p {:style {:white-space :pre-line}} text]]]))


(e/defn NotFound []
  (e/client
   (let [content (e/server (api/get-story :not-found))]
     (with-reagent not-found content))))
