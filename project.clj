(defproject com.sagar/casa "1.0.0"
  :description "Mi Casa"
  :dependencies [[org.clojure/clojure "1.11.1"]
                 [org.clojure/clojurescript "1.11.60"]
                 [party.donut/system "0.0.167"]
                 [com.hyperfiddle/electric "v2-alpha-428-g22937f75"]
                 [ring-basic-authentication/ring-basic-authentication "1.1.1"]
                 [info.sunng/ring-jetty9-adapter "0.18.5"]
                 [com.taoensso/timbre "6.1.0"]
                 [com.fzakaria/slf4j-timbre "0.4.0"]
                 [ch.qos.logback/logback-classic "1.2.11"]
                 [environ "1.1.0"]
                 [thheller/shadow-cljs "2.20.1"]
                 [reagent "1.2.0"]
                 [metosin/reitit "0.5.18"]]
  :plugins [[lein-environ "1.1.0"]]
  :main ^:skip-aot com.sagar.casa
  :target-path "target/%s"
  :repl-options {:init-ns user}
  :jvm-opts ["-Xss2m" ; https://github.com/hyperfiddle/photon/issues/11
             "-XX:-OmitStackTraceInFastThrow" ;; RCF
             ]
  :aliases {"build" ["run" "-m" "com.sagar.casa/build"]}
  :profiles {:uberjar {:aot :all}})
