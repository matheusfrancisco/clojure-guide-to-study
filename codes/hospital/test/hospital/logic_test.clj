(ns hospital.logic-test
  (:require [clojure.test :refer :all]
            [hospital.logic :refer :all]
            [hospital.model :as h.model]
            [schema.core :as s]))

(s/set-fn-validation! true)

;;this is a simple test format
(deftest fits-in-queue?-test
  (testing "That it fits in the queue"
    (is (fits-in-queue? {:g-queue []} :g-queue)))
  ;; limit boundary
  (testing "That it doesn't fit a new patient when the queue is full"
    (is (not (fits-in-queue? {:g-queue [1 2 3 4 5]} :g-queue))))
  ;;one above limit boundary
  (testing "That it doesn't fit a new patient when the queue is the above full"
    (is (not (fits-in-queue? {:g-queue [1 2 3 4 5 8]} :g-queue))))

  ;;one above limit boundary
  (testing "That it doesn't fit a new patient when the queue is the above full"
    (is (fits-in-queue? {:g-queue [1 2 3 4]} :g-queue)))

  ;;queue with few people boundary
  (testing "That it doesn't fit a new patient when the queue is the above full"
    (is (fits-in-queue? {:g-queue [1 2]} :g-queue)))

  ;below limit boundary
  (testing "That it fits a new patient when the queue is below the limit"
    (is (fits-in-queue? {:g-queue [1 2 3 4]} :g-queue))
    (is (fits-in-queue? {:g-queue [1 2]} :g-queue)))

  (testing "That is does not fit in queue when the deparment doesn't exist"
    (is (not (fits-in-queue? {:g-queue [1 2 3 4]} :x-ray))))

  )



(deftest arrived-at-test

  (let [full-hospital {:g-queue [1 58 96 74 32]}]
    (testing "that the new patient will be added to the department if the queue is not full"
      ;bad implementation, only tests if what you wrote here is the same that
      ;you wrote in the function - that's obvious
      ;(is (= (update {:g-queue [1 2 3 4]} :g-queue conj 5)
      ;       (arrived-at {:g-queue [1 2 3 4]}, :g-queue, 5)))
      ;
      ;(is (= (update {:g-queue [1 2]} :g-queue conj 5)
      ;       (arrived-at {:g-queue [1 2]}, :g-queue, 5)))

      (is (= {:g-queue [1 2 3 4 5]}
             (arrived-at {:g-queue [1 2 3 4]}, :g-queue, 5)))

      (is (= {:g-queue [1 2 5]}
             (arrived-at {:g-queue [1 2]}, :g-queue, 5)))

      ;(is (= {:hospital {:g-queue [1 2 3 4 5]}, :result :success}
      ;       (arrived-at {:g-queue [1 2 3 4]}, :g-queue, 5)))
      ;
      ;(is (= {:hospital {:g-queue [1 2 5]}, :result :success}
      ;       (arrived-at {:g-queue [1 2]}, :g-queue, 5)))
      )

    (testing "that it won't add the new patient to the queue when the queue is full"
      ;classic terrible coding, the Exception is too generic
      (is (thrown? clojure.lang.ExceptionInfo
                   (arrived-at full-hospital, :g-queue, 47)))

      ;(is (nil? (arrived-at full-hospital, :g-queue, 47)))

      ;(is (try
      ;      (arrived-at full-hospital, :g-queue, 47)
      ;      false
      ;      (catch clojure.lang.ExceptionInfo e
      ;        (= :impossible-to-add-patient-to-the-queue (:type (ex-data e))))))

      ;(is (= {:hospital full-hospital, :result :impossible-to-add-patient-to-the-queue}
      ;       (arrived-at full-hospital, :g-queue, 47)))
      )
    )
  )

(deftest transfer-test
  (testing "that the transfer works if the patient will fit in the destination department"
    (let [original-hospital {:g-queue (conj h.model/empty-queue "5"),
                             :x-ray h.model/empty-queue}]
      (is (= {:g-queue [], :x-ray ["5"]}
             (transfer original-hospital, :g-queue, :x-ray)))
      )

    (let [original-hospital {:g-queue (conj h.model/empty-queue "51" "5"),
                             :x-ray   (conj h.model/empty-queue "13")}]
      (is (= {:g-queue ["5"], :x-ray ["13" "51"]}
             (transfer original-hospital, :g-queue, :x-ray)))
      )
    )

  (testing "that the transfer does not work if the patient won't fit in the destination department"
    (let [full-hospital {:g-queue (conj h.model/empty-queue "5"),
                         :x-ray   (conj h.model/empty-queue "1" "13" "45" "67" "92")}]
      (is (thrown? clojure.lang.ExceptionInfo
                   (transfer full-hospital :g-queue :x-ray))))
    )

  (testing "cannot invoke transfer without a hospital"
    (is (thrown? clojure.lang.ExceptionInfo
                 (transfer nil :g-queue :x-ray)))
    )

  (testing "mandatory conditions"
    (let [hospital {:g-queue (conj h.model/empty-queue "5"),
                    :x-ray   (conj h.model/empty-queue "1" "13" "45" "67")}]
      (is (thrown? AssertionError
                   (transfer hospital :random-dep :x-ray)))

      (is (thrown? AssertionError
                   (transfer hospital :x-ray :random-dep)))
      )
    )
  )