(ns com.sagar.casa.ui.hello
  (:require [hyperfiddle.electric :as e]
            [hyperfiddle.electric-dom2 :as dom]))


(e/defn Hello []
  (e/client (dom/p (dom/text "Hey!!!"))))
