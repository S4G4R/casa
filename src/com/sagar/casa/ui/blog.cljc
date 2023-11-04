(ns com.sagar.casa.ui.blog
  (:require #?(:cljs ["react-bootstrap" :refer [Col Container ListGroup
                                                Card Row]])
            #?(:cljs ["interweave" :refer [Interweave Markup]])
            #?(:clj [com.sagar.casa.api.blog :as api])
            [com.sagar.casa.ui.reagent :refer [with-reagent]]
            [hyperfiddle.electric :as e])
  #?(:cljs (:require-macros com.sagar.casa.ui.reagent)))


(defn blog-post [{:keys [title body description timestamp]}]
  #?(:cljs
     [:> Container {:class-name "p-5 overflow-auto"}
      [:> Card {:bg :dark}
       [:> (.-Header Card)
        [:> (.-Text Card) title]
        [:> (.-Subtitle Card)
         [:> (.-Text Card) description]]]
       [:> (.-Body Card)
        [:> (.-Text Card)
         [:> Interweave {:attributes {:style {:white-space :pre-line}}
                         :content body}]]]]]))


(e/defn BlogPost [slug]
  (e/client
   (with-reagent blog-post (e/server (api/blog slug)))))


(defn blog-entry [{:keys [title description timestamp slug]}]
  #?(:cljs
     [:> (.-Item ListGroup) {:action true
                             :href slug
                             :class-name "mb-3"}
      [:> Row
       [:> Col {:class-name "text-start"} title]
       [:> Col description]
       [:> Col {:class-name "text-end"} timestamp]]]))


(defn blog-list [blogs]
  #?(:cljs
     [:> Container {:fluid true :class-name "p-5 overflow-auto"}
      [:> ListGroup {:class-name "mx-auto"}
       (for [{id :id :as entry} blogs]
         [:div {:key id} (blog-entry entry)])]]))


(e/defn BlogList []
  (e/client
   (with-reagent blog-list (e/server (api/blogs)))))
