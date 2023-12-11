(ns com.sagar.casa.api.blog
  (:require [camel-snake-kebab.core :refer [->kebab-case-keyword]]
            [camel-snake-kebab.extras :refer [transform-keys]]
            [cheshire.core :as cheshire]
            [com.sagar.casa.api.config :refer [config]]
            [java-time.api :as jt]
            [storyblok-clj.core :as sb]
            [taoensso.timbre :as timbre]))


(defn timestamp->date
  "Converts an ISO timestamp to a date

  eg. `2023-12-05T15:26:17.541216Z` -> `05/12/2023`"
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
  [{:keys [id first-published-at full-slug]
    {:keys [body title description]} :content}]
  {:id id
   :timestamp (timestamp->date first-published-at)
   :slug full-slug
   :html-body (sb/richtext->html body)
   :title title
   :description description})


(defn blogs
  "Returns a list of all the blogposts, `nil` if failed to fetch"
  []
  (try
    (->> (str (:storyblok-base-url (config))
            ;; Don't include body of the blog post in the list
              (str "?starts_with=blog&excluding_fields=body&token=")
              (:storyblok-token (config)))
         slurp
         cheshire/parse-string
         (transform-keys ->kebab-case-keyword)
         :stories
         (map transform-story))
    (catch java.io.FileNotFoundException e
      (timbre/error e)
      (timbre/error "Could not fetch blogs"))))


(defn blog
  "Returns the blogpost corresponding the given slug, `nil` if not
  found"
  [slug]
  (try
    (->> (str (:storyblok-base-url (config))
              "/blog/" slug "?token="
              (:storyblok-token (config)))
         slurp
         cheshire/parse-string
         (transform-keys ->kebab-case-keyword)
         :story
         transform-story)
    (catch java.io.FileNotFoundException e
      (timbre/error e)
      (timbre/error "Could not fetch blog"))))

(comment
  (blogs)
  (blog "first")
  )
