(ns com.sagar.casa.ui.hello
  (:require [hyperfiddle.electric :as e]
            #?(:cljs ["react-bootstrap" :refer [Container]])
            [com.sagar.casa.ui.reagent :refer [with-reagent]])
  #?(:cljs (:require-macros com.sagar.casa.ui.reagent)))


(defn HelloText []
  #?(:cljs
     (do
       (js/console.log "Here!")
       [:> Container {:fluid true :class-name "p-5 overflow-auto"}
        [:div [:b "Hello!"]]])))


(e/defn Hello []
  (e/client
   (with-reagent HelloText)))
