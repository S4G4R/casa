(ns com.sagar.casa.middleware
  (:require [clojure.edn :as edn]
            [clojure.java.io :as io]
            [clojure.string :as string]
            [ring.middleware.content-type :refer [wrap-content-type]]
            [ring.middleware.resource :refer [wrap-resource]]
            [ring.util.response :as res]))


(defn template
  "In string template `<div>$:foo/bar$</div>`, replace all instances of
  $key$ with target specified by map `m`. Target values are coerced to
  string with `str`."
  [t m]
  (reduce-kv (fn [acc k v]
               (string/replace acc (str "$" k "$") (str v)))
             t
             m))


(defn get-modules
  [manifest-path]
  (when-let [manifest (io/resource manifest-path)]
    (let [manifest-folder (when-let [folder-name (second (rseq (string/split manifest-path #"\/")))]
                            (str "/" folder-name "/"))]
      (->> (slurp manifest)
           (edn/read-string)
           (reduce (fn [r module]
                     (assoc r
                            (keyword "hyperfiddle.client.module" (name (:name module)))
                            (str manifest-folder (:output-name module))))
                   {})))))


(defn wrap-index-page
  "Server the `index.html` file with injected javascript modules from
  `manifest.edn`. `manifest.edn` is generated by the client build and
  contains javascript modules information."
  [next-handler config]
  (fn [ring-req]
    (if-let [response (res/resource-response (str (:resources-path config) "/index.html"))]
      (if-let [bag (merge config (get-modules (:manifest-path config)))]
        (-> (res/response (template (slurp (:body response)) bag)) ; TODO cache in prod mode
            (res/content-type "text/html") ; ensure `index.html` is not cached
            (res/header "Cache-Control" "no-store")
            (res/header "Last-Modified" (get-in response [:headers "Last-Modified"])))
        (-> (res/not-found (pr-str ::missing-shadow-build-manifest)) ; can't inject js modules
            (res/content-type "text/plain")))
      ;; index.html file not found on classpath
      (next-handler ring-req))))

(defn not-found-handler [_ring-request]
  (-> (res/not-found "Not found")
      (res/content-type "text/plain")))

(defn http-middleware
  [config]
  (-> not-found-handler
      (wrap-index-page config)
      (wrap-resource (:resources-path config))
      (wrap-content-type)))