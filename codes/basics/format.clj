(ns tutorial.core
  (:gen-class))

(def ranVar 10)
(neg? 15);; return false
(pos? 15);; return true
(even? 15)
(odd? 15)
(float? 15)
(zero? 15)



(defn -main
  "I don't do a whole lot ... yet"
  [& args]

  (def aLong 15)
  (nil? aLong)
  ;; will return false (nil is check for no value)

  (def aString "Hello")
  (def aLong 15)
  (def aDouble 1.234)
  (format "This is string %" aString)
  (format "5 spaces and %5d" aLong)
  (format "Leading zeros %04d" aLong)
  (format "%-4d left justified" aLong)
  (format "3 decimals %.3f" aDouble)

  )
