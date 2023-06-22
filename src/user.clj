(ns user
  (:require [donut.system :as ds]
            [donut.system.repl :refer [start stop restart]]
            [com.sagar.casa :refer [env config]]))


(defmethod ds/named-system :donut.system/repl
 [_]
 {::ds/defs {:env (assoc env :env :dev)
             :app config}})
