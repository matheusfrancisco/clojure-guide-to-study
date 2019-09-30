(ns learnclojure)

(type 1) java.lang.Long
(type (int 1))
(type 1.1) java.lang.Double
(type true) java.lang.Boolean
(type "Hello") java.lang.String
(type (keyword "a")) clojure.lang.Keyword

(type (quote a)) clojure.lang.Symbol
(type 'a) clojure.lang.Symbol

(type (list 1 2 3)) clojure.lang.PersistenList
(type ( vector 1 2 3 )) clojure.lang.PersistentVector

(nth (vector 1 2 3) 2) 3

{:a 1 :b 1 :c 1}
(type #{1 2 3}) clojure.lang.PersistentHashSet


type (hash-map :a 1 :b 2)) clojure.lang.PersistentHashMap
