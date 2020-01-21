(ns hospital.aula4
  (:use [clojure pprint])
  (:require [hospital.logic :as h.logic]
            [hospital.model :as h.model]))

(defn chega-sem-malvado! [hospital pessoa]
  (swap! hospital h.logic/chega-em  :espera pessoa)
  (println "apos inserir" pessoa))


(defn simula-um-dia-em-paralelo-com-mapv
  "Simulação utilizando um mapv para forcar quase que iperativamente a execucao da funcao"
  []
  (let [hospital (atom (h.model/novo-hospital))
        pessoas ["111","222","333","444","555","666",]]
    (mapv #(.start (Thread. (fn [] (chega-sem-malvado! hospital %)))) pessoas)
    (.start (Thread. (fn [] (Thread/sleep 8000)
                       (pprint hospital))))))


;(simula-um-dia-em-paralelo-com-mapv)


(defn starta-thread-de-chegada
  [hospital pessoa]
  (.start (Thread. (fn [] (chega-sem-malvado! hospital pessoa)))))

(defn preparadinha
  [hospital]
  (fn [pessoa] (starta-thread-de-chegada hospital pessoa))
  )

(defn simula-um-dia-em-paralelo-com-mapv-extraida
  []
  (let [hospital (atom (h.model/novo-hospital))
        pessoas ["111","222","333","444","555","666"]]
    (mapv  (preparadinha hospital) pessoas)
    (.start (Thread. (fn [] (Thread/sleep 8000)
                       (pprint hospital))))))


;(simula-um-dia-em-paralelo-com-mapv-extraida)


(defn starta-thread-de-chegada
  ([hospital]
  (fn [pessoa] (starta-thread-de-chegada hospital pessoa)))
  ([hospital pessoa]
    (.start (Thread. (fn [] (chega-sem-malvado! hospital pessoa))))))

(defn simula-um-dia-em-paralelo-com-mapv-extraida
  []
  (let [hospital (atom (h.model/novo-hospital))
        pessoas ["111","222","333","444","555","666"]
        starta (starta-thread-de-chegada hospital)]
    (mapv  starta pessoas)
    (.start (Thread. (fn [] (Thread/sleep 8000)
                       (pprint hospital))))))


;(simula-um-dia-em-paralelo-com-mapv-extraida)



(defn starta-thread-de-chegada
  [hospital pessoa]
   (.start (Thread. (fn [] (chega-sem-malvado! hospital pessoa)))))

(defn simula-um-dia-em-paralelo-com-mapv-extraida-com-partial
  []
  (let [hospital (atom (h.model/novo-hospital))
        pessoas ["111","222","333","444","555","666"]
        starta (partial starta-thread-de-chegada hospital)]

    (println (mapv starta pessoas))
    (.start (Thread. (fn [] (Thread/sleep 8000)
                       (pprint hospital))))))


;(simula-um-dia-em-paralelo-com-mapv-extraida-com-partial)


(defn starta-thread-de-chegada
  [hospital pessoa]
  (.start (Thread. (fn [] (chega-sem-malvado! hospital pessoa)))))

(defn simula-um-dia-em-paralelo-com-doseq
  []
  (let [hospital (atom (h.model/novo-hospital))
        pessoas ["111","222","333","444","555","666"]]

    (doseq [pessoa pessoas]
      (starta-thread-de-chegada pessoa))

    (.start (Thread. (fn [] (Thread/sleep 8000)
                       (pprint hospital))))))


;(simula-um-dia-em-paralelo-com-doseq)



(defn simula-um-dia-em-paralelo-com-doseq-
  "Preocupado em executar para a sequencia"
  []
  (let [hospital (atom (h.model/novo-hospital))
       pessoas (range 6)]

    (doseq [pessoa pessoas]
      (starta-thread-de-chegada hospital pessoa))

    (.start (Thread. (fn [] (Thread/sleep 8000)
                       (pprint hospital))))))


;(simula-um-dia-em-paralelo-com-doseq-)



(defn simula-um-dia-em-paralelo-com-dotimes
  "Realmente estou preocupado em executar N vezes"
  []
  (let [hospital (atom (h.model/novo-hospital))]

    (dotimes [pessoa 6]
      (starta-thread-de-chegada hospital pessoa))

    (.start (Thread. (fn [] (Thread/sleep 8000)
                       (pprint hospital))))))


(simula-um-dia-em-paralelo-com-dotimes)