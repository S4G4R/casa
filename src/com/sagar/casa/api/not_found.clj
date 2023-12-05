(ns com.sagar.casa.api.not-found
  (:require [camel-snake-kebab.core :refer [->kebab-case-keyword]]
            [camel-snake-kebab.extras :refer [transform-keys]]
            [com.sagar.casa.api.config :refer [config]]
            [cheshire.core :as cheshire]))


(defn ^:private transform-story
  "Returns the StoryBlok story after applying transformations"
  [{{:keys [caption] {image :filename} :image} :content}]
  {:text caption
   :image image})


(defn not-found
  "Returns the content for the not found page"
  []
  (->> (str (:storyblok-base-url (config))
            "/not-found?token="
            (:storyblok-token (config)))
       slurp
       cheshire/parse-string
       (transform-keys ->kebab-case-keyword)
       :story
       transform-story))


(comment
  (not-found))
