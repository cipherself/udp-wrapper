(defproject udp-wrapper "0.1.1"
  :description "A wrapper around the Java UDP API to make it a little nicer."
  :url "https://github.com/skeuomorf/udp-wrapper"
  :license {:name "MIT public license"
            :url "http://opensource.org/licenses/MIT"}
  :dependencies [[org.clojure/clojure "1.7.0"]]
  :profiles {:dev {:dependencies [[org.clojure/tools.nrepl "0.2.12"]]
                   :plugins [[jonase/eastwood "0.2.1"]
                             [lein-kibit "0.1.2"]
                             [lein-bikeshed "0.2.0"]]}}
  :aliases {"checkall" ["do" "check," "kibit," "eastwood," "bikeshed"]})
