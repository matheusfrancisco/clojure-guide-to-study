(ns hospital.logic
  (:require [schema.core :as s]
            [hospital.model :as h.model]))


;problem when department is nil
;(defn fits-in-queue?
;  [hospital department]
;  (-> hospital
;          department
;          count
;          (< 5))
;  )

; works even when the  department is nil
;(defn fits-in-queue?
;  [hospital department]
;  (when-let [queue (get hospital department)]
;    (-> queue
;        count
;        (< 5)))
;  )

; also works even when the  department is nil
(defn fits-in-queue?
  [hospital department]
  (some-> hospital
          department
          count
          (< 5))
  )



;(defn- tries-to-add-to-the-queue
;  [hospital department patient]
;  (if (fits-in-queue? hospital department)
;    (update hospital department conj patient))
;  )
;
;(defn arrived-at
;  [hospital department patient]
;  (if-let [new-hospital (tries-to-add-to-the-queue hospital department patient)]
;    {:hospital new-hospital, :result :success}
;    {:hospital hospital, :result :impossible-to-add-patient-to-the-queue})
;  )

;(defn arrived-at
;  [hospital department patient]
;  (if (fits-in-queue? hospital department)
;    (update hospital department conj patient)
;    (throw (ex-info "This deparment is full or doesn't exist"
;                    {:patient patient, :type :impossible-to-add-patient-to-the-queue})))
;  )

(defn arrived-at
  [hospital department patient]
  (if (fits-in-queue? hospital department)
    (update hospital department conj patient)
    (throw (ex-info "This department is full or doesn't exist" {:patient patient})))
  )

(s/defn was-attended-to :- h.model/Hospital
        [hospital :- h.model/Hospital, department :- s/Keyword]
        (update hospital department pop)
        )

(s/defn next-patient :- h.model/PatientID
        [hospital :- h.model/Hospital, department :- s/Keyword]
        (-> hospital
            department
            peek)
        )

(defn same-size?
  [hospital hospital-after from to]
  (= (+ (count (get hospital-after from)) (count (get hospital-after to)))
     (+ (count (get hospital from)) (count (get hospital to))))
  )

(s/defn transfer :- h.model/Hospital
        [hospital :- h.model/Hospital, from :- s/Keyword, to :- s/Keyword]
        {
         :pre  [(contains? hospital from), (contains? hospital to)]
         :post [(same-size? hospital, %, from, to)]
         }
        (let [patient (next-patient hospital from)]
          (-> hospital
              (was-attended-to from)
              (arrived-at to patient))
          )
        )

;;

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
