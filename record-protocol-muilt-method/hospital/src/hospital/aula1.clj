(ns hospital.aula1
  (:use [clojure pprint]))


(defn adiciona-paciente
  "Os pacientes são um mapa da seguinte forma  { 15 { paciente 15}, 23 {paciente 23}}
  O paciente {:id 15 ....}"
  [pacientes paciente]
  (if-let  [id  (:id paciente)]
      (assoc pacientes id paciente)
      (throw (ex-info "Paciente não possui id" {:paciente paciente}))))


(defn testa-uso-de-pacientes []
  (let [pacientes {}
        guilherme {:id 15 :nome "Guilherme" :nascimento "18/9/1981"}
        daniela {:id 20, :nome "Daniela", :nascimento "18/9/1982"}
        paulo {:nome "Daniela", :nascimento "18/9/1983"}]
    (pprint (adiciona-paciente pacientes guilherme))
    (pprint (adiciona-paciente pacientes daniela))
    (pprint (adiciona-paciente pacientes paulo))))



;(testa-uso-de-pacientes)

(defrecord Paciente [id nome nascimento])

(println (->Paciente 15 "Guilherme" "18/9/1981"))
(pprint (->Paciente 15 "Guilherme" "18/9/1981"))
(pprint (Paciente. 14 "Matheus" "9/9/1994"))
(pprint (Paciente. 12 "error" "Matheus2"))
(pprint(map->Paciente {:id 15, :nome "Matheus", :nascimento "18/9/1981"}))

(let [guilherme (->Paciente 15 "Guilherme" "18/9/1981")]
  (println (:id guilherme))
  (println (vals guilherme))
  (println (record? guilherme))
  (println (.nome guilherme)))

(pprint(map->Paciente {:id 15, :nome "Matheus", :nascimento "18/9/1981" :rg "hahahah"}))
(pprint (Paciente. nil "error" "Matheus2"))
(pprint (assoc (Paciente. nil "Matheus" "9/9/1994") :id 38))
(pprint (class (assoc (Paciente. nil "Matheus" "9/9/1994") :id 38)))


(pprint (= (->Paciente 15 "Guilherme" "18/9/1981") (->Paciente 15 "Guilherme" "18/9/1981")))
(pprint (= (->Paciente 15 "Guilherme" "18/9/1981") (->Paciente 15 "Guilherme" "18/9/1981")))
