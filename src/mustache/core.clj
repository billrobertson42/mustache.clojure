(ns mustache.core
  (:import [java.io StringWriter]
           [com.github.mustachejava MustacheFactory Mustache]
           [mustache.clojure.glue ClojureMustacheFactory]))

(defn mustache-factory 
  ([]
   "Return a MustacheFactory which finds resources based on classpath root.
    See http://mustachejava.s3-website-us-west-1.amazonaws.com/apidocs/com/github/mustachejava/MustacheFactory.html. 
    The mustache factory returned works with Clojure data structures."
   (ClojureMustacheFactory.))
  ([root]
   "If root is a java.io.File or java.nio.file.Path, return a MustacheFactory 
    which finds resources based on that directory, if root is a string, it 
    will indicate a base location on the classpath. See http://mustachejava.s3-website-us-west-1.amazonaws.com/apidocs/com/github/mustachejava/MustacheFactory.html.
    The mustache factory returned works with Clojure data structures."
   (ClojureMustacheFactory. root)))

(defn mustache-compile [mustache-factory template-name]
  "Given either a mustache-factory, or an atom containing a mustache factory 
   and a template name, return a function that will accept a data structure 
   and format it according to the template. The function returns a string."
  (if (instance? clojure.lang.Atom mustache-factory)
    (fn [data]
      (let [^MustacheFactory template-factory @mustache-factory
            ^Mustache template (.compile template-factory template-name)
            ^StringWriter buffer (StringWriter.)]
        (.execute template buffer data)
        (.toString buffer)))
    (fn [data]
      (let [^MustacheFactory template-factory mustache-factory
            ^Mustache template (.compile template-factory template-name)
            ^StringWriter buffer (StringWriter.)]
        (.execute template buffer data)
        (.toString buffer)))))

(defn mustache-eval
  "Convenience function to evaluate a template against a mustache factory 
   that references the classpath root. Returns a string."
  ([template-resourcename data]
   (let [factory (mustache-factory)]
     (mustache-eval factory template-resourcename data)))
  ([^MustacheFactory template-factory template-name data]
   "Convenience function to evaluate a template against the given mustache 
    factory. Returns a string"
   (let [^Mustache template (.compile template-factory template-name)
         buffer (java.io.StringWriter.)]
     (.execute template buffer data)
     (.toString buffer))))
