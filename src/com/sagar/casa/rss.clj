(ns com.sagar.casa.rss
  (:require [com.sagar.casa.api.storyblok :as api]
            [clojure.java.io :as io]
            [clojure.set :refer [rename-keys]]
            [clj-rss.core :as rss]))


(defn rss-feed
  []
  (->> (api/get-story :blogs {:include-body? true})
       (map #(select-keys % [:id :title :slug :description
                             :html-body :first-published-at]))
       (map #(assoc % :link (str "https://sagarvrajalal.com/" (:slug %))))
       (map #(assoc % :description (str "<![CDATA["
                                        (slurp (io/resource "rss-header.html"))
                                        (:html-body %)
                                        (slurp (io/resource "rss-footer.html"))
                                        "]]>")))
       (map #(rename-keys % {:first-published-at :pubDate
                             :id :guid}))
       (map #(dissoc % :slug :html-body))
       (apply rss/channel-xml
              {:title "Sagar Vrajalal"
               :description "Sagar's Blog"
               :link "https://sagarvrajalal.com/blog"})))
