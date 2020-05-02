(ns hospital.aula2
  (:use clojure.pprint))


(defrecord Paciente [id, nome, nascimento])

; Paciente Plano de Saude ==> + plano de saur
; Paciente Particular  ==> + 0


(defrecord PacienteParticular [id nome nascimento])
(defrecord PacientePlanoDeSaude [id nome nascimento plano])

;REGRAS DIFERENTES PARA TIPOS DIFERENTES
;deve-assinar-pre-autorizacao?
; Particular ==> valor >= 50
; PlanoDeSaude ==> procedimento NAO esta no plano

;VANTAGEM tudo no mesmo lugar
;DESVANTAGEM: tudo no mesmo lugar
;(defn deve-assinar-pre-autorizacao? [paciente procedimento valor]
;  (if (= PacienteParticular (type paciente))
;    (>= valor 50)
;    (if (= PacientePlanoDeSaude (type paciente))
;      (let [plano (get paciente :plano)]
;        (not (some #(= % procedimento) plano)))
;      true)))


(defprotocol Cobravel
  (deve-assinar-pre-autorizacao? [paciente procedimento valor]))

(extend-type PacienteParticular
  Cobravel
  (deve-assinar-pre-autorizacao? [paciente procedimento valor]
    (>= valor 50)))

(extend-type PacientePlanoDeSaude
  Cobravel
  (deve-assinar-pre-autorizacao? [paciente procedimento valor]
    (let [plano (:plano paciente)]
      (not (some #(= % procedimento) plano)))))

(let [particular (->PacienteParticular 15 "Xico" "9/9/1994")
      plano (->PacientePlanoDeSaude 15 "Xico" "9/9/1994" [:raio-x, :ultrassom])]
  (pprint (deve-assinar-pre-autorizacao? particular :raio-x 500))
  (pprint (deve-assinar-pre-autorizacao? particular :raio-x 40))
  (pprint (deve-assinar-pre-autorizacao? plano :raio-x 9393939))
  (pprint (deve-assinar-pre-autorizacao? plano :coleta-de-sangue 9393939)))
; conjure/out | true
; conjure/out | false
; conjure/out | false
; conjure/out | true

(defprotocol Dateable
  (to-ms [this]))

(extend-type java.lang.Number
  Dateable
  (to-ms [this] this))

(pprint (to-ms 56))
; conjure/out | 56

(extend-type java.util.Date
  Dateable
  (to-ms [this] (.getTime this)))

(pprint (to-ms (java.util.Date.)))
; conjure/out | 1588447826800

(extend-type java.util.Calendar
  Dateable
  (to-ms [this] (to-ms (.getTime this))))

(pprint (to-ms (java.util.GregorianCalendar.)))
; conjure/out | 1588447909488
