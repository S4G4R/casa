(ns com.sagar.casa.ui.reagent
  (:require [hyperfiddle.electric-dom2 :as dom]
            [reagent.core :as r]
            #?(:cljs ["react-dom/client" :as ReactDom])))


#?(:cljs
   (def ReactRootWrapper
     (r/create-class
      {:component-did-mount (fn [_] (js/console.log "mounted"))
       :render (fn [this]
                 (let [[_ Component & args] (r/argv this)]
                   (into [Component] args)))})))

(defn create-root
  "See `https://reactjs.org/docs/react-dom-client.html#createroot`"
  ([node] (create-root node (str (gensym))))
  ([node identifier-prefix]
   #?(:cljs (ReactDom/createRoot
             node
             #js
              {:identifierPrefix identifier-prefix}))))

(defn render [root & args]
  #?(:cljs (.render root (r/as-element (into [ReactRootWrapper] args)))))

(defmacro with-reagent [& args]
  `(dom/div  ; React will hijack this element and empty it.
     (let [root# (create-root dom/node)]
       (render root# ~@args)
       (e/on-unmount #(.unmount root#)))))
