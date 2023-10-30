(ns com.sagar.casa
  (:require [clojure.string :as str]
            [com.sagar.casa.logging :as log]
            [com.sagar.casa.server :as server]
            [com.sagar.casa.ui]
            [donut.system :as ds]
            [environ.core :refer [env]]
            [shadow.cljs.devtools.api :as shadow-api]
            [shadow.cljs.devtools.server :as shadow-server]
            [taoensso.timbre :as timbre])
  (:gen-class))


(defn env-map
  []
  {:log-level (read-string (env :log-level))
   :http-host (env :http-host)
   :http-port (read-string (env :http-port))
   :storyblok-base-url (env :storyblok-base-url)
   :storyblok-token (env :storyblok-token)})


(def config
  {:logging log/logger
   :server server/server})


(def dev-config
  (assoc config :server server/dev-server))


(defmethod ds/named-system :casa
  [_]
  {::ds/defs {:env (env-map)
              :app config}})


(defn build
  "Build client artifact"
  []
  (when (str/blank? server/VERSION)
    (throw
     (ex-info "HYPERFIDDLE_ELECTRIC_SERVER_VERSION jvm property must be set in prod"
              {})))
  (shadow-server/start!)
  (timbre/warn "Building client version" server/VERSION)
  ;; Build release
  (shadow-api/release
   :prod
   {:config-merge
    [{:closure-defines
      {'hyperfiddle.electric-client/VERSION server/VERSION}}]})
  (shadow-server/stop!))


(defn -main
  [& _]
  (ds/start :casa))
