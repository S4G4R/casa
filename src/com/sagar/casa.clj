(ns com.sagar.casa
  (:require [com.sagar.casa.logging :as log]
            [com.sagar.casa.server :as server]
            ;; If not loaded, leads to symbol unresolved errors, how do
            ;; we fix/dynamically load?
            [com.sagar.casa.ui.blog]
            [donut.system :as ds]
            [environ.core :refer [env]])
  (:gen-class))


(defn env-map
  []
  {:log-level (read-string (env :log-level))
   :http-host (env :http-host)
   :http-port (read-string (env :http-port))})


(def config
  {:logging log/logger
   :server server/server})


(def dev-config
  (assoc config :server server/dev-server))


(defmethod ds/named-system :casa
  [_]
  {::ds/defs {:env (env-map)
              :app config}})


(defn -main
  [& _]
  (ds/start :casa))
