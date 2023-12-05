(ns com.sagar.casa.ui.not-found
  (:require #?(:clj [com.sagar.casa.api.not-found :as api])
            #?(:cljs ["react-bootstrap" :refer [Stack Container Button]])
            [com.sagar.casa.ui.reagent :refer [with-reagent]]
            [hyperfiddle.electric :as e])
  #?(:cljs (:require-macros com.sagar.casa.ui.reagent)))


(defn not-found [{:keys [image text]}]
  #?(:cljs
     [:> Container {:data-theme :light
                    :class-name "p-5"
                    :style {:text-align :center}}
      [:> Stack
       [:img {:src image}]
       [:p text]]]))

(e/defn NotFound []
  (e/client
   (let [content (e/server (api/not-found))]
     (with-reagent not-found content))))