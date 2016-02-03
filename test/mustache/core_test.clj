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

(defn quick-eval [template-filename data]
  (let [text (slurp template-filename)
        r (java.io.StringReader. text)
        template (.compile (mustache-factory) r template-filename)
        buffer (java.io.StringWriter.)]
    (.execute template buffer data)
    (str buffer)))

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

