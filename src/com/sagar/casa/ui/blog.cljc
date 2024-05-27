(ns com.sagar.casa.ui.blog
  (:require #?(:cljs ["react-bootstrap" :refer [Col Container ListGroup Row Stack]])
            #?(:cljs ["interweave" :refer [Markup]])
            #?(:clj [com.sagar.casa.api.storyblok :as api])
            #?(:cljs [re-highlight.core :refer [highlight]])
            #?(:cljs [reagent.core :refer [as-element]])
            #?(:cljs [clojure.string :as string])
            #?(:cljs [com.sagar.casa.ui.routes :as routes])
            [com.sagar.casa.ui.not-found :refer [NotFound]]
            [com.sagar.casa.ui.reagent :refer [with-reagent]]
            [hyperfiddle.electric :as e])
  #?(:cljs (:require-macros com.sagar.casa.ui.reagent)))


#?(:cljs
   (defn set-page-title! [title]
     (set! (.-title js/document) title)))


(defn blog-post [{:keys [title html-body description date]}]
  #?(:cljs (set-page-title! title))
  #?(:cljs
     [:> Container {:data-theme :light
                    :fluid true
                    :class-name "pt-3 col-md-6 mx-auto"}
      [:> Stack {:gap 1}
       [:a {:style {:text-decoration :none}
            :href routes/blog}
        "↰ Back to list"]
       [:div
        [:i date]
        [:h2 title]
        [:hr {:style {:border-color :black}}]]
       [:> Markup {:attributes {:style {:white-space :pre-wrap}}
                   :transform #(condp = (string/lower-case (.-tagName %1))
                                  ;; Syntax highlighting for code blocks
                                 "code"
                                 (as-element [highlight {:language "clojure"}
                                              %2])
                                 "a"
                                 (as-element [:a {:href (.-href %1)
                                                   ;; Links should open
                                                   ;; in a new tab
                                                  :target "_blank"}
                                              %2])
                                 "img"
                                 (as-element
                                  ;; Center images
                                  ;; TODO: Fix div cannot be inside p
                                  [:div {:style {:text-align :center}}
                                   [:img {:src (.-src %1)}]])
                                 ;; Other elements as they are
                                 (as-element
                                  [(string/lower-case (.-tagName %1))
                                   %2]))
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
     [:> Container {:data-theme :light
                    :fluid true
                    :class-name "pt-3 col-md-6 overflow-auto mx-auto"}
      [:> Stack {:gap 2}

       [:> Row
        [:> Col {:class-name "text-start"}
         [:a {:style {:text-decoration :none}
              :href routes/home}
          "↰ Home"]]
        [:> Col {:class-name "text-end"}
         [:a {:style {:text-decoration :none}
              :href routes/rss}
          "RSS " [:i {:class-name "fa fa-rss"}]]]]
       [:div
        [:h2 "Blog"]
        [:hr {:style {:border-color :black}}]]
       [:> ListGroup
        (for [{id :id :as entry} blogs]
          [:div {:key id} (blog-entry entry)])]]]))


(e/defn BlogList []
  (e/client
   (if-let [blogs (e/server (api/get-story :blogs))]
     (with-reagent blog-list blogs)
     (NotFound.))))
