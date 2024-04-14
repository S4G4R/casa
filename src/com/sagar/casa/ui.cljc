(ns com.sagar.casa.ui
  (:require #?(:cljs [com.sagar.casa.ui.header :refer [Header]])
            [com.sagar.casa.ui.blog]
            [com.sagar.casa.ui.not-found]
            #?(:cljs [com.sagar.casa.ui.home])
            [com.sagar.casa.ui.reagent :refer [with-reagent]]
            [com.sagar.casa.ui.router :refer [Router]]
            [com.sagar.casa.ui.routes]
            [hyperfiddle.electric :as e]
            [hyperfiddle.electric-dom2 :as dom])
  #?(:cljs (:require-macros com.sagar.casa.ui.reagent)))


(e/defn Root [ring-request]
  (e/server
   (binding [e/http-request ring-request]
     (e/client
      (binding [dom/node js/document.body]
        (dom/div
         (dom/props {:style {:min-height "100vh"}})
         (with-reagent #?(:cljs Header))
         (Router.)))))))
