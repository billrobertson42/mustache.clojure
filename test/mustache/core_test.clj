(ns mustache.core-test
  (:require [clojure.test :refer :all]
            [mustache.core :refer :all]))

(defn quick-eval 
  ([template-resourcename data]
   (let [factory (mustache-factory)]
     (quick-eval factory template-resourcename data)))
  ([template-factory template-name data]
   (let [template (.compile template-factory  template-name)
         buffer (java.io.StringWriter.)]
     (.execute template buffer data)
     (str buffer))))

(def data1 {
  "name" "Chris"
  "value" 10000
  "taxed_value" (- 10000 (* 10000 0.4))
  "in_ca" true})

(def data2 {
  "repo" [
    { "name" "resque" }
    { "name" "hub" }
    { "name" "rip" }
  ]
})

(def data3 {
  "repo" '(
    { "name" "resque" }
    { "name" "hub" }
    { "name" "rip" }
  )
})

(def data4 
  {"repo" (map #(hash-map "name" %) ["resque" "hub" "drip"])})            

(deftest simple-cases
  (is (= "Hello Chris\nYou have just won 10000 dollars!\nWell, 6000.0 dollars, after taxes.\n"
         (quick-eval "template1.mustache" data1)))
  (is (= "Hello Chris\nYou have just won 10000 dollars!\nWell, 7000.0 dollars, after taxes.\n"
         (quick-eval "template1.mustache" (assoc data1 "taxed_value" #(- 10000 (* 10000 0.3))))))

  (is (= "  <b>resque</b>\n  <b>hub</b>\n  <b>rip</b>\n"
         (quick-eval "template2.mustache" data2)))
  (is (= "  <b>resque</b>\n  <b>hub</b>\n  <b>rip</b>\n"
         (quick-eval "template2.mustache" data3)))
  (is (= "  <b>resque</b>\n  <b>hub</b>\n  <b>drip</b>\n"
         (quick-eval "template2.mustache" data4)))
  
  )

(def data-keywords {
  :name "Chris"
  :value 10000
  :taxed_value (- 10000 (* 10000 0.4))
  :in_ca true})

(def dashed-data-keywords {
  :name "Chris"
  :value 10000
  :taxed-value (- 10000 (* 10000 0.4))
  :in_ca true})

(def data2-keywords {
  :repo [
    { :name "resque" }
    { :name "hub" }
    { :name "rip" }
  ]
})

(def data3-keywords {
  :repo '(
    { :name "resque" }
    { :name "hub" }
    { :name "rip" }
  )
})

(def data4-keywords
  {"repo" (map #(hash-map :name %) ["resque" "hub" "drip"])})

(deftest keywords-in-maps
  (is (= "Hello Chris\nYou have just won 10000 dollars!\nWell, 6000.0 dollars, after taxes.\n" 
         (quick-eval "template1.mustache" data-keywords)))
  (is (= "Hello Chris\nYou have just won 10000 dollars!\nWell, 6000.0 dollars, after taxes.\n" 
         (quick-eval "template1.mustache" dashed-data-keywords)))

  (is (= "  <b>resque</b>\n  <b>hub</b>\n  <b>rip</b>\n"
         (quick-eval "template2.mustache" data2-keywords)))
  (is (= "  <b>resque</b>\n  <b>hub</b>\n  <b>rip</b>\n"
         (quick-eval "template2.mustache" data3-keywords)))
  (is (= "  <b>resque</b>\n  <b>hub</b>\n  <b>drip</b>\n"
         (quick-eval "template2.mustache" data4-keywords)))
  )

(deftest resolver-tests
  (let [default-cp-resolver-factory (mustache-factory)
        rooted-cp-resolver-factory (mustache-factory "mustache")
        file-resolver-factory (mustache-factory (java.io.File. "test"))
        path-resolver-factory (mustache-factory (-> "test" java.io.File. .toPath))]
    (is (= "Hello Chris\nYou have just won 10000 dollars!\nWell, 6000.0 dollars, after taxes.\n"
           (quick-eval default-cp-resolver-factory "template1.mustache" data1)))
    (is (= "Hello Chris\nYou have just won 10000 dollars!\nWell, 6000.0 dollars, after taxes.\n"
           (quick-eval rooted-cp-resolver-factory "template3.mustache" data1)))
    (is (= "Hello Chris\nYou have just won 10000 dollars!\nWell, 6000.0 dollars, after taxes.\n"
           (quick-eval file-resolver-factory "template1.mustache" data1)))
    (is (= "Hello Chris\nYou have just won 10000 dollars!\nWell, 6000.0 dollars, after taxes.\n"
           (quick-eval path-resolver-factory "template1.mustache" data1)))
    ))
    
        
    
    

