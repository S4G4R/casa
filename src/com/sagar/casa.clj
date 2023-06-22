(ns com.sagar.casa
  (:require [donut.system :as ds]
            [com.sagar.casa.logging :as log]
            [com.sagar.casa.server :as server])
  (:gen-class))


(def env
  {:log-level :info
   :http-host "0.0.0.0"
   :http-port 7000})


(def config
  {:logging log/logger
   :server server/server})


(def dev-config
  (-> config
      (assoc :server server/dev-server)))


(defmethod ds/named-system :casa
 [_]
 {::ds/defs {:env env
             :app config}})


(defn -main
  [& _]
  (ds/start :casa))
