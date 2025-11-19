(defproject dhex-pos "0.1.0-SNAPSHOT"
  :description "POS system with re-frame + shadcn-ui"
  :dependencies [[org.clojure/clojure "1.11.1"]
                 [ring "1.12.2"]
                 [compojure "1.7.1"]
                 [integrant "0.8.1"]]

  :min-lein-version "2.9.10"

  :source-paths ["src"]
  :test-paths   ["test"]

  :main dhex-pos.server


  :profiles {:dev {:dependencies [[binaryage/devtools "1.0.7"]  ;; Already from template
                                  ;; Backend dev
                                  [ring/ring-mock "0.4.0"]]
                   :source-paths ["src/clj" "dev"]}  ;; dev/ for dev-only code

             :uberjar {:aot :all
                       :main dhex-pos.server
                       :uberjar-name "dhex-pos.jar"}})
