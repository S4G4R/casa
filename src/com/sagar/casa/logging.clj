(ns com.sagar.casa.logging
  (:require [donut.system :as ds]
            [taoensso.timbre :as timbre]))


(def logger
  #::ds{:start  (fn [{{log-level :log-level} ::ds/config}]
                  (timbre/warn "Setting log level to " log-level)
                  (timbre/set-min-level! log-level))
        :stop   (fn [_]
                  nil)
        :config {:log-level (or (keyword (ds/ref [:env :log-level]))
                                :info)}})
