(ns com.sagar.casa.ui
  (:require #?(:cljs [com.sagar.casa.ui.header :refer [Header]])
            #?(:cljs [com.sagar.casa.ui.footer :refer [Footer]])
            [com.sagar.casa.ui.reagent :refer [with-reagent]]
            [com.sagar.casa.ui.blog :refer [Blog]]
            [hyperfiddle.electric :as e])
  #?(:cljs (:require-macros com.sagar.casa.ui.reagent)))


(e/defn Root []
  (e/client
   (with-reagent #?(:cljs Header))
   (Blog.)
   (with-reagent #?(:cljs Footer))))
