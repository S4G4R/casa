(ns com.sagar.casa.data
  (:require [hyperfiddle.electric :as e]))


#?(:clj
   (def data
     (map-indexed
      #(assoc %2 :id %1)
      [{:title "ABC2" :date "20/05/2023"}
       {:title "ABC2" :date "21/05/2023"}
       {:title "ABC2" :date "21/05/2023"}
       {:title "ABC2" :date "21/05/2023"}
       {:title "ABC2" :date "21/05/2023"}
       {:title "ABC2" :date "21/05/2023"}
       {:title "ABC2" :date "21/05/2023"}])))

#?(:clj (def !blogs (atom data)))

(e/def blogs (e/server (e/watch !blogs)))


(comment
  (swap! !blogs conj {:title "ABC" :date "20/05/2023"})

  (reset! !blogs data))
