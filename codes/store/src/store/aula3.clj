(ns store.aula3
  (:require [store.db :as s.db]))

(println (s.db/todos-os-pedidos))

(println (group-by :usuario (s.db/todos-os-pedidos)))

(defn minha-funcao-de-agrupamento
  [elemento]
  (println "elemento" elemento)
  (:usuario elemento))
;pega todos os pedidos para usuarios
;{ 15 [x,c,y]
;   1 [x]
;   10 [x]
;   20 [x,2]}

(println (group-by minha-funcao-de-agrupamento (s.db/todos-os-pedidos)))
;count usuarios
(println (count (vals (group-by :usuario (s.db/todos-os-pedidos)))))
;ilegivel
(println (map count (vals (group-by :usuario (s.db/todos-os-pedidos)))))

;THREAD LAST
(->> (s.db/todos-os-pedidos)
     (group-by :usuario)
     vals
     (map count)
     println)


;(defn conta-total-por-usuario
;  [[usuario pedidos]]
;  (count pedidos)
;  ))

;
;(->> (s.db/todos-os-pedidos)
;    (group-by :usuario)
;    (map conta-total-por-usuario)
;    println)




(defn conta-total-por-usuario
  [[usuario pedidos]]
  [usuario (count pedidos)])

(->> (s.db/todos-os-pedidos)
     (group-by :usuario)
     (map conta-total-por-usuario)
     println)



(defn conta-total-por-usuario
  [[usuario pedidos]]
  {:usuario-id usuario
   :total-de-pedidos (count pedidos)})

(->> (s.db/todos-os-pedidos)
     (group-by :usuario)
     (map conta-total-por-usuario)
     println)



(defn total-dos-pedidos
  [pedidos]
  -1)


(defn quantia-de-pedidos-e-gasto-total-por-usuario
  [[usuario pedidos]]
  {:usuario-id usuario
   :total-de-pedidos (count pedidos)
   :preco-total (total-dos-pedidos pedidos)})

(->> (s.db/todos-os-pedidos)
     (group-by :usuario)
     (map quantia-de-pedidos-e-gasto-total-por-usuario)
     println)





(println "PEDIDOS")

(defn total-do-item
  [[_ detalhes]]
  (* (get detalhes :quantidade 0) (get detalhes :preco-unitario 0)))

(defn total-do-pedido
  [pedido]
  (reduce + (map total-do-item pedido)))

(defn total-dos-pedidos
  [pedidos]
  (->> pedidos
       (map :itens)
       (map total-do-pedido)
       (reduce +)))


(defn quantia-de-pedidos-e-gasto-total-por-usuario
  [[usuario pedidos]]
  {:usuario-id usuario
   :total-de-pedidos (count pedidos)
   :preco-total (total-dos-pedidos pedidos)})

(->> (s.db/todos-os-pedidos)
     (group-by :usuario)
     (map quantia-de-pedidos-e-gasto-total-por-usuario)
     println)





(println "PEDIDOS")

(defn total-do-item
  [[_ detalhes]]
  (* (get detalhes :quantidade 0) (get detalhes :preco-unitario 0)))

(defn total-do-pedido
  [pedido]
  (->> pedido (map total-do-item)
       (reduce +)))

(defn total-dos-pedidos
  [pedidos]
  (->> pedidos
       (map :itens)
       (map total-do-pedido)
       (reduce +)))


(defn quantia-de-pedidos-e-gasto-total-por-usuario
  [[usuario pedidos]]
  {:usuario-id usuario
   :total-de-pedidos (count pedidos)
   :preco-total (total-dos-pedidos pedidos)})

(->> (s.db/todos-os-pedidos)
     (group-by :usuario)
     (map quantia-de-pedidos-e-gasto-total-por-usuario)
     println)


