(ns hospital.model
  (:require [schema.core :as s]))

(def fila-vazia clojure.lang.PersistentQueue/EMPTY)

(defn novo-hospital []
  {:espera fila-vazia
   :laboratorio1 fila-vazia
   :laboratorio2 fila-vazia
   :laboratorio3 fila-vazia})


(s/def PacienteID s/Str)
(s/def Departamento (s/queue PacienteID))
(s/def Hospital {s/Keyword Departamento})


;ilustrativo para a aula
;(s/validate PacienteID "Matheus")
;;(s/validate PacienteID 12)
;(s/validate Departamento (conj fila-vazia "Guilherme" "Daniela"))
;(s/validate Hospital {:espera  ["Matheus" "Marcos"]})