(ns hospital.core-test
  (:require [clojure.test :refer :all]
            [hospital.core :refer :all]
            [hospital.aula1 :as h]))

(def pacientes {})
(def new-paciente-matheus {:id 15 :nome "Xico" :nascimento "9/9/1994"})
(def expected {15 {:id 15 :nome "Xico" :nascimento "9/9/1994"}})
(def error {:nome "Test" :nascimento "throw"})


(deftest a-test
  (testing "Testing add paciente"
    (is (= (h/adiciona-paciente pacientes new-paciente-matheus) expected))
    (is (thrown? Exception (h/adiciona-paciente-se-id-existe-improve pacientes error)))))
