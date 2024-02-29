(ns com.sagar.casa.api.storyblok
  (:require [camel-snake-kebab.core :refer [->kebab-case-keyword
                                            ->snake_case_string]]
            [camel-snake-kebab.extras :refer [transform-keys]]
            [cheshire.core :as cheshire]
            [clojure.string :as string]
            [donut.system :as ds]
            [java-time.api :as jt]
            [ring.util.codec :refer [form-encode]]
            [storyblok-clj.core :as sb]
            [taoensso.timbre :as timbre]))


(defn timestamp->date
  "Converts an ISO timestamp to a date

  eg. `2023-12-05T15:26:17.541216Z` -> `05/12/2023`"
  [timestamp]
  (let [{:keys [year
                month-of-year
                day-of-month]} (jt/as-map (jt/zoned-date-time timestamp))]
    (str (format "%02d" day-of-month)
         " "
         (string/capitalize (jt/month month-of-year))
         ", "
         year)))


(defn config
  "Returns a configuration map used for the StoryBlok APIs"
  []
  (-> (ds/system :casa)
      (get-in [::ds/defs :env])
      (select-keys [:storyblok-token :storyblok-base-url])))


(defn url
  [& [path opts]]
  (->> (:storyblok-token (config))
       (assoc opts :token)
       (transform-keys ->snake_case_string)
       form-encode
       (str (:storyblok-base-url (config)) path "?")))


(defn get-content
  [url]
  (try
    (->> (slurp url)
         cheshire/parse-string
         (transform-keys ->kebab-case-keyword))
    (catch java.io.FileNotFoundException e
      (timbre/error e)
      (timbre/error "Could not fetch content"))))


(defmulti transform-story
  (fn [type story]
    (if (nil? story)
      :default
      (keyword type))))


(defmulti get-story
  (fn [type & [opts]]
    (keyword type)))


(defmethod transform-story :blog
  [_ {:keys [id first-published-at full-slug]
      {:keys [body title description]} :content}]
  {:id id
   :timestamp (timestamp->date first-published-at)
   :slug full-slug
   :html-body (sb/richtext->html body)
   :title title
   :description description})


(defmethod transform-story :blogs
  [_ blogs]
  (map #(transform-story :blog %) blogs))


(defmethod transform-story :not-found
  [_ {{:keys [caption] {image :filename} :image} :content}]
  {:text caption
   :image image})


(defmethod transform-story :default
  [_ story]
  story)


(defmethod get-story :blog
  [type slug]
  (->> (url (str "/blog/" slug))
       get-content
       :story
       (transform-story type)))


(defmethod get-story :blogs
  [type]
  (->> (url nil {:starts-with "blog"
                 :excluding-fields "body"})
       get-content
       :stories
       (transform-story type)))


(defmethod get-story :not-found
  [type]
  (->> (url "/not-found")
       get-content
       :story
       (transform-story type)))


(comment
  (get-story :blog "first")
  (get-story :blogs)
  (get-story :not-found)
  )
