(ns hospital.colecoes
  (:use [clojure pprint]))

(defn testa-vetor []
  (let [espera [111 222]]
    (println espera)
    (println (conj espera 333))
    (println (conj espera 444))
    (println (pop espera))))

(testa-vetor)


(defn testa-lista []
  (let [espera '(111 222)]
    (println espera)
    (println (conj espera 333))
    (println (conj espera 444))
    (println (pop espera))))

(testa-lista)


(defn testa-conjunto []
  (let [espera #{111 222}]
    (println "conjunto")
    (println espera)
    (println (conj espera 333))
    (println (conj espera 444))
    ; não funciona em set (println (pop espera))
    ))

(testa-conjunto)


(defn testa-fila []
  (let [espera (conj clojure.lang.PersistentQueue/EMPTY "111" "222")]
    (println "conjunto")
    (println (seq espera))
    (println (seq (conj espera 333)))
    (println (seq (conj espera 444)))
    (println (seq (pop espera)))
    (println (peek espera))
    (pprint espera)))

(testa-fila)