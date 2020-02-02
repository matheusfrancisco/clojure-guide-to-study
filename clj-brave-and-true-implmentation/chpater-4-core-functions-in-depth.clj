; Programing to Abstractions

(defn titleize
  [topic]
  (str topic " for the Brave and True"))

(map titleize ["Matheus" "Ragnarok"])
; => ("Matheus for the Brave and True" "Ragnarokfor the Brave and True")
(map titleize #{"Matheus" "Ragnarok"})
; => ("Matheus for the Brave and True" "Ragnarok for the Brave and True")
(map titleize '("Matheus" "Ragnarok"))

;=> ("Matheus for the Brave and True" "Ragnarok for the Brave and True")
(map #(titleize (second %)) {:unconmfortable-thing "Matheus"})

;=> ("Matheus for the Brave and True" "Ragnarok for the Brave and True")


; Abstraction Through Indirection

; map, first, rest, cons -- it calls the seq function

(seq '(1 2 3))
;=> (1 2 3)
(seq [1 2 3])
;=> (1 2 3)
(seq #{1 2 3})
;=> (1 2 3)
(seq {:name "Matheus" :occupation "Dev"})
;=>([:name "Matheus"] [:occupation "Dev"])

(into {} (seq {:a 1 :b 2 :c 3}))
; => {:a 1, :c 3, :b 2}

;As ong as data structure implements the sequence abstractions, it can use the extensive seq library, which includes such
;superstar functions as reduce, filter,distinct, group-by, and dozens more.


;map
(map inc [1 2 3])
; => (2 3 4)

(map str ["a" "b" "c"] ["A" "B" "C"])
; => ("aA" "bB" "cC")

;when you pass map multiple collections, the elements of the first
;collection (["a" "b" "c"]) will be passed as the first argument of the mapping
;function the elements of the second collection (["A" "B"]) will be passed as the second
; argument, and so on. Just be sure that your mapping functoin can take a number of arguments equal to
; the number of collections you're passing to map


(def human-consumption [8.1 7.3 6.6 5.0])
(def critter-consumption [0.0 0.2 0.3 1.1])
(defn unify-diet-data
  [human critter]
  {:human human
   :critter critter})
(map unify-diet-data human-consumption critter-consumption)
; => ({:human 8.1, :critter 0.0}
;{:human 7.3, :critter 0.2}
;{:human 6.6, :critter 0.3}
;{:human 5.0, :critter 1.8})


(def sum #(reduce + %))
(def avg #(/ (sum %) (count %)))
(defn stats
  [numbers]
  (map #(% numbers) [sum count avg]))

(stats [3 4 10])
; stats function interates over a vector of functions, applying each functoin to numbers.

;Additionally, Clojurists often use map to retrieve the value associated
;with a keyword from a collection of map data structures. Because keywords
;can be used as functions, you can do this succinctly. Here’s an example:

(def identities
  [{:alias "Batman" :real "Bruce Wayne"}
   {:alias "Spider-Man" :real "Peter Parker"}
   {:alias "Santa" :real "Your mom"}
   {:alias "Easter Bunny" :real "Your dad"}])

(map :real identities)
; => ("Bruce Wayne" "Peter Parker" "Your mom" "Your dad")


;Reduce

(reduce (fn [new-map [key val]]
          (assoc new-map key (inc val)))
          {}
          {:max 30 :min 10})
; => {:max 31, :min 11}

;In this example, reduce treats the argument {:max 30 :min 10} as a
;sequence of vectors, like ([:max 30] [:min 10]). Then, it starts with an
;empty map (the second argument) and builds it up using the first argument,
;an anonymous function. It’s as if reduce does this:

(assoc (assoc {} :max (inc 30))
       :min (inc 10))


(reduce (fn [new-map [key val]]
          (if (> val 4)
            (assoc new-map key val)
            new-map))
        {}
        {:human 4.1 :critter 3.9})
; => {:human 4.1}


;The takeaway here is that reduce is a more flexible function than it first
;appears. Whenever you want to derive a new value from a seqable data
;structure, reduce will usually be able to do what you need. If you want an
;exercise that will really blow your hair back, try implementing map using
;reduce, and then do the same for filter and some after you read about
;them later in this chapter


;Take, drop, take-while, and drop-while

(take 3 [1 2 3 4 5 6 7 8 9 10])
; => (1 2 3)

(drop 3 [1 2 3 4 5 6 7 8 9 10])
; => (4 5 6 7 8 9 10)







