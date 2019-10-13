(ns store.aula5
  (:require [store.db :as s.db]
            [store.logic :as s.logic]))

(defn gastou-bastante? [info-do-usuario]
  (> (:preco-total info-do-usuario) 500))

(let [pedidos (s.db/todos-os-pedidos)
      resumo (s.logic/resumo-por-usuario pedidos)]
  (println "keep" (keep gastou-bastante? resumo))
  (println "filter" (filter gastou-bastante? resumo)))

(println "tetanto entender dentro do keep e do filter")


(defn gastou-bastante? [info-do-usuario]
  (println "gastou-bastante-esse-usuario?" (:usuario-id info-do-usuario))
  (> (:preco-total info-do-usuario) 500))

(let [pedidos (s.db/todos-os-pedidos)
      resumo (s.logic/resumo-por-usuario pedidos)]
  (println "keep" (keep gastou-bastante? resumo))
  (println "filter" (filter gastou-bastante? resumo)))



(println "Isolando os codigos")

(println (range 10))
(println (take 2 (range 10000)))

; a sequencia não esta sendo "gulosa" (eager)
(let [sequencia (range 100000)]
  (println (take 2 sequencia))
  (println (take 2 sequencia)))
;esta sendo LAZY (preguicoso)


(defn filtro1 [x]
  (println "filtro1" x)
  x)

(println (map filtro1 (range 10)))

(defn filtro2 [x]
  (println "filtro2" x)
  x)

(println (map filtro2 (map filtro1 (range 10))))


(->> (range 10)
     (map filtro1)
     (map filtro2)
     println)

;CHUNKS de 32
(->> (range 50)
     (map filtro1)
     (map filtro2)
     println)


(->> (range 50)
     (mapv filtro1)
     (mapv filtro2)
     println)

(->> [0 1 2 3 4 5 6 76 7 7 8 9 0 7 5 4 3 3 3 3 32 2 2 2 3 3 4 5 5 6 3 32 3 3 50]
     (mapv filtro1)
     (mapv filtro2)
     println)


; lista ligada foi 100% lazy nesse cenário
(->> '(0 1 2 3 4 5 6 76 7 7 8 9 0 7 5 4 3 3 3 3 32 2 2 2 3 3 4 5 5 6 3 32 3 3 50)
     (mapv filtro1)
     (mapv filtro2)
     println)