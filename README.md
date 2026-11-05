# udp-wrapper

A wrapper around the Java UDP API to make it a little nicer.

[![Clojars Project](https://clojars.org/udp-wrapper/latest-version.svg)](https://clojars.org/udp-wrapper)

[Latest codox API docs](https://mohamed.computer/udp-wrapper/)

## Installation

For Clojure CLI, add to `deps.edn`.

```clojure
{:deps {udp-wrapper/udp-wrapper {:mvn/version "0.1.3"}}}
```

For Leiningen, add to `:dependencies`.

```clojure
[udp-wrapper "0.1.3"]
```

## Usage

Start the server, then run the client in a second REPL on the same machine.

### Server

```clojure
(require '[udp-wrapper.core :refer [create-udp-server close-udp-server
                                    empty-packet receive-loop]])

;; Listen on port 1337.
(def socket (create-udp-server 1337))

;; Start a listening loop in a future which prints the message it receives.
(def my-future (receive-loop socket (empty-packet 512) println))
```

After sending messages with the client, shut down the server in its REPL.

```clojure
;; Shutdown.
(future-cancel my-future)
(close-udp-server socket)
```

### Client

```clojure
(require '[udp-wrapper.core :refer [create-udp-server close-udp-server
                                    packet get-bytes-utf8 make-address send-message]])

;; Open a socket on an available local port.
(def socket (create-udp-server 0))

;; Create a packet and send it.
(def payload (packet (get-bytes-utf8 "You might receive this.") (make-address "127.0.0.1") 1337))
(send-message socket payload)

;; Shutdown.
(close-udp-server socket)
```

## Development

Use the Clojure CLI. The project depends on Clojure `1.12.6`.

Run the tests, including a local UDP round trip.

```sh
clojure -M:test
```

Build the library JAR and Maven metadata under `target/`.

```sh
clojure -T:build jar
```

## License
[MIT](./LICENSE)
