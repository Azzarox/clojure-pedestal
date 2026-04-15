(defproject pedestal-app "0.1.0-SNAPSHOT"
  :description "Pedestal service"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}

  :dependencies [[org.clojure/clojure "1.12.4"]
                 [io.pedestal/pedestal.service "0.8.2-beta-3"]
                 [io.pedestal/pedestal.http-kit "0.8.2-beta-3"]
                 [ch.qos.logback/logback-classic "1.5.26"]]

  :source-paths ["src"]
  :resource-paths ["resources"]
  :test-paths ["test"]

  :profiles {:dev {:source-paths ["dev"]
                   :resource-paths ["test-resources"]
                   :dependencies [[org.clojure/test.check "1.1.1"]
                                  [nubank/matcher-combinators "3.10.0"]
                                  [org.clj-commons/pretty "3.6.8"]]
                   :jvm-opts ["-Dio.pedestal.dev-mode=true"]}
             :test {:resource-paths ["test-resources"]
                    :dependencies [[org.clojure/test.check "1.1.1"]
                                   [nubank/matcher-combinators "3.10.0"]]}}

  :main pedestal-app.main
  :repl-options {:init-ns user})
