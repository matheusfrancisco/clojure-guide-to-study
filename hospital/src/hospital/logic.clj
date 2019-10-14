(ns hospital.logic)

(defn cabe-na-fila? [hospital departamento]
  (-> hospital
      (get,,, departamento)
      count,,,
      (< ,,, 5)))


(defn chega-em
  [hospital departamento pessoa]
  (if (cabe-na-fila? hospital departamento)
      (update hospital departamento conj pessoa)
      (throw (ex-info "Fila já está cheia " {:tetanto-adicionar pessoa}))))


(defn chega-em-pausado
  [hospital departamento pessoa]
  (Thread/sleep (* (rand) 2000))
  (if (cabe-na-fila? hospital departamento)
    (do
        (update hospital departamento conj pessoa))
    (throw (ex-info "Fila já está cheia " {:tetanto-adicionar pessoa}))))

;funcao malvada que parece ser pura mas usa random e altera estado do random e loga
(defn chega-em-pausado-logando
  [hospital departamento pessoa]
  (println "Tetando adicionar a pessoa" pessoa)
  (Thread/sleep (* (rand) 2000))
  (if (cabe-na-fila? hospital departamento)
    (do
      (println "Dando o update" pessoa)
      (update hospital departamento conj pessoa)      )
    (throw (ex-info "Fila já está cheia " {:tetanto-adicionar pessoa}))))


(defn atende
  [hospital departamento]
  (update hospital departamento pop))



(defn proxima
  [hospital departamento]
  (-> hospital
      departamento
      peek))


(defn transfere
  [hospital de para]
  (let [pessoa (proxima hospital de)]
    (-> hospital
        (atende de)
        (chega-em para pessoa))))




(defn atende-completo
  "somente para demonstrar que é possível retornar os dois "
  [hospital departamento]
  {:paciente (update hospital departamento peek)
   :hospital (update hospital departamento pop)})



; pessoalmente, acho que não ficou melhor
(defn atende-completo-que-chama-ambos
  "somente para demonstrar que é possível retornar os dois "
  [hospital departamento]
  (let [fila (get hospital departamento)
        peek-pop (juxt peek pop)
        [pessoa fila-atualizada] (peek-pop fila)
        hospital-atualizado (update hospital assoc departamento fila-atualizada)]
  {:paciente pessoa
   :hospital hospital-atualizado}))
