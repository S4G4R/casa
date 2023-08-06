(defproject com.sagar/casa "1.0.0"
  :description "Mi Casa"
  :dependencies [[org.clojure/clojure "1.11.1"]
                 [org.clojure/clojurescript "1.11.60"]
                 [party.donut/system "0.0.167"]
                 [com.hyperfiddle/electric "v2-alpha-349-ge9996713"]
                 [ring-basic-authentication/ring-basic-authentication "1.1.1"]
                 [info.sunng/ring-jetty9-adapter "0.18.5"]
                 [com.taoensso/timbre "6.1.0"]
                 [environ "1.1.0"]
                 [thheller/shadow-cljs "2.20.1"]
                 [reagent "1.2.0"]]
  :plugins [[lein-environ "1.1.0"]]
  :main ^:skip-aot com.sagar.casa
  :target-path "target/%s"
  :repl-options {:init-ns user}
  :profiles {:uberjar {:aot :all}})
