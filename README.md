# mustache.clojure

Simple clojure interface to [Mustache.java](https://github.com/spullara/mustache.java).

## Usage

For the moment...

Create a ClojureMustacheFactory and use according to the documentation provided in Mustache.java.  

## Goals

* The first goal was to adapt Mustache.java so it would work with Clojure w/o bombing on Vectors, etc...
* The second goal was to make it understand maps with keywords w/o having to convert them ahead of time.

Now that these have been met (at least on a trial basis), the goals are...

* Come up with a bit better Clojure API, this API should not require you to learn the Clojure wrapper AND the underlying Java * code, nor should it get in your way if you only want to use the underlying Java code.
* Come up with a decent set of tests (always in progress)

## License

Copyright © 2015

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
