(ns com.sagar.casa.rss
  (:require [com.sagar.casa.api.storyblok :as api]
            [clj-rss.core :as rss]))


(defn rss-feed
  []
  (->> (api/get-story :blogs)
       (map #(select-keys % [:title :slug :description]))
       (map #(assoc % :link (str "https://sagarvrajalal.com/" (:slug %))))
       (map #(dissoc % :slug))
       (apply rss/channel-xml
              {:title "Sagar Vrajalal"
               :description "Sagar's Blog"
               :link "https://sagarvrajalal.com/blog"})))
