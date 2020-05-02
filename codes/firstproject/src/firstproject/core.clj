(ns firstproject.core
  (:require [clojure.string :as st])
  (:import [java.util Date Calendar]))

(set! *warn-on-reflection* true)
(defn strlen [^String s] (.length s))
(defn badstrlen [s] (.length s))
(time (reduce + (map badstrlen (repeat 100000 "asdf"))))
(time (reduce + (map strlen (repeat 100000 "asdf"))))

(defn foo
  "I don't do a whole lot."
  [x]
  (println x "Hello, World!"))

(defn -main []
  (foo "Main, " )
    println (st/split "a,b,c" #",")
      print (Date.))


(def myarray (into-array String ["this" "is" "an" "array"]))
(comment
(aget myarray 1)
(aset myarray 1 "was")

(amap myarray idx ret(aset ret idx (apply str (reverse (aget myarray idx)))))
(areduce myarray idx ret (long 0) (+ ret (count (aget myarray)))))

; Thread mythread = new Thread(){
;   public void run() {
;     System.out.println ("Running in a thread");
;   }
; }

(def mythread (proxy [Thread] []
  (run [] (println "Running in a thread"))))

(import 'java.util.concurrent.Executors)
(def mypool (Executors/newFixedThreadPool 4))

(.submit mypool mythread)

(def myrunnable
  (proxy [Runnable] []
    (run [] (println "Running in a runnable"))))

(.submit mypool myrunnable)

(def myreified (reify
  Comparable
  (compareTo [this other] -1)
  Runnable
  (run [this] (println "Running via reify"))))

(.submit mypool myreified)

(.compareTo myreified "Not myreified")
