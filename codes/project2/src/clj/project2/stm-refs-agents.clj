(ns project2.core)

;(def acct1 (atom 0 :validator #(>= % 0)))
;(def acct2 (atom 0 :validator #(>= % 0)))

(def acct1 (ref 1000 :validator #(>= % 0)))
(def acct2 (ref 1000 :validator #(>= % 0)))

(defn transfer [from-acct to-acct amt]
  (dosync
    (alter to-acct + amt)
    (alter from-acct - amt)))


;(defn transfer [from-acct to-acct amt]
;  (swap! to-acct + amt)
;  (swap! from-acct - amt))

(dotimes [_ 1000]
    (future (transfer acct2 acct1 100)))

@acct1
@acct2


(def my-agent (agent 0 :validator #(>= % 0) :error-mode :continue
                     :error-handler println))

(send my-agent inc)
(restart-agent my-agent 0)
@my-agent 2
