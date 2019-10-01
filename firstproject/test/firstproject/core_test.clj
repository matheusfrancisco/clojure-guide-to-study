(ns firstproject.core-test
  (:require [clojure.test :refer [is deftest testing]]))


(deftest my-test
  (testing "1+ 1= 2"
    (is (=(+ 1 1) 2)))

  (testing "Ensure 2- 1 = 1"
    (is (= (- 2 1) 1)))
   )
