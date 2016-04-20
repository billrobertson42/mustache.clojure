# Mustache.clojure

Mustache.clojure is a simple clojure interface to [Mustache.java](https://github.com/spullara/mustache.java). 

Mustache.java is a high performance implementation of Mustache for the JVM. However due to some [implemention issues](https://groups.google.com/forum/#!topic/mustachejava/e8EjcdYUY3E), Mustache.java does not interact well with Clojure data structures.

Mustache.clojure works around those issues and also allows for Clojure keywords as map keys, including keys that have dashes in them.

## Requirements

Mustache.clojure depends on Mustache.java, which requires Java 1.8.

## Usage

Include mustache.clojure in your leinigen project.

    [mustache.clojure "0.1.1"]

Create a mustache factory, and use that to create functions that wrap your mustache templates. e.g.

    (use 'mustache.core)
    (def factory (mustache-factory))
    (def template-function (mustache-compile mustache-factory "some-resource"))
    (def data {:foo [1 2 3 5 8 13] 
               :bar (range 0 10)} 
               :render-time #(str (java.util.Date.))})
    (template-function data)

The no-arg version of `mustache-factory` creates a factory that will load templates as resources from the root of the classpath. You may also pass in a string which indicates which directory to use as resource root e.g. `(mustache-factory "resources/mustache")`. Additionally, you may pass in a `java.io.File` object or `java.nio.file.Path` object to indicate a directory to serve as a root for loading templates from the file system.

Mustache.java handles reloading by discarding the mustache factory and creating a new one. To accommodate this, `mustache-compile` can accept an `atom` containing a mustache factory rather than a direct refererence to a mustache factory, and you can reset that reference as appropriate.


## License

Copyright © 2016

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
