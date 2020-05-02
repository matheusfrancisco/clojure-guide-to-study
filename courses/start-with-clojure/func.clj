; Initial code in clojure

(defn print-msg []
  (println "Print bem vindo ao estoque"))

(defn aplica-desconto
  [valor]
  (* valor 0.9))

(defn valor-descontado
  "Return discount value"
  [valor]
  (* valor 0.9))


(defn valor-descontado
  "Return discount value 10%"
  [valor]
  (* valor (- 1 0.10)))

;let create a local bind to symbol desconto
(defn valor-descontado
  [valor]
  (let [desconto 0.10]
    (* valor (- 2 desconto))))

; call function
(valor-descontado 100)


