(ns com.sagar.casa.ui.blog
  (:require #?(:cljs [reagent.core :as r])
            #?(:cljs ["react-bootstrap" :refer [Col Row Container Modal
                                                ListGroup]])
            [com.sagar.casa.ui.reagent :refer [with-reagent]]
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


#?(:clj
   (def data
     (map-indexed
      #(assoc %2 :id %1)
      [{:title "ABC2" :date "20/05/2023"}
       {:title "ABC2" :date "21/05/2023"}
       {:title "ABC2" :date "21/05/2023"}
       {:title "ABC2" :date "21/05/2023"}
       {:title "ABC2" :date "21/05/2023"}
       {:title "ABC2" :date "21/05/2023"}
       {:title "ABC2" :date "21/05/2023"}])))

#?(:clj (def !blogs (atom data)))

(e/def blogs (e/server (e/watch !blogs)))


(e/defn Blog []
  (e/client
   (dom/div
    (dom/props {:style {:padding-top "56px"
                        :padding-bottom "56px"
                        :min-height "100vh"}})
    (with-reagent BlogList blogs))))


(comment
  (swap! data conj {:title "ABC" :date "20/05/2023" :text "Hello!1"})

  (reset! data nil))
