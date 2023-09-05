(ns com.sagar.casa.ui.blog
  (:require #?(:cljs [reagent.core :as r])
            #?(:cljs ["react-bootstrap" :refer [Col Row Container Modal
                                                ListGroup]])
            [com.sagar.casa.ui.reagent :refer [with-reagent]]
            [com.sagar.casa.data :refer [blogs]]
            [hyperfiddle.electric :as e]
            [hyperfiddle.electric-dom2 :as dom])
  #?(:cljs (:require-macros com.sagar.casa.ui.reagent)))


#?(:cljs (def modal-shown (r/atom false)))


(defn BlogEntry [{:keys [id title date]}]
  #?(:cljs
     [:> (.-Item ListGroup) {:action true
                             :on-click #(reset! modal-shown not)
                             :class-name "mb-3"}
      [:> Row
       [:> Col {:class-name "text-start"} title]
       [:> Col {:class-name "text-end"} date]]]))


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
   (dom/div
    (dom/props {:style {:padding-top "56px"
                        :padding-bottom "56px"
                        :min-height "100vh"}})
    (with-reagent BlogList blogs))))
