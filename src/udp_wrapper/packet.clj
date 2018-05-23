(ns udp-wrapper.packet
  (:require [udp-wrapper.core :as core]))

(defn- wait [seconds] (if seconds (Thread/sleep (* 1000 seconds))))

(defn create-packet
  "Helper to coerce data + destination into packet"
  [data address port]
  (core/packet (core/get-bytes-utf8 data) (core/make-address address) port))

(defn send-packet
  "Send a constructed packet, without receiving"
  [data socket address port & [wait-seconds]]
  (let [packet (create-packet data address port)]
    (core/send-message socket packet)
    (wait wait-seconds)
    packet))

(defn send-packets
  "Send a collection of constructed packets, without receiving"
  [data socket address port]
  (doall (map #(send-packet % socket address port) data)))

(defn send-packet-and-receive
  "Send a constructed packet, wait, and then recieve data back"
  [data socket address port wait-seconds]
  (let [packet (send-packet data socket address port)]
    (wait wait-seconds)
    (core/receive-message-data socket packet)))

(defn send-packets-and-receive
  "Send a collection of constructed packets, wait, and then recieve data back"
  [data socket address port wait-seconds]
  (let [packets (send-packets data socket address port)]
    (wait wait-seconds)
    (doall (map #(core/receive-message-data socket %) packets))))
