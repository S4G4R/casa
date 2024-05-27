(ns com.sagar.casa.rss
  (:require [com.sagar.casa.api.storyblok :as api]
            [clojure.set :refer [rename-keys]]
            [clj-rss.core :as rss]))


(defn rss-feed
  []
  (->> (api/get-story :blogs)
       (map #(select-keys % [:id :title :slug :description
                             :first-published-at]))
       (map #(assoc % :link (str "https://sagarvrajalal.com/" (:slug %))))
       (map #(dissoc % :slug))
       (map #(rename-keys % {:first-published-at :pubDate
                             :id :guid}))
       (apply rss/channel-xml
              {:title "Sagar Vrajalal"
               :description "Sagar's Blog"
               :link "https://sagarvrajalal.com/blog"})))
