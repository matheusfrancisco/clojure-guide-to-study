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

;Better Symmetrizer with reduce

(reduce + [1 2 3 4])
; (+ (+ (+ 1 2) 3) 4)
;=>10

; Reduce works, apply the given function to the frist two elements of sequence. That's where (+ 1 2) comes from.
; Apply the given  fucntion to the result and the next element of the sequence. In this case, the result of step 1 is 3, and the next element of the sequence.
; is 3 as well, So the final result is (+ 3 3)
; Repeat step 2 for every remaining element in the sequence.
;
; Reduce also take an optional initial value. The initial value her is 15:
(reduce + 15 [ 1 2 3 4 ])

;My reduce
; create our reduce
(defn my-reduce
  ([func initial-value coll]
   (loop [result initial-value
          remaining coll]
     (if (empty? remaining)
       result
       (recur (func result (first remaining)) (rest remaining)))))
    ([func [head & tail]]
     (my-reduce func head tail)))

; in file hobbit-model.clj We could reimplement our symmetrizer as follows:

(def asym-hobbit-body-parts
  [{:name "head" :size 3}
   {:name "left-eye" :size 1}
   {:name "left-ear" :size 1}
   {:name "mount" :size 1}
   {:name "nose" :size 1}
   {:name "neck" :size 2}
   {:name "left-shoulder" :size 3}
   {:name "left-upper-arm" :size 3}
   {:name "chest" :size 10}
   {:name "back" :size 10}
   {:name "left-forearm" :size 3}
   {:name "abdomen" :size 6}
   {:name "left-kidney" :size 1}
   {:name "left-hand" :size 2}
   {:name "left-knee" :size 2}
   {:name "left-thigh" :size 4}
   {:name "left-lower-leg" :size 3}
   {:name :size "left-achilles" 1}
   {:name "left-foot" :size 2}])


(defn matching-part
  [part]
  {:name (clojure.string/replace (:name part) #"^left-" "right-") :size (:size part)})

(defn symetrize-body-parts
  "Expects a seq of maps that have a :name and :size"
  [asym-body-parts]
  (loop [remaining-asym-parts asym-body-parts
         final-body-parts []]
    (if (empty? remaining-asym-parts)
      final-body-parts
      (let [[part & remaining] remaining-asym-parts]
        (recur remaining
               (into final-body-parts
                     (set [part (matching-part part)])))))))

(symetrize-body-parts asym-hobbit-body-parts)

;=>[{:name "head", :size 3}
; {:name "left-eye", :size 1}
; {:name "right-eye", :size 1}
; {:name "left-ear", :size 1}
; {:name "right-ear", :size 1}
; {:name "mount", :size 1}
; {:name "nose", :size 1}
; {:name "neck", :size 2}
; {:name "left-shoulder", :size 3}
; {:name "right-shoulder", :size 3}
; {:name "right-upper-arm", :size 3}
; {:name "left-upper-arm", :size 3}
; {:name "chest", :size 10}
; {:name "back", :size 10}
; {:name "left-forearm", :size 3}
; {:name "right-forearm", :size 3}
; {:name "abdomen", :size 6}
; {:name "left-kidney", :size 1}
; {:name "right-kidney", :size 1}
; {:name "left-hand", :size 2}
; {:name "right-hand", :size 2}
; {:name "right-knee", :size 2}
; {:name "left-knee", :size 2}
; {:name "right-thigh", :size 4}
; {:name "left-thigh", :size 4}
; {:name "right-lower-leg", :size 3}
; {:name "left-lower-leg", :size 3}
; {:name ":size", :size nil}
; {:name :size, "left-achilles" 1}
; {:name "right-foot", :size 2}
; {:name "left-foot", :size 2}]

; re-implemented with reduce
(defn better-symmetrize-body-parts
  "Expects a seq of maps that have a :name and :size"
  [asym-body-parts]
  (reduce (fn [final-body-parts part ]
            (into final-body-parts (set [part (matching-part part)])))
          []
          asym-body-parts))


;functions to determines which part of a hobbit is hit:

(defn hit
  [asym-body-parts]
  (let [sym-parts (better-symmetrize-body-parts asym-hobbit-body-parts)
        target (rand body-part-size-sum)]
    (loop [[part & remaining] sym-parts
           accumulated-size (:size part)]
    (if (> accumulated-size target)
      part
      (recur remaining (+ accumulated-size (:size (first remaining))))))))


