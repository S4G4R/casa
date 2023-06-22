(ns ^:dev/always user ; Electric currently needs to rebuild everything when any file changes. Will fix
  (:require com.sagar.casa.ui
            hyperfiddle.electric
            hyperfiddle.electric-dom2))

(def electric-main
  (hyperfiddle.electric/boot ; Electric macroexpansion - Clojure to signals compiler
   (binding [hyperfiddle.electric-dom2/node js/document.body]
     ;; For mobile responsiveness
     (when-not (.getElementById js/document "meta-viewport")
       (-> (.-head js/document)
           (.appendChild
            (doto (.createElement js/document "meta")
              (.setAttribute "id" "meta-viewport")
              (.setAttribute "name" "viewport")
              (.setAttribute "content" "width=device-width")
              (.setAttribute "initial-scale" "1.0")))))
     ;; Bootstrap CSS
     (when-not (.getElementById js/document "bootstrap-css")
       (-> (.-head js/document)
           (.appendChild
            (doto (.createElement js/document "link")
              (.setAttribute "id" "bootstrap-css")
              (.setAttribute "rel" "stylesheet")
              (.setAttribute "href" "https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css")
              (.setAttribute "integrity" "sha384-rbsA2VBKQhggwzxH7pPCaAqO46MgnOM80zW1RWuH61DGLwZJEdK2Kadq2F9CUG65")
              (.setAttribute "crossorigin" "anonymous")))))
     ;; Pico CSS
     (when-not (.getElementById js/document "pico-css")
       (-> (.-head js/document)
           (.appendChild
            (doto (.createElement js/document "link")
              (.setAttribute "id" "pico-css")
              (.setAttribute "rel" "stylesheet")
              (.setAttribute "href" "https://unpkg.com/@picocss/pico@latest/css/pico.min.css")))))
     (com.sagar.casa.ui/Main.))))

(defonce reactor nil)

(defn ^:dev/after-load ^:export start! []
  (assert (nil? reactor) "reactor already running")
  (set! reactor (electric-main
                 #(js/console.log "Reactor success:" %)
                 #(js/console.error "Reactor failure:" %))))

(defn ^:dev/before-load stop! []
  (when reactor (reactor)) ; teardown
  (set! reactor nil))
