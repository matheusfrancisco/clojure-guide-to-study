(defn print-msg []
  (println "Bem vindo(a) ao estoque!"))



(defn aplica-desconto [valor-bruto]
  (* valor-bruto 0.9))


(defn valor-descontado [valor-bruto]
  (* valor-bruto 0.9))


(defn valor-descontado
  "Retorna o valor com desconto de 10%."
  [valor-bruto]
  (* valor-bruto (- 1 0.10)))



(def valor ["Test" "Test2"])
(count valor)
(def valor (conj valor "Cadeira"))


(defn valor-descontado
  "Retorna o valor com desconto de 10%."
  [valor-bruto]
  (let [desconto 0.10]
    (* valor-bruto (- 1 desconto))))


(defn valor-descontado
  "Retorna o valor com desconto de 10%."
  [valor-bruto]
  (let [desconto (/ 10 100)]
    (* valor-bruto (- 1 desconto))))


(class 90N)
(class 90M)


(defn valor-descontado
  "Retorna o valor com desconto de 10%."
  [valor-bruto]
  (let [taxa-de-desconto (/ 10 100)
        desconto        (* valor-bruto taxa-de-desconto)]
    (- valor-bruto desconto))))


(defn valor-descontado
  "Retorna o valor com desconto de 10% se o valor bruto for estritamente maior que 100."
  [valor-bruto]
  (if (> valor-bruto 100)
   (let [taxa-de-desconto (/ 10 100)
         desconto        (* valor-bruto taxa-de-desconto)]
     (- valor-bruto desconto))
   valor-bruto))






