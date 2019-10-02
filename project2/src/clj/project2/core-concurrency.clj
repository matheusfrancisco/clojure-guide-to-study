(ns project2.core)

(instance? Runnable (fn []))

(.start (Thread. (fn [] (println "Hello"))))

(promise)
(deliver (promise) "Hello")

(defn myslowfn []
  (let [p (promise)]
    (.start
      (Thread. (fn []
                 (Thread/sleep 5000)
                 (deliver p "Hello"))))
    p))

(deref (myslowfn))

@(myslowfn)

(let [p (myslowfn)]
  (println "Waiting for a promise...")
  @p)


(defn myslowfn []
  (Thread/sleep 5000)
  "Hello")

@(future (myslowfn))

(defn slowlog [msg]
  (Thread/sleep 5000)
  (println msg))

(defn myfn []
  (future (slowlog "Called myfn"))
  :ok)

(myfn)

(defn myfn []
  (delay (slowlog "Called myfn"))
  :ok)

(myfn)

(defn fib [n]
  (if (< n 2)
    1
    (+ (fib (- n 1 )) (fib (- n 2)))))

(def fib (memoize fib))
(fib 40)
