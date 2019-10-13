(ns store.aula2)

; ["daniela" "matheus" "julia" "carlos" "ana"]
(def vetor ["daniela" "matheus" "julia" "carlos" "ana"])
;no stop iteration
(defn conta-no-stop
  [total-ate-agora elementos]
  (conta-no-stop (inc total-ate-agora) (rest elementos)))


;

(defn conta-no-else
  [total-ate-agora elementos]
  (if (next elementos)
    (recur (inc total-ate-agora) (next elementos))))

(println (conta-no-else 0 vetor))


(defn conta-inc
  [total-ate-agora elementos]
  (if (next elementos)
    (recur (inc total-ate-agora) (next elementos))
    (inc total-ate-agora)))

(println (conta-inc 0 vetor))
(println (conta-inc 0 []))

;
(defn conta-um-parametro
  [total-ate-agora elementos]
  (if (seq elementos)
    (recur (inc total-ate-agora) (next elementos))
    total-ate-agora))

(println (conta-um-parametro 0 vetor))
(println (conta-um-parametro 0 []))


(defn minha-funcao
  ([parametro1] (println "p1" parametro1))
  ([parametro1 parametro2] (println "p2" parametro1 parametro2)))
(minha-funcao 1)
(minha-funcao 1 2)



(defn conta-dois-parametros
  ([elementos]
   (conta-dois-parametros 0 elementos))

  ([total-ate-agora elementos]
   (if (seq elementos)
     (recur (inc total-ate-agora) (next elementos))
     total-ate-agora)))

(println (conta-dois-parametros 0 vetor))
(println (conta-dois-parametros  []))


(def vetor ["daniela" "matheus" "julia" "carlos" "ana"])

;; for total-ate-agora 0 , elementos-restantes elementos ;; 1 next
(defn conta
  [elementos]
  (println "antes")
  (loop [total-ate-agora 0
         elementos-restantes elementos]
    (if (seq elementos-restantes)
      (recur (inc total-ate-agora) (next elementos-restantes))
      total-ate-agora)))

(println (conta vetor))
(println (conta  []))
