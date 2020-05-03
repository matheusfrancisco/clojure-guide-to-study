(ns estoque.aula3)

(defn valor-descontado
  "Retorna o valor com desconto de 10% se o valor bruto for
  estritamente maior que 100."
  [valor-bruto]
  (if (> valor-bruto 100)
    (let [taxa-de-desconto (/ 10 100)
          desconto         (* valor-bruto taxa-de-desconto)]
      (println "Calculando desconto de " desconto)
      (- valor-bruto desconto))
    valor-bruto))

(println (valor-descontado 1000))
(println (valor-descontado 100))


;PREDICATE
(defn aplica-desconto?
  [valor-bruto]
  (if (> valor-bruto 100)
    true
    false))

(println (aplica-desconto? 100))

(defn valor-descontado
  "Retorna o valor com desconto de 10% se o valor bruto for estritamente maior que 100."
  [valor-bruto]
  (if (aplica-desconto? valor-bruto)
    (let [taxa-de-desconto (/ 10 100)
          desconto         (* valor-bruto taxa-de-desconto)]
      (- valor-bruto desconto))
    valor-bruto))

(println (valor-descontado 100))
(println (valor-descontado 1000))


(defn aplica-desconto?
  [valor-bruto]
  (println "Chamando a versao redefinida")
  (if (> valor-bruto 100)
    true))

(println (valor-descontado 100))
(println (valor-descontado 1000))


(defn aplica-desconto?
  [valor-bruto]
  (println "Chamando a versao when")
  (when (> valor-bruto 100)
    true))


(println (valor-descontado 100))
(println (valor-descontado 1000))

(defn aplica-desconto?
  [valor-bruto]
  (println "Chamando a versao  direta")
  (> valor-bruto 100))

(println (valor-descontado 100))
(println (valor-descontado 1000))


(defn mais-caro-que-100?
  [valor-bruto]
  (> valor-bruto 100))


(defn valor-descontado-aplica
  "Retorna o valor com desconto de 10% se deve aplicar desconto."
  [aplica? valor-bruto]
  (if (aplica? valor-bruto)
    (let [taxa-de-desconto (/ 10 100)
          desconto         (* valor-bruto taxa-de-desconto)]
      (- valor-bruto desconto))
    valor-bruto))


(println "funcao como parâmetro")
(println (valor-descontado-aplica mais-caro-que-100? 1000))

; HIGHER ORDER FUNCTIONS
; IMUTABILIDADE

(defn mais-caro-que-100? [valor-bruto] (> valor-bruto 100))

(println "funcao sem nome anonimo")
(println (valor-descontado-aplica (fn [valor-bruto] (> valor-bruto 100)) 1000))
(println (valor-descontado-aplica (fn [v] (> v 100)) 1000))
(println (valor-descontado-aplica #(> %1 100) 1000))
(println (valor-descontado-aplica #(> % 100) 1000))


(def mais-caro-que-100? (fn [valor-bruto] (> valor-bruto 100)))
