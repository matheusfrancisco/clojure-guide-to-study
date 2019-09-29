(ns tutorial.core
  (:require [clojure.string :as str])
  (:gen-class))

(def -main
  "I don't do a whole lot...yet"

  [& args]

  (def string1 "this is my 2nd string")
  (str/blank? string1);; flase
  (str/inclues? string1 "my");; true because my is there
  (str/index-of string1 "my")
  (str/split string1 #"  ") ;;split in vector
  (str/split string1 #"\d") ;; split in number
  (str/join " " ["The" "Big" "Cheese"])
  (str/replace "I am 42" #"42" "43")
  (str/upper-case string1)
  (str/lower-case string1)

  )
