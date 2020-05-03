(ns estoque.aula5)

(def estoque {"Mochila" 10, "Camiseta" 5})
(println  estoque)
; conjure/out | {Mochila 10, Camiseta 5}


(println "Temos" (count estoque) "elementos")
; conjure/out | Temos 2 elementos
(println "Chaves são" (keys estoque))
; conjure/eval | (println "Chaves são" (keys estoque))
(println "Valores são:" (vals estoque))
; conjure/out | Chaves são (Mochila Camiseta)

; keyword
; :mochila

(def estoque {:mochila 10
              :camiseta 5})

(println (assoc estoque :cadeira 3))
; conjure/out | {:mochila 10, :camiseta 5, :cadeira 3}

(println (update estoque :mochila inc))
; conjure/out | {:mochila 11, :camiseta 5}

(defn tira-um
  [valor]
  (println "tirando um de " valor)
  (- valor 1))

(println (update estoque :mochila tira-um))
; conjure/out | tirando um de  10
; conjure/out | {:mochila 9, :camiseta 5}
(println (update estoque :mochila #(- % 3)))
; conjure/out | {:mochila 7, :camiseta 5}

(println (dissoc estoque :mochila))
; conjure/out | {:camiseta 5}

(def pedido {:mochila {:quantidade 2, :preco 80}
             :camiseta {:quantidade 3, :preco 40}})

(println "\n\n\n\n\n")
(println pedido)
; conjure/out | {:mochila {:quantidade 2, :preco 80}, :camiseta {:quantidade 3, :preco 40}}
(println pedido (assoc pedido :chaveiro {:quantidade 1, :preco 10}))
(def pedido (assoc pedido :chaveiro {:quantidade 1, :preco 10}))
(println pedido)
; conjure/out | {:mochila {:quantidade 2, :preco 80}, :camiseta {:quantidade 3, :preco 40}, :chaveiro {:quantidade 1, :preco 10}}


(println (get pedido :mochila))
(println (get pedido :cadeira {}))
(println (:mochila pedido))
(println (:cadeira pedido {}))

(println (:quantidade (:mochila pedido)))
; conjure/out | 2
(println (update-in pedido [:mochila :quantidade] inc))
; conjure/out | {:mochila {:quantidade 3, :preco 80}, :camiseta {:quantidade 3, :preco 40}, :chaveiro {:quantidade 1, :preco 10}}
(def example {:chave {:fenda {:quantidade 1}}})
(println (update-in example [:chave :fenda :quantidade] inc))
; conjure/out | {:chave {:fenda {:quantidade 2}}}


;;; ENCADEMENTO
;THREADING FIRST
(println pedido)
(println (-> pedido
             :mochila
             :quantidade))
; conjure/out | 2

(println (-> pedido
             :mochila
             :quantidade
             inc))
; conjure/out | 3

