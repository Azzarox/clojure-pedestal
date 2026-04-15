(defproject api-only "0.1.0-SNAPSHOT"
  :description "Example: Pure JSON API — frontend lives elsewhere"
  :dependencies [[org.clojure/clojure "1.11.1"]
                 [io.pedestal/pedestal.service  "0.8.2-beta-4"]
                 [io.pedestal/pedestal.http-kit "0.8.2-beta-4"]
                 [org.clojure/data.json         "2.5.0"]
                 [ch.qos.logback/logback-classic "1.4.14"]]
  :main api.core
  :source-paths   ["src"]
  :resource-paths ["resources"]
  :profiles {:dev {:source-paths ["dev"]
                   :jvm-opts     ["-Dio.pedestal.dev-mode=true"]}})
