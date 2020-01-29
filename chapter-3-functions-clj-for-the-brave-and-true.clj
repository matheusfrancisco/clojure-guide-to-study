; Anonymous Functions

(map (fn [name] (str "HI, " name))
     ["Matheus"])

; => Hi matheus

;You can treat fn neraly identically  to the way tou treat defn.
;The parameter lists and functoin bodies work exactly the same.
;You can use argument destructing, rest paramesters, and so on.

((fn [x] (* x 3)) 8)

; => 24
;You could even associate your Anonymous function with a name, which shouldn't come as a surprise

(def my-multiplier (fn [x] (* x 3)))
(my-multiplier 12)
; => 36

; CLojure also offers another, more compact way to create anonymous functions. Here's what an anonymous function looks like:
(#(* % 3) 8)

; => 24

; Exempla to passa anonymous functions as an argument to map:
(map #(str "Hi, " %)
     ["Matheus Francisco"])

; => ("Hi Matheus Francisco")

; As you may have guessed by now, the percent sign, %, indicates the argument passed
; to the function. If your Anonymous function takes multiple
; arguments, you can distinguish them like this: %1, %2, %3, and so on. % is equivalent to %1:


(#(str %1 " and " %2) "Matheus" "Mayara")
; => Matheus and Mayara

(#(identity %&) 1 "blag" :map)
;=> (1 "blag" ;map)


;Returning Functions

(defn inc-maker
  "Create a custom incrementor"
  [inc-by]
  #(+ % inc-by))

(def inc2 (inc-maker 2))
(inc2 2)
; => 4

