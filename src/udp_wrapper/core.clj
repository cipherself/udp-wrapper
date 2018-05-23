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
  [port & [{:keys [broadcast? reuse-address? receive-buffer-size send-buffer-size
                   so-timeout traffic-class]
            :as   options}]]
  (let [socket (DatagramSocket. port)]
    (if-not (nil? broadcast?) (.setBroadcast socket broadcast?))
    (if-not (nil? reuse-address?) (.setReuseAddress socket reuse-address?))
    (if-not (nil? receive-buffer-size) (.setReceiveBufferSize socket broadcast?))
    (if-not (nil? send-buffer-size) (.setSendBufferSize socket send-buffer-size))
    (if-not (nil? so-timeout) (.setSoTimeout socket so-timeout))
    (if-not (nil? traffic-class) (.setTrafficClass socket traffic-class))
    socket))

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
