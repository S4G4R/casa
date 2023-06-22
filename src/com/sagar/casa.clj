(ns com.sagar.casa
  (:require [donut.system :as ds]
            [com.sagar.casa.logging :as log]
            [com.sagar.casa.server :as server]))


(def env
  {:log-level :info
   :env :prod
   :http-host "0.0.0.0"
   :http-port 7000})


(def config
  {:logging log/logger
   :server server/server})


(defmethod ds/named-system :casa
 [_]
 {::ds/defs {:env env
             :app config}})


(defn -main
  [& _]
  (ds/start :casa))
