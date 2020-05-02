(ns learnclojure)

(def x "Hello Clojure")

(let [x "Xicao"]
  (print "Hello, "x))


(if (empty? x)
  "X is empty"
  "X is not empty"))


(if (empty? x)
  nil
  (do
    (println "Ok")
    :ok))

(when-not (empty? x)
  (println "Ok")
  :ok) :ok)

(case x
  "Goodbye" :goodbye
  "Hello" :hello
  :nothing :nothing)

(cond
  (= x "Goodbye") :goobye
  (= (apply str (reverse x)) "olleH") :olleh
  :otherwise :nothing) :nothing

