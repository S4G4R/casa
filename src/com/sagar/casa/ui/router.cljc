(ns com.sagar.casa.ui.router
  (:require [com.sagar.casa.ui.blog :refer [Blog]]
            [com.sagar.casa.ui.hello :refer [Hello]]
            [hyperfiddle.electric :as e]
            [missionary.core :as m]
            [reitit.core :as rr]
            [reitit.frontend.easy :as rfe]))

;; Reference
;; https://github.com/lumberdev/tesserae/blob/cea33f19b46892abb78feb99d51af2dd54849435/src/tesserae/ui/app.cljs


(def router
  (rr/router
   [["/"      {:name :home
               :title "Home"}]
    ["/blog"  {:name :blog
               :title "Blog"}]
    ["/hello" {:name :hello
               :title "Hello"}]]))


(defn set-page-title! [route-match]
  (set! (.-title js/document) (->> route-match :data :title)))


(e/def re-router
  (->> (m/observe
        (fn [!] (rfe/start! router ! {:use-fragment false})))
       (m/relieve {})
       new))


(e/defn Router []
  (let [{:as match :keys [data query-params path-params]} re-router
          route (some-> data :name)]
      (set-page-title! match)
      (case route
        :home (Blog.)
        :blog (Blog.)
        :hello (Hello.)
        ;; TODO: Add 404 page
        )))
