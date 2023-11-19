(ns com.sagar.casa.ui
  (:require #?(:cljs [com.sagar.casa.ui.header :refer [Header]])
            #?(:cljs [com.sagar.casa.ui.footer :refer [Footer]])
            [com.sagar.casa.ui.blog]
            [com.sagar.casa.ui.hello]
            #?(:cljs [com.sagar.casa.ui.home])
            [com.sagar.casa.ui.reagent :refer [with-reagent]]
            [com.sagar.casa.ui.router :refer [Router]]
            [com.sagar.casa.ui.routes]
            [hyperfiddle.electric :as e]
            [hyperfiddle.electric-dom2 :as dom])
  #?(:cljs (:require-macros com.sagar.casa.ui.reagent)))


(e/defn Root []
  (e/client
   (dom/div
    (dom/props {:style {:padding-top "56px"
                        :padding-bottom "56px"
                        :min-height "100vh"}})
    (with-reagent #?(:cljs Header))
    (Router.)
    (with-reagent #?(:cljs Footer)))))
