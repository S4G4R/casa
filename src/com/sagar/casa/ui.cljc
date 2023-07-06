(ns com.sagar.casa.ui
  (:require #?(:cljs [com.sagar.casa.ui.header :refer [Header]])
            #?(:cljs [com.sagar.casa.ui.footer :refer [Footer]])
            #?(:cljs [reagent.core :as r])
            #?(:cljs ["react-bootstrap" :refer [Button Card Container
                                                Col Modal Row Stack]])
            [com.sagar.casa.reagent :refer [with-reagent]]
            [hyperfiddle.electric :as e]
            [hyperfiddle.electric-dom2 :as dom])
  #?(:cljs (:require-macros com.sagar.casa.reagent)))


#?(:cljs (def modal-shown (r/atom false)))

(defn Body [cards]
  #?(:cljs
     [:> Container {:fluid true :class-name "p-5 overflow-auto"}
      [:> Stack {:gap 3 :class-name "col-md-8 mx-auto"}
       (for [{:keys [title date text]} cards]
         [:> Card
          [:> (.-Header Card)
           [:> Row
            [:> Col {:class-name "text-start"} title]
            [:> Col {:class-name "text-end"} date]]]
          [:> (.-Body Card)
           [:> (.-Title Card) title]
           [:> Button {:variant "primary"
                       :on-click #(swap! modal-shown not)}
            text]]])]
      [:> Modal {:centered true
                 :animation false
                 :show @modal-shown
                 :on-hide #(reset! modal-shown false)}
       [:> (.-Header Modal) {:close-button true}]
       [:> (.-Body Modal) {}]]]))


(def cards [{:title "ABC" :date "20/05/2023" :text "Hello!1"}
            {:title "ABC2" :date "21/05/2023" :text "Hello!2"}
            {:title "ABC2" :date "21/05/2023" :text "Hello!2"}
            {:title "ABC2" :date "21/05/2023" :text "Hello!2"}
            {:title "ABC2" :date "21/05/2023" :text "Hello!2"}
            {:title "ABC2" :date "21/05/2023" :text "Hello!2"}
            {:title "ABC2" :date "21/05/2023" :text "Hello!2"}])


(e/defn Main []
  (e/client
   (with-reagent #?(:cljs Header))
   (dom/div
    (dom/props {:style {:padding-top "56px"
                        :padding-bottom "56px"
                        :min-height "100vh"}})
    (with-reagent Body cards))
   (with-reagent #?(:cljs Footer))))
