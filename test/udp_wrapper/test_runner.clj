(ns udp-wrapper.test-runner
  (:require [clojure.test :as test]
            [udp-wrapper.core-test]))

(defn -main
  [& _]
  (let [{:keys [fail error]} (test/run-tests 'udp-wrapper.core-test)]
    (shutdown-agents)
    (System/exit (if (zero? (+ fail error)) 0 1))))
