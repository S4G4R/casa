(defproject com.sagar/casa "1.0.0"
  :description "Mi Casa"
  :dependencies [[org.clojure/clojure "1.11.1"]
                 [org.clojure/clojurescript "1.11.60"]
                 [party.donut/system "0.0.215"]
                 [com.hyperfiddle/electric "v2-alpha-540-ga4699532"]
                 [ring/ring-core "1.11.0"]
                 [ring/ring-jetty-adapter "1.11.0"]
                 [com.taoensso/timbre "6.1.0"]
                 [com.fzakaria/slf4j-timbre "0.4.0"]
                 [ch.qos.logback/logback-classic "1.2.11"]
                 [environ "1.1.0"]
                 [thheller/shadow-cljs "2.20.1"]
                 [reagent "1.2.0"]
                 [metosin/reitit "0.5.18"]
                 [cheshire "5.12.0"]
                 [alekcz/storyblok-clj "1.2.0"]
                 [camel-snake-kebab/camel-snake-kebab "0.4.3"]
                 [clojure.java-time "1.3.0"]]
  :plugins [[lein-environ "1.1.0"]]
  :main ^:skip-aot com.sagar.casa
  :target-path "target/%s"
  :repl-options {:init-ns user}
  :jvm-opts ["-Xss2m" ; https://github.com/hyperfiddle/photon/issues/11
             "-XX:-OmitStackTraceInFastThrow" ;; RCF
             ]
  :aliases {"build" ["run" "-m" "com.sagar.casa/build"]}
  :profiles {:uberjar {:aot :all}})
