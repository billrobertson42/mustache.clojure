(defproject mustache.clojure "0.3.0"
  :description "Glue code to allow mustache.java to work better with Clojure."
  :url "https://github.com/billrobertson42/mustache.clojure"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :java-source-paths ["java"]
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [com.github.spullara.mustache.java/compiler "0.9.1"]
                 ]
  :profiles {:dev 
             {:dependencies 
              [[clj-yaml "0.4.0"]
               [cheshire "5.6.1"]
               [com.fasterxml.jackson.core/jackson-databind "2.7.4"]
               [pjstadig/humane-test-output "0.8.0"]
               ]
              :plugins [[com.jakemccrary/lein-test-refresh "0.15.0"]]
              :injections [(require 'pjstadig.humane-test-output)
                           (pjstadig.humane-test-output/activate!)]
              }})

