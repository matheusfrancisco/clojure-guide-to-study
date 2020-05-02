
(def x (list 1 2 3))
(def v [1 2 3])

(first x)
(last x)
(nth x 1)

(cons 0 x)
(conj x 0)

(type (concat x v)) #clojure.lang.LazySeq

(def m {:a 1 :b 2})
(array-map :a 1 :b 2)
(type (hash-map :a 1 :b 2))
(assoc {:a 1} :b 2)
(assoc-in {:settings {:a 1 :b 2}} [:settings :a] "a")

(update-in {:settings {:a 1 :b 2}} [:settings :a] inc)

(get m :a)
(:a m)


(def s #{1 2 3})

(conj s 4)
(disj s 3)
(contains? s 3)
(get s 4)


