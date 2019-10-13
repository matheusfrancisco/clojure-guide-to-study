(ns store.aula4
  (:require [store.db :as s.db]
            [store.logic :as s.logic]))


(println (s.db/todos-os-pedidos))

(let [pedidos (s.db/todos-os-pedidos)
      resumo (s.logic/resumo-por-usuario pedidos)]
  (println "Resumo" resumo)
  (println "Ordenacao" (sort-by :preco-total resumo))
  (println "Ordenacao ao contrário" (reverse (sort-by :preco-total resumo)))
  (println "Ordenado por id" (sort-by :usuario-id resumo))

  ; update-in, assoc-in
  (println (get-in pedidos [0 :itens :mochila :quantidade]))

  )


(defn resumo-ordenado-por-usuario [pedidos]
  (->> pedidos
       (s.logic/resumo-por-usuario)
       (sort-by :preco-total)
       reverse))


(let [pedidos (s.db/todos-os-pedidos)
      resumo (resumo-ordenado-por-usuario pedidos)]
  (println "Resumo" resumo)
  (println "Primeiro" (first resumo))
  (println "Segundo" (rest resumo))
  (println "Resto" (rest resumo))
  (println "Total" (count resumo))
  (println "Class" (class resumo))
  (println "nth" (nth resumo 1))
  (println "get 1" (get resumo 1))
  (println "take" (take 2 resumo)))


(defn top-2 [resumo]
  (take 2 resumo))


(let [pedidos (s.db/todos-os-pedidos)
      resumo (resumo-ordenado-por-usuario pedidos)]
  (println "Resumo" resumo)
  (println "Top-2" (top-2 resumo)))


(let [pedidos (s.db/todos-os-pedidos)
      resumo (resumo-ordenado-por-usuario pedidos)]
  (println ">500 filter" (filter #(> (:preco-total %) 500)resumo))
  (println ">500 filter empty not" (not (empty? (filter #(> (:preco-total %) 500) resumo))))
  (println ">500 some" (some #(> (:preco-total %) 500)resumo))
  )



