(ns com.sagar.casa.server
  (:require [com.sagar.casa.middleware :as middleware]
            com.sagar.casa.ui
            [donut.system :as ds]
            [hyperfiddle.electric :as e]
            [hyperfiddle.electric-ring-adapter :as electric-ring]
            [ring.adapter.jetty :as ring]
            [ring.middleware.cookies :as cookies]
            [ring.middleware.params :refer [wrap-params]]
            [shadow.cljs.devtools.api :as shadow-api]
            [shadow.cljs.devtools.server :as shadow-server]
            [taoensso.timbre :as timbre])
  (:import (org.eclipse.jetty.server.handler.gzip
            GzipHandler)
           (org.eclipse.jetty.websocket.server.config
            JettyWebSocketServletContainerInitializer
            JettyWebSocketServletContainerInitializer$Configurator)))


(def VERSION
  ;; See Dockerfile
  (not-empty (System/getProperty "HYPERFIDDLE_ELECTRIC_APP_VERSION")))


(defn electric-websocket-middleware
  "Open a websocket and boot an Electric server program defined by `entrypoint`.
  Takes:
  - a ring handler `next-handler` to call if the request is not a websocket upgrade (e.g. the next middleware in the chain),
  - a `config` map eventually containing {:hyperfiddle.electric/user-version <version>} to ensure client and server share the same version,
    - see `hyperfiddle.electric-ring-adapter/wrap-reject-stale-client`
  - an Electric `entrypoint`: a function (fn [ring-request] (e/boot-server {} my-ns/My-e-defn ring-request))
  "
  [next-handler config entrypoint]
  (-> (electric-ring/wrap-electric-websocket next-handler entrypoint)
      (cookies/wrap-cookies)
      (electric-ring/wrap-reject-stale-client config)
      (wrap-params)))

(defn middleware [config entrypoint]
  (-> (middleware/http-middleware config)  ; 2. serve regular http content
      (electric-websocket-middleware config entrypoint))) ; 1. intercept electric websocket

(defn- add-gzip-handler!
  "Makes Jetty server compress responses. Optional but recommended."
  [server]
  (.setHandler server
               (doto (GzipHandler.)
                 (.setIncludedMimeTypes
                  (into-array ["text/css"
                               "text/plain"
                               "text/javascript"
                               "application/javascript"
                               "application/json"
                               "image/svg+xml"]))
                 (.setMinGzipSize 1024)
                 (.setHandler (.getHandler server)))))

(defn- configure-websocket!
  "Tune Jetty Websocket config for Electric compat."
  [server]
  (JettyWebSocketServletContainerInitializer/configure
   (.getHandler server)
   (reify JettyWebSocketServletContainerInitializer$Configurator
     (accept [_this _servletContext wsContainer]
       (.setIdleTimeout wsContainer (java.time.Duration/ofSeconds 60))
       ;; 100M - temporary
       (.setMaxBinaryMessageSize wsContainer (* 100 1024 1024))
       ;; 100M - temporary
       (.setMaxTextMessageSize wsContainer (* 100 1024 1024))))))


(def entrypoint
  (fn [handler]
    (e/boot-server {} com.sagar.casa.ui/Root handler)))


(def server
  #::ds{:start  (fn [{{:keys [host port] :as opts} ::ds/config}]
                  ;; Start electric compiler and server
                  (timbre/warn (str "Starting server on " host ":" port))
                  (ring/run-jetty (middleware opts entrypoint) opts))
        :config {:host (ds/ref [:env :http-host])
                 :port (ds/ref [:env :http-port])
                 :join? false
                 :resources-path "public"
                 :manifest-path "public/js/manifest.edn"
                 :configurator (fn [server]
                                 (configure-websocket! server)
                                 (add-gzip-handler! server))}})


(def dev-server
  #::ds{:start  (fn [{{:keys [host port] :as opts} ::ds/config}]
                  ;; Start shadowcljs server
                  (shadow-server/start!)
                  (shadow-api/watch :dev)
                  ;; Build client artifact
                  (shadow-api/dev :dev)
                  ;; Start electric compiler and server
                  (timbre/warn (str "Starting server on " host ":" port))
                  (ring/run-jetty (middleware opts entrypoint) opts))
        :stop   (fn [{server ::ds/instance}]
                  (timbre/warn "Stopping HTTP Server...")
                  (.stop server)
                  ;; Stop shadowcljs server
                  (shadow-api/stop-worker :dev)
                  (shadow-server/stop!))
        :config {:host (ds/ref [:env :http-host])
                 :port (ds/ref [:env :http-port])
                 :join? false
                 :resources-path "public"
                 :manifest-path "public/js/manifest.edn"
                 :configurator (fn [server]
                                 (configure-websocket! server)
                                 (add-gzip-handler! server))}})
