# udp-wrapper

A wrapper around the Java UDP API to make it a little nicer.

[![Clojars Project](https://clojars.org/udp-wrapper/latest-version.svg)](https://clojars.org/udp-wrapper)

[Latest codox API docs](https://muattiyah.com/udp-wrapper/)

## Usage
### Server

```clojure
(require '[udp-wrapper.core :refer [create-udp-server close-udp-server
                                    empty-packet receive-loop]])

;; Listen on port 1024.
(def socket (create-udp-server 1024))

;; Start a listening loop in a future which prints the message it receives.
(def my-future (receive-loop socket (empty-packet 512) println))

;; Shutdown.
(future-cancel my-future)
(close-udp-server socket)
```

### Client

```clojure
(require '[udp-wrapper.core :refer [create-udp-server close-udp-server
                                    packet get-bytes-utf8 make-address]])

;; Open a socket to send and receive through it.
(def socket (create-udp-server 1024))

;; Create a packet and send it.
(def payload (packet (get-bytes-utf8 "You might receive this.") (make-address "127.0.0.1") 1337))
(send-message socket payload)

;; Shutdown.
(close-udp-server socket)
```

## License
[MIT](./LICENSE)
