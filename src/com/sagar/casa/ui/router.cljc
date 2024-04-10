(ns com.sagar.casa.ui.router
  (:require [com.sagar.casa.ui.blog :refer [BlogList BlogPost]]
            [com.sagar.casa.ui.not-found :refer [NotFound]]
            #?(:cljs [com.sagar.casa.ui.home :refer [Home]])
            [com.sagar.casa.ui.routes :as routes]
            [com.sagar.casa.ui.reagent :refer [with-reagent]]
            [hyperfiddle.electric :as e]
            [missionary.core :as m]
            [reitit.core :as rr]
            #?(:cljs [cljs.js :as js])
            #?(:cljs [reitit.frontend.easy :as rfe]))
  #?(:cljs (:require-macros com.sagar.casa.ui.reagent)))

;; Reference
;; https://github.com/lumberdev/tesserae/blob/cea33f19b46892abb78feb99d51af2dd54849435/src/tesserae/ui/app.cljs


(def router
  (rr/router
   [[routes/home               {:name :home
                                :title "Home"}]
    [routes/blog               {:name :blog
                                :title "Blog"}]
    [(routes/blogpost ":slug") {:name :blog-post
                                :title "Blog"}]
    [routes/not-found          {:name :not-found
                                :title "Not Found"}]]))


#?(:cljs
   (defn set-page-title! [route-match]
     (set! (.-title js/document) (or (->> route-match :data :title)
                                     "Not Found"))))


(e/def re-router
  (->> (m/observe
        (fn [!] #?(:cljs (rfe/start! router ! {:use-fragment false}))))
       (m/relieve {})
       new))


(e/defn Router []
  (let [{:as match :keys [data query-params path-params]} re-router
        route (some-> data :name)]
    (e/client
     #?(:cljs (set-page-title! match))
     (case route
       :home (with-reagent #?(:cljs Home))
       :blog (BlogList.)
       :blog-post (BlogPost. (:slug path-params))
       :not-found (NotFound.)
       (NotFound.)))))
