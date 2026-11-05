(ns udp-wrapper.core-test
  (:require [clojure.test :refer :all]
            [udp-wrapper.core :refer :all])
  (:import (java.nio.charset Charset)))

(deftest localhost-address-type
  (is (= (type (localhost)) java.net.Inet4Address)))

(deftest inet-address-type
  (is (= (type (make-address "192.168.1.1")) java.net.Inet4Address)))

(deftest datagram-packet-type
  (is (= (type
          (packet
           (.getBytes "Mozart")
           (make-address "192.168.1.1")
           1024))
         java.net.DatagramPacket)))

(deftest empty-packet-type
  (is (= (type (empty-packet 512)) java.net.DatagramPacket)))

(deftest packet-ip-string
  (is (= "192.168.1.1" (get-ip (packet
                                (.getBytes "Edvard Grieg")
                                (make-address "192.168.1.1")
                                1024)))))

(deftest packet-port
  (is (= 1024 (get-port (packet
                         (.getBytes "Mozart")
                         (make-address "192.168.1.1")
                         1024)))))

(deftest udp-server
  (let [server (create-udp-server 1024)]
    (is (= java.net.DatagramSocket (type server)))
    (close-udp-server server)
    (is (.isClosed server))))

(deftest packet-data
  (let [pkt (packet
             (.getBytes "Schubert" (Charset/forName "UTF-8"))
             (make-address "192.168.1.1")
             1024)]
    (is (= "Schubert" (get-data pkt)))))

(deftest receive-buffer-option
  (with-open [socket (create-udp-server 0 {:receive-buffer-size 4096})]
    ;; The operating system may adjust the requested buffer size.
    (is (pos? (.getReceiveBufferSize socket)))))

(deftest loopback-message
  (with-open [server (create-udp-server 0 {:so-timeout 2000})
              client (create-udp-server 0)]
    (let [message "You might receive this. λ"
          payload (packet (get-bytes-utf8 message)
                          (make-address "127.0.0.1")
                          (.getLocalPort server))]
      (send-message client payload)
      (is (= message (receive-message-data server (empty-packet 512)))))))
