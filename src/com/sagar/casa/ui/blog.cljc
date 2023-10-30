(ns com.sagar.casa.ui.blog
  (:require #?(:cljs [reagent.core :as r])
            #?(:cljs ["react-bootstrap" :refer [Col Container ListGroup
                                                Modal Row]])
            #?(:clj [com.sagar.casa.api.blog :as blog])
            [com.sagar.casa.ui.reagent :refer [with-reagent]]
            [hyperfiddle.electric :as e])
  #?(:cljs (:require-macros com.sagar.casa.ui.reagent)))


#?(:cljs (def modal-shown (r/atom false)))


(defn BlogEntry [{:keys [title description timestamp]}]
  #?(:cljs
     [:> (.-Item ListGroup) {:action true
                             :href "/hello"
                            ;;  :on-click #(reset! modal-shown not)
                             :class-name "mb-3"}
      [:> Row
       [:> Col {:class-name "text-start"} title]
       [:> Col description]
       [:> Col {:class-name "text-end"} timestamp]]]))


(defn BlogList [blogs]
  #?(:cljs
     [:> Container {:fluid true :class-name "p-5 overflow-auto"}
      [:> ListGroup {:class-name "mx-auto"}
       (for [entry blogs] [:div {:key (:id entry)} (BlogEntry entry)])]
      [:> Modal {:centered true
                 :animation false
                 :show @modal-shown
                 :on-hide #(reset! modal-shown false)}
       [:> (.-Header Modal) {:close-button true}]
       [:> (.-Body Modal) {}]]]))


(e/defn Blog []
  (e/client
   (with-reagent BlogList (e/server (blog/blogs)))))
