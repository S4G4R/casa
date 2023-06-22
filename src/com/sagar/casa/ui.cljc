(ns com.sagar.casa.ui
  (:require [hyperfiddle.electric :as e]
            [hyperfiddle.electric-dom2 :as dom]
            [hyperfiddle.electric-ui4 :as ui]
            #?(:cljs [com.sagar.casa.ui.header :refer [Header]])
            #?(:cljs [com.sagar.casa.ui.footer :refer [Footer]])
            #?(:cljs [reagent.core :as r])
            #?(:cljs ["react-dom/client" :as ReactDom])
            #?(:cljs ["react-bootstrap" :refer [Container Col Row Modal
                                                Button Stack Card]]))
  #?(:cljs (:require-macros com.sagar.casa.ui)))

(def ReactRootWrapper
  #?(:cljs
     (r/create-class
      {:component-did-mount (fn [this] (js/console.log "mounted"))
       :render (fn [this]
                 (let [[_ Component & args] (r/argv this)]
                   (into [Component] args)))})))

(def react-root-hook "See `e/with`"
  #?(:clj  dom/unsupported
     :cljs (fn ([x] (.unmount x))
             ([x y] (.insertBefore (.-parentNode x) x y)))))

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
    (binding [dom/node (create-root dom/node)]
      (new (e/hook react-root-hook dom/node
                   (e/fn [] dom/keepalive
                     (render dom/node ~@args)))))))


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
