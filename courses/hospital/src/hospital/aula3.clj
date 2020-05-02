(ns hospital.aula3
  (:require [hospital.logic :as h.logic])
  (:use clojure.pprint))

(defn carrega-paciente [id]
  (println "Carregando" id)
  (Thread/sleep 1000)
  {:id id, :carrega-em (h.logic/agora)})

(pprint (carrega-paciente 15))
; conjure/out | Carregando 15
; conjure/out | {:id 15, :carrega-em 1588448536927}

(pprint (carrega-paciente 30))
; conjure/out | Carregando 30
; conjure/out | {:id 30, :carrega-em 1588448538918}


(defn- carrega-se-nao-existe
  [cache id carregadora]
  (if (contains? cache id)
    cache
    (let [paciente (carregadora id)]
      (assoc cache id paciente))))

(pprint (carrega-se-nao-existe {} 15 carrega-paciente))
; conjure/out | Carregando 15
; conjure/out | {15 {:id 15, :carrega-em 1588448757727}}

(pprint (carrega-se-nao-existe {15 {:id 15}} 15 carrega-paciente))
; conjure/out | {15 {:id 15}}

;isolando comportamento
(defprotocol Carregavel
  (carrega! [this id]))

(defrecord Cache
  [cache carregadora]

  Carregavel

  (carrega! [this id]
    (swap! cache carrega-se-nao-existe id carregadora)
    (get @cache id)))

(def pacientes (->Cache (atom {}), carrega-paciente))
(carrega! pacientes 15)
(carrega! pacientes 30)
(carrega! pacientes 30)
(pprint pacientes)
; conjure/out | {:cache
; conjure/out |  #<Atom@7d991d79: 
; conjure/out |    {15 {:id 15, :carrega-em 1588450162234},
; conjure/out |     30 {:id 30, :carrega-em 1588450168371}}>,
; conjure/out |  :carregadora
; conjure/out |  #object[hospital.aula3$carrega_paciente 0x43954c3f "hospital.aula3$carrega_paciente@43954c3f"]}
