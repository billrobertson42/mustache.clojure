(ns spec-test
  (:import [com.github.mustachejava DefaultMustacheFactory]
           [java.io StringReader StringWriter]
           [com.fasterxml.jackson.databind ObjectMapper]
           [java.util Map])
  (:require [clojure.test :refer :all]
            [mustache.core :refer :all]
            [clj-yaml.core :as yaml]
            [clojure.java.io :as io]
            [clojure.string :as str]
            [cheshire.core :as cheshire]
            [clojure.pprint :refer [pprint]]))

(def spec-files ["comments" "delimiters" "interpolation" "inverted"
                 "partials" "sections"])

(defn normalize-output [output]
  "Mustache.java elides blank lines. It is slightly different than the spec."
  output
  #_(let [r (io/reader (java.io.StringReader. output))]
      (str/join "\n" (filter #(not (re-matches #"^\s*$" %)) (line-seq r)))))

(defn original-result [java-factory main-template test]
  (let [java-stache (.compile java-factory (.getName main-template))
        test-data  (StringReader. (cheshire/generate-string (:data test)))
        json (.readValue (ObjectMapper.) test-data Map)
        buffer (StringWriter.)]
    (.execute java-stache buffer json)
    (str buffer)))

(defn test-spec [clj-factory java-factory source work-dir test]  
  (let [main-template (io/file work-dir (str (:name test) " test.mustache"))
        test-data (:data test)]
    (spit main-template (:template test))
    (doseq [[partial template] (:partials test)]
      (spit (io/file work-dir (str (name partial) ".mustache")) template))
    
    (println "Testing" source (:name test))
    (let [template-fn (mustache-compile clj-factory (.getName main-template))
          expected (:expected test)
          original (original-result java-factory main-template test)
          actual (template-fn test-data)]
      (if (:regression test)
        (is (= original actual) (str "java/clj " (:name test)))
        (is (= expected actual) (str "spec/clj " (:name test)))))))
    
(defn clean [dir]
  (doseq [f (rest (file-seq dir))]
    (.delete f)))

(deftest spec-tests
  (let [work-dir (io/file "spec-work-dir")]
    (try
      (when-not (.exists work-dir) (.mkdirs work-dir))
      (doseq [spec spec-files]
        (let [spec-resource (str "spec/specs/" spec ".yml")
              spec-data (yaml/parse-string (slurp (io/resource spec-resource)))
              clj-factory (mustache-factory work-dir)
              java-factory (DefaultMustacheFactory. work-dir)]
        (doseq [test (:tests spec-data)]
          (clean work-dir)
          (if (= "Dotted Names - Context Precedence" (:name test))
            (pprint test))
          (if-not (:skip test)
            (test-spec clj-factory java-factory (str spec ".yml") 
                       work-dir test)))))
      (finally
        #_(clean work-dir)
        #_(.delete work-dir)))))
  
