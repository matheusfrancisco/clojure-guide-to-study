(ns estoque.aula6)

(def pedido {:mochila  {:quantidade 2, :preco 80}
             :camiseta {:quantidade 3, :preco 40}})

(defn imprime-e-15 [valor]
  (println "valor" valor)
  15)

(println (map imprime-e-15 pedido))
; conjure/out | (valor [:mochila {:quantidade 2, :preco 80}]
; conjure/out | valor [:camiseta {:quantidade 3, :preco 40}]
; conjure/out | 15 15)

;
; (defn imprime-e-15 [chave valor]
; (println chave "e" valor)
; 15)
;
; (println (map imprime-e-15 pedido)


(defn imprime-e-15 [[chave valor]]
  (println chave "<e>" valor)
  valor)

(println (map imprime-e-15 pedido))
; conjure/out | (:mochila <e> {:quantidade 2, :preco 80}
; conjure/out | :camiseta <e> {:quantidade 3, :preco 40}
; conjure/out | {:quantidade 2, :preco 80} {:quantidade 3, :preco 40})


(defn imprime-e-15 [[chave valor]]
  valor)

(println (map imprime-e-15 pedido))
; conjure/out | ({:quantidade 2, :preco 80} {:quantidade 3, :preco 40})


(defn preco-dos-produto [[chave valor]]
  (* (:quantidade valor) (:preco valor)))

(println (map preco-dos-produto pedido))
; conjure/out | (160 120)

(println (reduce + (map preco-dos-produto pedido)))
; conjure/out | 280


(defn preco-dos-produto [[_ valor]]
  (* (:quantidade valor) (:preco valor)))

(println (map preco-dos-produto pedido))
; conjure/out | (160 120)
(println (reduce + (map preco-dos-produto pedido)))
; conjure/out | 280

(defn total-do-pedido
  [pedido]
  (reduce + (map preco-dos-produto pedido)))

;THREAD LAST
(defn total-do-pedido
  [pedido]
  (->> pedido
       (map preco-dos-produto ,,,)
       (reduce + ,,,)))

(println (total-do-pedido pedido))
; conjure/out | 280


(defn preco-total-do-produto [produto]
  (* (:quantidade produto) (:preco produto)))

;THREAD LAST
(defn total-do-pedido
  [pedido]
  (->> pedido
       vals
       (map preco-total-do-produto ,,,)
       (reduce + ,,,)))

(println (total-do-pedido pedido))
; conjure/out | 280



(def pedido {:mochila  {:quantidade 2, :preco 80}
             :camiseta {:quantidade 3, :preco 40}
             :chaveiro {:quantidade 1}})

(defn gratuito?
  [item]
  (<= (get item :preco 0) 0))

(println (filter gratuito? (vals pedido)))
; conjure/out | ({:quantidade 1})

(defn gratuito?
  [[_ item]]
  (<= (get item :preco 0) 0))

(defn gratuito?
  [item]
  (<= (get item :preco 0) 0))

(println (filter (fn [[chave item]] (gratuito? item))  pedido))
; conjure/out | ([:chaveiro {:quantidade 1}])

(println (filter #(gratuito? (second %))  pedido))


(defn pago?
  [item]
  (not (gratuito? item)))

(println (pago? {:preco 50}))
; conjure/out | true
(println (pago? {:preco 0}))
; conjure/out | false


(println ((comp not gratuito?) {:preco 50}))
; conjure/out | true

(def pago? (comp not gratuito?))

(println (pago? {:preco 50}))
; conjure/out | true
(println (pago? {:preco 0}))
; conjure/out | false
