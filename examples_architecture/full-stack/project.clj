(defproject full-stack "0.1.0-SNAPSHOT"
  :description "Example: Pedestal serves everything — API + compiled frontend"
  :dependencies [[org.clojure/clojure "1.11.1"]
                 [io.pedestal/pedestal.service  "0.8.2-beta-4"]
                 [io.pedestal/pedestal.http-kit "0.8.2-beta-4"]
                 [org.clojure/data.json         "2.5.0"]
                 [ch.qos.logback/logback-classic "1.4.14"]]
  :main app.core
  :source-paths   ["src"]
  :resource-paths ["resources"]
  :profiles {:dev {:source-paths ["dev"]
                   :jvm-opts     ["-Dio.pedestal.dev-mode=true"]}})
