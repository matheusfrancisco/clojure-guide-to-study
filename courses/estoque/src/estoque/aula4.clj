(ns estoque.aula4)

(def precos [30 700 1000])
(println precos)
; conjure/out | [30 700 1000]
(println (precos 0))
; conjure/out | 30
(println (get precos 0))
; conjure/out | 30
(println (get precos 2))
; conjure/out | 1000
(println "valor padrão nil" (get precos 17))
; conjure/out | valor padrão nil nil
(println "valor padrão 0" (get precos 17 0))
; conjure/out | valor padrão 0 0

(println (conj precos 5))
; conjure/out | [30 700 1000 5]
(println precos)
; conjure/out | [30 700 1000]

(println (inc 5))
; conjure/out | 6
(println (update precos 0 inc))
; conjure/out | [31 700 1000]
(println (update precos 1 dec))
; conjure/out | [30 699 1000]
(println precos)
; conjure/out | [30 700 1000]

(defn soma-1
  [valor]
  (println "estou somando um em" valor)
  (+ valor 1))

(println (update precos 0 soma-1))
; conjure/out | estou somando um em 30
; conjure/out | [31 700 1000]

(defn soma-3
  [valor]
  (println "estou somando 3 em" valor)
  (+ valor 3))

(println (update precos 0 soma-3))
; conjure/out | estou somando 3 em 30
; conjure/out | [33 700 1000]

; Codigo antigo

(defn aplica-desconto?
  [valor-bruto]
  (> valor-bruto 100))


(defn valor-descontado
  "Retorna o valor com desconto de 10% se o valor bruto for estritamente maior que 100."
  [valor-bruto]
  (if (aplica-desconto? valor-bruto)
    (let [taxa-de-desconto (/ 10 100)
          desconto         (* valor-bruto taxa-de-desconto)]
      (- valor-bruto desconto))
    valor-bruto))

(println "map" (map valor-descontado precos))
; conjure/out | map (30 630N 900N)

(println (range 10))
; conjure/out | (0 1 2 3 4 5 6 7 8 9)
(println (filter even? (range 10)))
; conjure/out | (0 2 4 6 8)

(println (filter aplica-desconto? precos))
; conjure/out | (700 1000)

(println "map apos o filter" (map valor-descontado (filter aplica-desconto? precos)))
; conjure/out | map apos o filter (630N 900N)

(println (reduce + precos))
; conjure/out | 1730

(defn minha-soma
  [valor-1 valor-2]
  (println "somando" valor-1 valor-2)
  (+ valor-1 valor-2))

(println (reduce minha-soma precos))
; conjure/out | somando 30 700
; conjure/out | somando 730 1000
; conjure/out | 1730

(println (reduce minha-soma (range 10)))
; conjure/out | somando 0 1
; conjure/out | somando 1 2
; conjure/out | somando 3 3
; conjure/out | somando 6 4
; conjure/out | somando 10 5
; conjure/out | somando 15 6
; conjure/out | somando 21 7
; conjure/out | somando 28 8
; conjure/out | somando 36 9
; conjure/out | 45

(println (reduce minha-soma [15]))
; conjure/out | 15

(println (reduce minha-soma 0 [15]))
; conjure/out | somando 0 15
; conjure/out | 15
(println (reduce minha-soma 0 []))
; conjure/out | 0