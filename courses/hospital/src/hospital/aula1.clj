(ns hospital.aula1
  (:use clojure.pprint))

(defn adiciona-paciente
  "Os pacientes são um mapa da seg forma
  {15 {paciente 15}, 23 {paciente 23}}"
  [pacientes paciente]
  (let [id (:id paciente)]
    (assoc pacientes id paciente)))


(defn testa-uso-de-pacientes []
  (let [pacientes {}
        matheus {:id 15 :nome "Xico" :nascimento "9/9/1994"}
        jao {:nome "Jao" :nascimento "18/2/1994"}]
    (pprint (adiciona-paciente pacientes matheus))
    (pprint (adiciona-paciente pacientes jao))))

(testa-uso-de-pacientes)
; conjure/out | {15 {:id 15, :nome "Xico", :nascimento "9/9/1994"}}
; Tivemos problemas porque não tinha ID no jao
; We've some problems because jao doesn't have id
; type problem
; conjure/out | {nil {:nome "Jao", :nascimento "18/2/1994"}}


(defn adiciona-paciente-se-id-existe
  "Os pacientes são um mapa da seg forma
  {15 {paciente 15}, 23 {paciente 23}}"
  [pacientes paciente]
  (let [id (:id paciente)]
    (if id
      (assoc pacientes id paciente)
      (throw (ex-info "Paciente não possui id" {:paciente paciente})))))

;improve function belong
(defn adiciona-paciente-se-id-existe-improve
  "Os pacientes são um mapa da seg forma
  {15 {paciente 15}, 23 {paciente 23}}"
  [pacientes paciente]
  (if-let [id (:id paciente)]
    (assoc pacientes id paciente)
    (throw (ex-info "Paciente não possui id" {:paciente paciente}))))

;;Mapa não garante as coisas para gente
;; Nos que devemos validar as coisas
;; Problema de tipos
;; Mapa doesn't guarantee things for us (for people)
;; We've to guarantee things like type

(defrecord Paciente [id nome nascimento])

(println (->Paciente "15" "Matheus" "9/9/1994"))
; conjure/out | #hospital.aula1.Paciente{:id 15, :nome Matheus, :nascimento 9/9/1994}
(pprint (->Paciente "15" "Matheus" "9/9/1994"))
; conjure/out | {:id "15", :nome "Matheus", :nascimento "9/9/1994"}

(pprint (Paciente. 15 "Xico" "9/9/1994"))
; conjure/out | {:id 15, :nome "Xico", :nascimento "9/9/1994"}

(pprint (map->Paciente {:id 15 :nome "Xico" :nascimento "9/91994"}))
; conjure/out | {:id 15, :nome "Xico", :nascimento "9/91994"}
;#hospital.aula1.Paciente{:id 15, :nome "Xico", :nascimento "9/91994"}

(let [xico (->Paciente 15 "Xico" "9/9/1994")]
  (println (:id xico))
  (println (vals xico))
  (println (class xico))
  (println (record? xico)))
; conjure/out | 15
; conjure/out | (15 Xico 9/9/1994)
; conjure/out | hospital.aula1.Paciente
; conjure/out | true

(pprint (map->Paciente {:id 15 :nome "Xico" :nascimento "9/91994" :cpf "22222"}))
; conjure/out | {:id 15, :nome "Xico", :nascimento "9/91994", :cpf "22222"}

;(pprint (Paciente "Xico" "9/9/1994"))
(pprint (assoc (Paciente. nil "Xico" "9/9/1994") :id 38))
; conjure/out | {:id 38, :nome "Xico", :nascimento "9/9/1994"}
(pprint (class (assoc (Paciente. nil "Xico" "9/9/1994") :id 38)))
; conjure/out | hospital.aula1.Paciente
