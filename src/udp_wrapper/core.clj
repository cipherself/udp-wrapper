(ns udp-wrapper.core
  (:import (java.net InetAddress DatagramPacket DatagramSocket)
           (java.nio.charset Charset)))

(defn localhost
  "Get the InetAddress of the localhost."
  []
  (InetAddress/getLocalHost))

(defn make-address
  "Make an InetAddress from a string."
  [ip]
  (InetAddress/getByName ip))

(defn get-bytes-utf8
  [^String string]
  (.getBytes string (Charset/forName "UTF-8")))

(defn packet
  "Make a DatagramPacket from bytes."
  [byts host port]
  (DatagramPacket. byts (alength byts) host port))

(defn empty-packet
  "Make an empty DatagramPacket."
  [length]
  (DatagramPacket. (byte-array length) length))

(defn get-ip
  "Get the ip address as a string from a DatagramPacket."
  [pkt]
  (.getHostAddress ^InetAddress (.getAddress ^DatagramPacket pkt)))

(defn get-port
  "Get the port from a DatagramPacket."
  [pkt]
  (.getPort ^DatagramPacket pkt))

(defn create-udp-server
  "Make a UDP listening socket."
  [port]
  (DatagramSocket. port))

(defn close-udp-server
  "Close a UDP socket."
  [socket]
  (.close ^DatagramSocket socket))

(defn send-message
  ""
  [socket pkt]
  (.send ^DatagramSocket socket pkt))

(defn get-data
  "Get data as a String from a packet."
  [pkt]
  (String. (.getData pkt) 0 (.getLength pkt) (Charset/forName "UTF-8")))

(defn receive-message
  ""
  [socket pkt]
  (.receive socket pkt))

(defn receive-message-data
  "Receive a message from a DatagramPacket and extract data from it."
  [socket pkt]
  (.receive socket pkt)
  (get-data pkt))

(defn receive-loop
  "Listen for messages and run f on the data extracted from a DatagramPacket."
  [socket pkt f]
  (future (while true (f (receive-message-data socket pkt)))))
