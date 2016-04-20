(defproject mustache.clojure "0.2.1"
  :description "Glue code to allow mustache.java to work better with Clojure."
  :url "https://github.com/billrobertson42/mustache.clojure"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :java-source-paths ["java"]
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [com.github.spullara.mustache.java/compiler "0.9.1"]
                 ])
