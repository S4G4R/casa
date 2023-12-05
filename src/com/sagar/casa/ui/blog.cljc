(ns com.sagar.casa.ui.blog
  (:require #?(:cljs ["react-bootstrap" :refer [Card Col Container ListGroup Row]])
            #?(:cljs ["interweave" :refer [Markup]])
            #?(:clj [com.sagar.casa.api.blog :as api])
            [com.sagar.casa.ui.not-found :refer [NotFound]]
            [com.sagar.casa.ui.reagent :refer [with-reagent]]
            [hyperfiddle.electric :as e])
  #?(:cljs (:require-macros com.sagar.casa.ui.reagent)))


(defn blog-post [{:keys [title html-body description timestamp]}]
  #?(:cljs
     [:> Container {:fluid true
                    :data-theme :dark
                    :class-name "p-5 text-white"}
      [:> Card {:bg :dark}
       [:> (.-Header Card)
        [:> (.-Title Card) [:big title]]
        [:> (.-Subtitle Card) description]]
       [:> (.-Body Card) {:style {:--pico-color :white}}
        [:> Markup {:attributes {:style {:white-space :pre-line}}
                    :content html-body}]]]]))


(e/defn BlogPost [slug]
  (e/client
   (if-let [blog (e/server (api/blog slug))]
     (with-reagent blog-post blog)
     (NotFound.))))


(defn blog-entry [{:keys [title description timestamp slug]}]
  #?(:cljs
     [:> (.-Item ListGroup) {:action true
                             :href slug
                             :class-name "mb-3"}
      [:> Row
       [:> Col {:class-name "text-start"} title]
       [:> Col {:class-name "text-end"} timestamp]]]))


(defn blog-list [blogs]
  #?(:cljs
     [:> Container {:fluid true :class-name "p-5 overflow-auto"}
      [:> ListGroup {:class-name "mx-auto"}
       (for [{id :id :as entry} blogs]
         [:div {:key id} (blog-entry entry)])]]))


(e/defn BlogList []
  (e/client
   (if-let [blogs (e/server (api/blogs))]
     (with-reagent blog-list blogs)
     (NotFound.))))
