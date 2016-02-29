(ns mustache.core-test
  (:require [clojure.test :refer :all]
            [mustache.core :refer :all]))


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

(deftest default-mustache-eval
  (is (= "Hello Chris\nYou have just won 10000 dollars!\nWell, 6000.0 dollars, after taxes.\n"
         (mustache-eval "template1.mustache" data1)))
  (is (= "Hello Chris\nYou have just won 10000 dollars!\nWell, 7000.0 dollars, after taxes.\n"
         (mustache-eval "template1.mustache" (assoc data1 "taxed_value" #(- 10000 (* 10000 0.3))))))

  (is (= "  <b>resque</b>\n  <b>hub</b>\n  <b>rip</b>\n"
         (mustache-eval "template2.mustache" data2)))
  (is (= "  <b>resque</b>\n  <b>hub</b>\n  <b>rip</b>\n"
         (mustache-eval "template2.mustache" data3)))
  (is (= "  <b>resque</b>\n  <b>hub</b>\n  <b>drip</b>\n"
         (mustache-eval "template2.mustache" data4)))
  
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
         (mustache-eval "template1.mustache" data-keywords)))
  (is (= "Hello Chris\nYou have just won 10000 dollars!\nWell, 6000.0 dollars, after taxes.\n" 
         (mustache-eval "template1.mustache" dashed-data-keywords)))

  (is (= "  <b>resque</b>\n  <b>hub</b>\n  <b>rip</b>\n"
         (mustache-eval "template2.mustache" data2-keywords)))
  (is (= "  <b>resque</b>\n  <b>hub</b>\n  <b>rip</b>\n"
         (mustache-eval "template2.mustache" data3-keywords)))
  (is (= "  <b>resque</b>\n  <b>hub</b>\n  <b>drip</b>\n"
         (mustache-eval "template2.mustache" data4-keywords)))
  )

(deftest resolver-tests
  (let [default-cp-resolver-factory (mustache-factory)
        rooted-cp-resolver-factory (mustache-factory "mustache")
        file-resolver-factory (mustache-factory (java.io.File. "test"))
        path-resolver-factory (mustache-factory (-> "test" java.io.File. .toPath))]
    (is (= "Hello Chris\nYou have just won 10000 dollars!\nWell, 6000.0 dollars, after taxes.\n"
           (mustache-eval default-cp-resolver-factory "template1.mustache" data1)))
    (is (= "Hello Chris\nYou have just won 10000 dollars!\nWell, 6000.0 dollars, after taxes.\n"
           (mustache-eval rooted-cp-resolver-factory "template3.mustache" data1)))
    (is (= "Hello Chris\nYou have just won 10000 dollars!\nWell, 6000.0 dollars, after taxes.\n"
           (mustache-eval file-resolver-factory "template1.mustache" data1)))
    (is (= "Hello Chris\nYou have just won 10000 dollars!\nWell, 6000.0 dollars, after taxes.\n"
           (mustache-eval path-resolver-factory "template1.mustache" data1)))
    ))
    
(deftest compile-direct-tests
  (let [factory (mustache-factory)
        f1 (mustache-compile factory "template1.mustache")]
    (is (= "Hello Chris\nYou have just won 10000 dollars!\nWell, 6000.0 dollars, after taxes.\n"
           (f1 data1)))))
    
(deftest compile-atom-tests
  (let [factory (atom (mustache-factory))
        f1 (mustache-compile factory "template1.mustache")]
    (is (= "Hello Chris\nYou have just won 10000 dollars!\nWell, 6000.0 dollars, after taxes.\n"
           (f1 data1)))))
