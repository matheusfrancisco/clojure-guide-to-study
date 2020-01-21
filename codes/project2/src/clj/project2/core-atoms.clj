(ns project2.core)

(def count (atom 0 :validator integer?))

;(reset! count "ho")
(dotimes [_ 1000]
  (future (swap! count inc))))

@count
