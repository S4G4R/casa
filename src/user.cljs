(ns ^:dev/always user ; Electric currently needs to rebuild everything when any file changes. Will fix
  (:require com.sagar.casa.ui
            hyperfiddle.electric
            hyperfiddle.electric-dom2))


(defn add-tag
  [{:keys [tag id props]}]
  (when-not (.getElementById js/document id)
    (let [element (.createElement js/document tag)]
      (doseq [[attr-key attr-val] (merge props {"id" id})]
        (.setAttribute element attr-key attr-val))
      (.appendChild (.-head js/document) element))))


(def meta-tags
  [;; Mobile Responsiveness
   {:tag "meta"
    :id "meta-viewport"
    :props {"name" "viewport"
            "content" "width=device-width"
            "initial-scale" "1.0"}}
   ;; Bootstrap CSS
   {:tag "link"
    :id "bootstrap-css"
    :props {"rel" "stylesheet"
            "href" "https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css"
            "integrity" "sha384-rbsA2VBKQhggwzxH7pPCaAqO46MgnOM80zW1RWuH61DGLwZJEdK2Kadq2F9CUG65"
            "crossorigin" "anonymous"}}
   ;; Pico CSS
   {:tag "link"
    :id "pico-css"
    :props {"rel" "stylesheet"
            "href" "https://unpkg.com/@picocss/pico@latest/css/pico.min.css"}}
   ;; Font
   {:tag "link"
    :id "font-fam"
    :props {"rel" "stylesheet"
            "media" "screen"
            "href" "https://fontlibrary.org//face/archivo-narrow"
            "type" "text/css"}}])


(def electric-main
  (hyperfiddle.electric/boot ; Electric macroexpansion - Clojure to signals compiler
   (binding [hyperfiddle.electric-dom2/node js/document.body]
     ;; Set page title
     (set! (.-textContent (js/document.querySelector "title")) "Casa")
     ;; Add meta tags
     (mapv add-tag meta-tags)
     ;; Render root component
     (com.sagar.casa.ui/Root.))))

(defonce reactor nil)

(defn ^:dev/after-load ^:export start! []
  (assert (nil? reactor) "reactor already running")
  (set! reactor (electric-main
                 #(js/console.log "Reactor success:" %)
                 #(js/console.error "Reactor failure:" %))))

(defn ^:dev/before-load stop! []
  (when reactor (reactor)) ; teardown
  (set! reactor nil))
