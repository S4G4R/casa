(ns user
  (:require [donut.system :as ds]
            [donut.system.repl :refer [start stop restart]]
            [com.sagar.casa :refer [dev-config]]))


(defmethod ds/named-system :donut.system/repl
 [_]
 {::ds/defs dev-config})
