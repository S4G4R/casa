(ns com.sagar.casa.api.blog
  (:require [camel-snake-kebab.core :refer [->kebab-case-keyword]]
            [camel-snake-kebab.extras :refer [transform-keys]]
            [cheshire.core :as cheshire]
            [donut.system :as ds]
            [java-time.api :as jt]
            [storyblok-clj.core :as sb]))


(defn ^:private config []
  (-> (ds/system :casa)
      (get-in [::ds/defs :env])
      (select-keys [:storyblok-token :storyblok-base-url])))


(defn timestamp->str
  [timestamp]
  (let [{:keys [year
                month-of-year
                day-of-month]} (jt/as-map
                                (jt/zoned-date-time timestamp))]
    (str (format "%02d" day-of-month)
         "/"
         month-of-year
         "/"
         year)))


(defn ^:private transform-story
  "Returns the StoryBlok story after applying transformations"
  [{:keys [first-published-at full-slug]
    {:keys [body title description]} :content}]
  {:timestamp (timestamp->str first-published-at)
   :slug full-slug
   :body (sb/richtext->html body)
   :title title
   :description description})


(defn blogs
  "Returns a list of all the blogposts"
  []
  (->> (str (:storyblok-base-url (config))
            ;; Don't include body of the blog post in the list
            (str "?excluding_fields=body&token=")
            (:storyblok-token (config)))
       slurp
       cheshire/parse-string
       (transform-keys ->kebab-case-keyword)
       :stories
       (map transform-story)))


(defn blog
  "Returns the blogpost corresponding the given slug"
  [slug]
  (->> (str (:storyblok-base-url (config))
            "/blog/" slug "?token="
            (:storyblok-token (config)))
       slurp
       cheshire/parse-string
       (transform-keys ->kebab-case-keyword)
       :story
       transform-story))

(comment
  (blogs)
  (blog "first")
  )
