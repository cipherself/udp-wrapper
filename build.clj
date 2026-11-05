(ns build
  (:require [clojure.tools.build.api :as b]
            [deps-deploy.deps-deploy :as dd]))

(def lib 'udp-wrapper/udp-wrapper)
(def version "0.1.3")
(def class-dir "target/classes")
(def jar-file (str "target/udp-wrapper-" version ".jar"))
(def pom-file (str class-dir "/META-INF/maven/udp-wrapper/udp-wrapper/pom.xml"))

(defn jar
  "Build the library JAR and Maven metadata."
  [_]
  (b/delete {:path "target"})
  (b/write-pom
   {:class-dir class-dir
    :lib lib
    :version version
    :basis (b/create-basis {:project "deps.edn"})
    :src-dirs ["src"]
    :pom-data [[:description "A wrapper around the Java UDP API to make it a little nicer."]
               [:url "https://github.com/cipherself/udp-wrapper"]
               [:licenses
                [:license
                 [:name "MIT License"]
                 [:url "https://opensource.org/licenses/MIT"]]]
               [:scm
                [:url "https://github.com/cipherself/udp-wrapper"]
                [:connection "scm:git:https://github.com/cipherself/udp-wrapper.git"]
                [:tag version]]]})
  (b/copy-dir {:src-dirs ["src"] :target-dir class-dir})
  (b/copy-file {:src "LICENSE" :target (str class-dir "/LICENSE")})
  (b/jar {:class-dir class-dir :jar-file jar-file})
  (println "Built" jar-file))

(defn deploy
  "Build and publish to Clojars using CLOJARS_USERNAME and CLOJARS_PASSWORD."
  [_]
  (jar nil)
  (dd/deploy {:installer :remote
              :artifact jar-file
              :pom-file pom-file}))
