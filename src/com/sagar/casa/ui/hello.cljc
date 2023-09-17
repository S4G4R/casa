(ns com.sagar.casa.ui.hello
  (:require #?(:cljs ["react-bootstrap" :refer [Container]])
            [com.sagar.casa.ui.reagent :refer [with-reagent]]
            [hyperfiddle.electric :as e]
            [hyperfiddle.electric-dom2 :as dom])
  #?(:cljs (:require-macros com.sagar.casa.ui.reagent)))


(def !client-text (atom nil))
(def text-input-id (str (random-uuid)))


(defn HelloText []
  #?(:cljs
     [:> Container {:fluid true :class-name "p-5 overflow-auto"}
      [:input
       {:id text-input-id :type :text}]
      [:button
       {:type :submit
        :on-click #(->> (.getElementById js/document text-input-id)
                        .-value
                        (reset! !client-text))}
       "Submit"]]))

#?(:clj (defonce !server-text (atom nil)))
#?(:clj (e/def server-text (e/server (e/watch !server-text))))


(e/defn Hello []
  (e/client
   (with-reagent HelloText)
   (when-some [client-text (e/watch !client-text)]
     (e/server
      (reset! !server-text client-text)
      (prn server-text))
     (dom/h1 (dom/text client-text)))))


(comment
  ;; Will always be nil because the client holds the value, can't access
  ;; here
  @!client-text
  ;; Works!
  @!server-text
  )
