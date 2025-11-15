(defproject udp-wrapper "0.1.1"
  :description "A wrapper around the Java UDP API to make it a little nicer."
  :url "https://github.com/cipherself/udp-wrapper"
  :license {:name "MIT public license"
            :url "http://opensource.org/licenses/MIT"}
  :dependencies [[org.clojure/clojure "1.11.1"]]
  :profiles {:dev {:dependencies [[org.clojure/tools.nrepl "0.2.13"]]
                   :plugins [[jonase/eastwood "1.2.4"]
                             [lein-kibit "0.1.8"]
                             [lein-bikeshed "0.5.2"]]}}
  :aliases {"checkall" ["do" "check," "kibit," "eastwood," "bikeshed"]})
