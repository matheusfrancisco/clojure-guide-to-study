(ns hospital.model
  (:require [schema.core :as s]))

(def empty-queue clojure.lang.PersistentQueue/EMPTY)

(defn new-hospital
  []
  {:g-queue empty-queue
   :lab1 empty-queue
   :lab2 empty-queue
   :lab3 empty-queue
   })

(s/def PatientID s/Str)
(s/def Department (s/queue PatientID))
(s/def Hospital { s/Keyword Department})

;(s/validate PatientID "William")
;(s/validate PatientID 15)
;(s/validate Department ["William", "Daniela"])
;(s/validate Hospital {:g-queue ["William", "Daniela"]})
;(s/validate Department (conj empty-queue "William", "Daniela"))

(def fila-vazia clojure.lang.PersistentQueue/EMPTY)

(defn novo-hospital []
  {:espera fila-vazia
   :laboratorio1 fila-vazia
   :laboratorio2 fila-vazia
   :laboratorio3 fila-vazia})
