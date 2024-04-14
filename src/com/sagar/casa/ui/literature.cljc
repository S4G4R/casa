(ns com.sagar.casa.ui.literature
  (:require #?(:cljs ["react-bootstrap" :refer [Container Stack]])
            #?(:cljs ["interweave" :refer [Markup]])
            #?(:clj [com.sagar.casa.api.storyblok :as api])
            #?(:cljs [reagent.core :refer [as-element]])
            #?(:cljs [clojure.string :as string])
            [com.sagar.casa.ui.not-found :refer [NotFound]]
            [com.sagar.casa.ui.reagent :refer [with-reagent]]
            [hyperfiddle.electric :as e])
  #?(:cljs (:require-macros com.sagar.casa.ui.reagent)))


(defn literature-body [{body :body}]
  (prn body)
  #?(:cljs
     [:> Container {:data-theme :light
                    :fluid true}
      [:div {:class-name "pt-3 col-md-6 mx-auto"}
       [:> Stack {:gap 1}
        [:div
         [:h2 "Literature"]
         [:hr {:style {:border-color :black}}]]
        [:> Markup {:attributes {:style {:white-space :pre-wrap}}
                    :transform #(condp = (string/lower-case (.-tagName %1))
                                  "a"
                                  (as-element [:a {:href (.-href %1)
                                                  ;; Links should open
                                                  ;; in a new tab
                                                   :target "_blank"}
                                               %2])
                                 ;; Other elements as they are
                                  (as-element
                                   [(string/lower-case (.-tagName %1))
                                    %2]))
                    :content body}]]]]))


(e/defn Literature []
  (e/client
   (if-let [content (e/server (api/get-story :literature))]
     (with-reagent literature-body content)
     (NotFound.))))
