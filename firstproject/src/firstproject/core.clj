(ns firstproject.core
  (:require [clojure.string :as st])
  (:import [java.util Date]))



(defn foo
  "I don't do a whole lot."
  [x]
  (println x "Hello, World!"))

(defn -main []
  (foo "Main, " )
    println (st/split "a,b,c" #",")
      print (Date.))
