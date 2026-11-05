# Change Log
All notable changes to this project will be documented in this file. This change
log follows the conventions of [keepachangelog.com](http://keepachangelog.com/).

## [0.1.3] - 2026-11-05

### Fixed
- Apply the requested receive buffer size when creating a socket.
- Correct the README client import, server port, and shutdown instructions.

### Changed
- Upgrade Clojure to 1.12.6.
- Use Clojure CLI and tools.build for tests, packaging, and Clojars publishing.
- Remove the Leiningen configuration and its old nREPL and lint plugins.

### Added
- Receive-buffer and UTF-8 UDP loopback regression tests.
- Document installation coordinates and test commands.

## [0.1.2] - 2022-07-14

This version was tagged in Git. It was not published to Clojars.

### Changed
- Upgrade Clojure to 1.11.1 and update development dependencies.

### Fixed
- Update project links and the README client example.

### Added
- Expose DatagramSocket options through a configuration map.
- Add deps.edn for Clojure CLI support.

## [0.1.1] - 2015-11-18

### Added
- Initial release.
