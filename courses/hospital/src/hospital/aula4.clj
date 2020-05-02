(ns hospital.aula4
  (:use clojure.pprint))

(defrecord PacienteParticular [id, nome, nascimento situacao])
(defrecord PacientePlanoDeSaude [id, nome, nascimento, situacao, plano])


(defprotocol Cobravel
  (deve-assinar-pre-autorizacao? [paciente procedimento valor]))

(defn nao-eh-urgente? [paciente]
  (not= :urgente (:situacao paciente :normal)))

(extend-type PacienteParticular
  Cobravel
  (deve-assinar-pre-autorizacao? [paciente procedimento valor]
    (and (>= valor 50) (nao-eh-urgente? paciente))))

(extend-type PacientePlanoDeSaude
  Cobravel
  (deve-assinar-pre-autorizacao? [paciente procedimento valor]
    (let [plano (:plano paciente)]
      (and (not (some #(= % procedimento) plano)) (nao-eh-urgente? paciente)))))

(let [particular (->PacienteParticular 15 "Xico" "9/9/1994" :normal)
      plano (->PacientePlanoDeSaude 15 "Xico" "9/9/1994" :normal [:raio-x, :ultrassom])]
  (pprint (deve-assinar-pre-autorizacao? particular :raio-x 500))
  (pprint (deve-assinar-pre-autorizacao? particular :raio-x 40))
  (pprint (deve-assinar-pre-autorizacao? plano :raio-x 9393939))
  (pprint (deve-assinar-pre-autorizacao? plano :coleta-de-sangue 9393939)))


(let [particular (->PacienteParticular 15 "Xico" "9/9/1994" :urgente)
      plano (->PacientePlanoDeSaude 15 "Xico" "9/9/1994" :urgente [:raio-x, :ultrassom])]
  (pprint (deve-assinar-pre-autorizacao? particular :raio-x 500))
  (pprint (deve-assinar-pre-autorizacao? particular :raio-x 40))
  (pprint (deve-assinar-pre-autorizacao? plano :raio-x 9393939))
  (pprint (deve-assinar-pre-autorizacao? plano :coleta-de-sangue 9393939)))

; conjure/out | true
; conjure/out | false
; conjure/out | false
; conjure/out | true
; conjure/out | false
; conjure/out | false
; conjure/out | false
; conjure/out | false
;nao se coloca multi no final
;só para manter no mesmo arquivo
(defmulti deve-assinar-pre-autorizacao-multi? class)
(defmethod deve-assinar-pre-autorizacao-multi? PacienteParticular [paciente] 
  (println "Invocando paciente particular")
  true)

(defmethod deve-assinar-pre-autorizacao-multi? PacientePlanoDeSaude [paciente]
  (println "Invocando paciente plano de saudde")
  false)

(let [particular (->PacienteParticular 15 "Xico" "9/9/1994" :urgente)
      plano (->PacientePlanoDeSaude 15 "Xico" "9/9/1994" :urgente [:raio-x, :ultrassom])]
  (pprint (deve-assinar-pre-autorizacao-multi? particular))
  (pprint (deve-assinar-pre-autorizacao-multi? plano)))

; conjure/out | Invocando paciente particular
; conjure/out | true
; conjure/out | Invocando paciente plano de saudde
; conjure/out | false

;pedido {:paciente paciente, :valor valor, :procidemeno procedimento}
;explorando como a funcao q define a estrategia
(defn minha-funcao [p]
  (println p)
  (class p))

(defmulti multi-teste minha-funcao)
;(multi-teste :xico)
; conjure/out | :xico
; conjure/err | Syntax error (IllegalArgumentException) compiling at (src/hospital/aula4.clj:76:1).
; conjure/err | No method in multimethod 'multi-teste' for dispatch value: class clojure.lang.Keyword


(defrecord PacienteParticular [id, nome, nascimento, situacao])
(defrecord PacientePlanoDeSaude [id, nome, nascimento, situacao, plano])
;feio, misturando keywords e calsses
;funcao normal, pode testar, facil de testar!!
(defn tipo-de-autorizador [pedido]
  (let [paciente (:paciente pedido)
        situacao (:situacao paciente)
        urgencia? (= :urgente situacao)]
    (if urgencia?
      :sempre-autorizado
      (class paciente))))

(defmulti deve-assinar-pre-autorizacao-do-pedido? tipo-de-autorizador)

(defmethod  deve-assinar-pre-autorizacao-do-pedido? :sempre-autorizado [pedido]
  false)

(defmethod  deve-assinar-pre-autorizacao-do-pedido? PacienteParticular [pedido]
  (>= (:valor pedido 0) 50))

(defmethod deve-assinar-pre-autorizacao-do-pedido? PacientePlanoDeSaude [pedido]
  (not (some #(= % (:procedimento pedido)) (:paciente pedido))))

(let [particular (->PacienteParticular 15 "Xico" "9/9/1994" :urgente)
      plano (->PacientePlanoDeSaude 33 "Xico" "9/9/1994" :urgente [:raio-x :ultrassom])]
  (pprint (deve-assinar-pre-autorizacao-do-pedido? {:paciente plano :valor 1000 :procedimento :coleta-de-sangue}))
  (pprint (deve-assinar-pre-autorizacao-do-pedido? {:paciente particular :valor 1000 :procedimento :coleta-de-sangue})))
