(ns hospital.aula2
  (:use clojure.pprint))

;(defrecord Pacientes [id, nome, nascimento])

; Paciente Plano de Saude ==> + plano de saude
; Paciente Particular ===> 0

; caminho horripilante com provaveis problemas horriveis e tipos 2^n
;(defrecord PacientePlanoDeSaude HERDA Paciente [plano])

(defrecord PacienteParticular [id, nome, nascimento])
(defrecord PacientePlanoDeSaude [id, nome, nascimento, plano])

; REGRAS DIFERENTES PARA TIPOS DIFERENTES
;deve-assinar-pre-autorizacao?
; Particular ==> valor >= 50
; PlanoDeSaude ==> procedimento NAO esta no plano


;VANTAGEM: tudo no mesmo lugar
;DESVANTAGEM tudo no mesmo lugar
;(defn deve-assinar-pre-autorizacao? [paciente procedimento valor]
; (if (= PacienteParticular (type paciente))
;   (>= valor 50)
;   ;assumindo que existe os dois tipos
;   (if (= PacientePlanoDeSaude (type paciente))
;      (let [plano (get paciente :plano)]
;        (not (some #(= % procedimento) plano)))
;      true)))

(defprotocol Cobravel
  (deve-assinar-pre-autorizacao? [paciente procedimento valor]))


(extend-type PacienteParticular
  Cobravel
  (deve-assinar-pre-autorizacao? [paciente, procedimento, valor]
    (>= valor 50)))


(extend-type PacientePlanoDeSaude
  Cobravel
  (deve-assinar-pre-autorizacao? [paciente, procedimento, valor]
    (let [plano (:plano paciente)]
      (not (some #(= % procedimento) plano)))))
;contains? => verifica o indice... e indice voce fica dependendo da estrutura de dados vetor


(let [particular (->PacienteParticular 15, "Guilherme", "3/9/1981")
      plano (->PacientePlanoDeSaude  15, "matheus", "18/9/1981", [:raio-x, :ultrasom])]
  (pprint (deve-assinar-pre-autorizacao? particular, :raio-x, 500))
  (pprint (deve-assinar-pre-autorizacao? particular, :raio-x, 33))
  (pprint (deve-assinar-pre-autorizacao? plano, :raio-x, 33333333))
  (pprint (deve-assinar-pre-autorizacao? plano, :coleta-de-sangue, 33333333)))

;alternativa seria implementar diretamente

(defprotocol Dateable
  (to-ms [this]))

(extend-type java.lang.Number
  Dateable
  (to-ms [this] this))

(pprint (to-ms 56))

(extend-type java.util.Date
  Dateable
  (to-ms [this] (.getTime this)))

(pprint (to-ms (java.util.Date.)))


(extend-type java.util.Calendar
  Dateable
  (to-ms [this] (to-ms (.getTime this))))

(pprint (to-ms (java.util.GregorianCalendar.)))