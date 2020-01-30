; hobbit implementation in file hobbit-model.clj

;let
;In the mass of craziness in Listing 3-1 you can see a form of the sctructure
;(let...)

; bind the name x to the value 3
(let [x 3]
  x) ; => 3

(def dalmatian-list
  ["Pongo" "Puppy" "P2"])

;bind the name dalmatians to the result of the expression (take 2 dalmatian-list)
(let [dalmatians (take dalmatian-list 2)]
    dalmatians)

; global context
(def x 0)

; let context (scope)
(let [x 1] x)

(def x 0)
(let [x (inc x)] x)


; in this case pongo name in pongo and rest of the dalmatian-list to dalmatians.

(let [[pongo & dalmatians] dalmatian-list]
  [pongo dalmatians])

(let [[part & remaining] remaining-asym-parts]
  (recur remaining
         (into final-body-parts
               (set [part (matching-part part)]))))


;loop
(loop [iteration 0]
  (println (str "Iteration" iteration))
  (if (> iteration 3)
    (println "Goodbye!")
    (recur (inc iteration))))


(defn some-join [coll result]
          (if (= 1 (count coll)) (str result (first coll))
            (do
              (println result)
              (recur (rest coll) (str result (first coll) ", ")))))

(some-join ["hello" "world" "love" "coding"] "Words: ")

(if (= 1 (count ["h"])) "oi2" 
  "oi")

