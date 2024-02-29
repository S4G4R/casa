(ns com.sagar.casa.ui.blog
  (:require #?(:cljs ["react-bootstrap" :refer [Col Container ListGroup Row Stack]])
            #?(:cljs ["interweave" :refer [Markup]])
            #?(:clj [com.sagar.casa.api.storyblok :as api])
            #?(:cljs [com.sagar.casa.ui.routes :as routes])
            [com.sagar.casa.ui.not-found :refer [NotFound]]
            [com.sagar.casa.ui.reagent :refer [with-reagent]]
            [hyperfiddle.electric :as e])
  #?(:cljs (:require-macros com.sagar.casa.ui.reagent)))


(defn blog-post [{:keys [title html-body description timestamp]}]
  #?(:cljs
     [:> Container {:data-theme :light
                    :fluid true}
      [:div {:class-name "pt-3 col-md-6 mx-auto"}
       [:> Stack {:gap 4}
        [:a {:style {:text-decoration :none}
             :href routes/blog}
         "↰ Back to list"]
        [:div
         [:i timestamp]
         [:h2 title]]]
       [:hr {:style {:border-color :black}}]
       [:> Markup {:attributes {:style {:white-space :pre-line}}
                   :content html-body}]]]))


(e/defn BlogPost [slug]
  (e/client
   (if-let [blog (e/server (api/get-story :blog slug))]
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
     [:> Container {:fluid true :class-name "overflow-auto"}
      [:> ListGroup {:class-name "pt-3 mx-auto"}
       (for [{id :id :as entry} blogs]
         [:div {:key id} (blog-entry entry)])]]))


(e/defn BlogList []
  (e/client
   (if-let [blogs (e/server (api/get-story :blogs))]
     (with-reagent blog-list blogs)
     (NotFound.))))
