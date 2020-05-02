(ns hospital.aula3
  (:use [clojure pprint])
  (:require [hospital.logic :as h.logic]
            [hospital.model :as h.model]))

; simbolo que qq thread acessar esse namespac vai ter acesso a ele com o valor guilherme
;root binding
(def nome "guilherme")

;redefinir o simbolo
(def nome "redefinir")

(let [nome "guilherme"]
  ;coisa 1
  ;coisa 2
  (println nome)
  ;nao estou refazendo binding do simbolo local
  ; criando um novo simbolo local a este bloco e escondendo
  ; SHADOWING
  (let [nome "daniela"]
    ;coisa3
    ;coisa4
    (println nome))
  (println nome))

(defn testa-atomao []
  (let [hospital-silveira (atom {})]
    (println hospital-silveira)
    (pprint hospital-silveira)
    (pprint (deref hospital-silveira))
    (pprint @hospital-silveira)
    ;não é assim que altera o conteudo dentro de um atomo
    (pprint (assoc @hospital-silveira :laboratorio1 h.model/fila-vazia))
    (pprint @hospital-silveira)

    ; essa é uma das maneira de alterar conteudo dentro de um atomo
    (swap! hospital-silveira assoc :laboratorio1 h.model/fila-vazia)
    (pprint @hospital-silveira)

    (swap! hospital-silveira assoc :laboratorio2 h.model/fila-vazia)
    (pprint @hospital-silveira)
    (update @hospital-silveira :laboratorio1 conj "111")


    (swap! hospital-silveira update :laboratorio1 conj "111")
    (pprint hospital-silveira)
    ))


;(testa-atomao)

(defn chega-em-malvado! [hospital pessoa]
  (swap! hospital h.logic/chega-em-pausado-logando  :espera pessoa)
  (println "apos inserir" pessoa))


(defn simula-um-dia-em-paralelo
  []
  (let [hospital (atom (h.model/novo-hospital))]
    (.start (Thread. (fn [] (chega-em-malvado! hospital "111"))))
    (.start (Thread. (fn [] (chega-em-malvado! hospital "222"))))
    (.start (Thread. (fn [] (chega-em-malvado! hospital "333"))))
    (.start (Thread. (fn [] (chega-em-malvado! hospital "444"))))
    (.start (Thread. (fn [] (chega-em-malvado! hospital "555"))))
    (.start (Thread. (fn [] (chega-em-malvado! hospital "666"))))
    (.start (Thread. (fn [] (Thread/sleep 8000)
                       (pprint hospital))))))

;forcando situacao de retry (busy retry), pode acontecer, mas pode não acontecer
;(simula-um-dia-em-paralelo)

(defn chega-sem-malvado! [hospital pessoa]
  (swap! hospital h.logic/chega-em  :espera pessoa)
  (println "apos inserir" pessoa))


(defn simula-um-dia-em-paralelo
  []
  (let [hospital (atom (h.model/novo-hospital))]
    (.start (Thread. (fn [] (chega-sem-malvado! hospital "111"))))
    (.start (Thread. (fn [] (chega-sem-malvado! hospital "222"))))
    (.start (Thread. (fn [] (chega-sem-malvado! hospital "333"))))
    (.start (Thread. (fn [] (chega-sem-malvado! hospital "444"))))
    (.start (Thread. (fn [] (chega-sem-malvado! hospital "555"))))
    (.start (Thread. (fn [] (chega-sem-malvado! hospital "666"))))
    (.start (Thread. (fn [] (Thread/sleep 8000)
                       (pprint hospital))))))


(simula-um-dia-em-paralelo)
