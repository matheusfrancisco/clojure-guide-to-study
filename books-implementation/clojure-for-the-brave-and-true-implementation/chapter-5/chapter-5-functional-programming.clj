; Pure Function
; Living with immutable data struct
; Function Composition

(require '[clojure.string :as s])

(defn clean
  [text]
  (s/replace (s/trim text) #"lol" "LOL"))

(clean "My boa constrictor is so sassy lol! ")
; => "My boa constrictor is so sassy LOL!"

;Combining functions like this—so that the return value of one function
;is passed as an argument to another—is called function composition. In fact,
;this isn’t so different from the previous example, which used recursion,
;because recursion continually passes the result of a function to another
;function; it just happens to be the same function. In general, functional
;programming encourages you to build more complex functions by
;combining simpler functions


;Cool Things to Do with Pure Functions

;you can derive new functions from existing functions
;in the same way that you derive new data from existing data.
; you've already see one Function, partial, that creates a new functions.

;This sections introduces you to two more functions, comp, and memoize

;Comp

((comp inc * ) 2 3)
;=> 7
;Here, you create an anonymous function by composing the inc and *
;functions. Then, you immediately apply this function to the arguments 2
;and 3

(def character
  {:name "Smooches McCutes"
   :attributes {:intelligence 10
                :strength 4
                :dexterity 5}})


(def c-int (comp :intelligence :attributes))
(def c-str (comp :strength :attributes))
(def c-dex (comp :dexterity :attributes))

(c-int character)
; => 10

(c-str character)
; => 4

(c-dex character)
; => 5
