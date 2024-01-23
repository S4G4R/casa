(ns ^:dev/always user
  (:require com.sagar.casa.ui
            hyperfiddle.electric
            hyperfiddle.electric-dom2
            hyperfiddle.rcf))


(def entrypoint
  (hyperfiddle.electric/boot-client {} com.sagar.casa.ui/Root nil))

(defonce reactor nil)

(defn ^:dev/after-load ^:export start! []
  (assert (nil? reactor) "reactor already running")
  (set! reactor (entrypoint
                 #(js/console.log "Reactor success:" %)
                 #(js/console.error "Reactor failure:" %))))


(defn ^:dev/before-load stop! []
  (when reactor (reactor)) ; teardown
  (set! reactor nil))
