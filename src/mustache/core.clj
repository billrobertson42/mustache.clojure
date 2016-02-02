(ns mustache.core
  (:import [com.github.mustachejava MustacheFactory]
           [mustache.clojure.glue ClojureMustacheFactory]))

(defn mustache-factory []
  (ClojureMustacheFactory.))
