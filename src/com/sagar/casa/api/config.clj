(ns com.sagar.casa.api.config
  (:require [donut.system :as ds]))


(defn config
  "Returns a configuration map used for the StoryBlok APIs"
  []
  (-> (ds/system :casa)
      (get-in [::ds/defs :env])
      (select-keys [:storyblok-token :storyblok-base-url])))
