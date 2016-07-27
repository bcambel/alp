(ns alp.raw
(:require [manifold.deferred :as d]
          [manifold.stream :as ms]
          [clojure.edn :as edn]
          [aleph.tcp :as tcp]
          [gloss.core :as gloss]
          [gloss.io :as gio]
          [byte-streams :as bs])
          (:gen-class))

(def words (clojure.string/split  (slurp "/usr/share/dict/words") #"\n"))

(defn echo-handler [strm _]
  (.start (Thread. (fn [& args]
    (while true
      (Thread/sleep 1000)
      (ms/put! strm
       (str  "->" (nth words
          (rand-int (count words))) ))))))

  (ms/connect strm strm))

(defn serv
  [port]
  (let [server (tcp/start-server echo-handler {:port port})]
    server))

(defn listen [port]
  (defonce c @(tcp/client {:host "localhost", :port port}))
  (bs/to-string (or @(ms/try-take! c 1000) "")))

(defn try-read []
  (try
    (println
      (bs/to-string @(ms/try-take! c 1000) ""))
    (catch Throwable t )
    )
  )

(defn kickoff []
  (let [port 11111]
    (defonce srv (serv port))
    (listen port)
    (while true
      (let [seconds (-> (rand-int 10)
                            (* 1000)
                            (+ 1000 ))]
      (println (format "Sleeping %s seconds" (/ seconds 1e3 )))
      (Thread/sleep seconds)
      (try-read)))))
