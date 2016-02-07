(ns mustache.core
  (:import [com.github.mustachejava MustacheFactory]
           [mustache.clojure.glue ClojureMustacheFactory]))

(defn mustache-factory 
  ([]
   "Return a MustacheFactory which finds resources based on classpath root"
   (ClojureMustacheFactory.))
  ([root]
   "If root is a java.io.File or java.nio.file.Path, return a MustacheFactory which finds 
    resources based on that directory, if root is a string, it will indicate a base location 
    on the classpath"
   (ClojureMustacheFactory. root)))

