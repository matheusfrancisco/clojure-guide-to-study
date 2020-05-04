;You might think that when is a special form like if. Well guess what?
;It’s not! In most other languages, you can only create conditional
;expressions using special keywords, and there’s no way to create your own
;conditional operators. However, when is actually a macro

(when boolean-expression
  expression-1
  expression-2
  expression-3
  ...
  expression-x)

;In this macro expansion, you can see that when is implemented in terms
;of if and do:
(macroexpand '(when boolean-expression
                      expression-1
                      expression-2
                      expression-3
                      ))
;=> (if boolean-expression
;        (do expression-1
;            expression-2
;            expression-3))


;Anatomy of a Macro
;Macro definitions look much like function definitions. They have a name, an
;optional document string, an argument list, and a body. The body will
;almost always return a list. This makes sense because macros are a way of
;transforming a data structure into a form Clojure can evaluate, and Clojure
;uses lists to represent function calls, special form calls, and macro calls.
;You can use any function, macro, or special form within the macro body,
;and you call macros just like you would a function or special form.
;As an example, here’s our old friend the infix macro:

(defmacro infix
  "Use this macro when you pine for the notation of your ch ildhood"
  [infixed]
  (list (second infixed) (first infixed) (last infixed)))


(infix (1 + 1))
(macroexpand '(infix (1 + 1)))


(defmacro infix-2
  [[operand1 op operand2]]
  (list op operand1 operand2)

(defmacro and
  "Evaluates exprs one at a time, from left to right. If a
  form 
  returns logical false (nil or false), and returns
  that value and doesn't evaluate any of the other expressions, otherwise
  it returns
  the value of the last expr. (and) returns true."
  {:added "1.0"}
  ([] true)
  ([x] x)
  ([x & next]
   `(let [and# ~x]
      (if and# (and ~@next) and#)))


;;
;;Building A lists for evaluation

;;Distinguishing Symbols and Values
(let [result expression]
  (println result)
  result)

(defmacro my-print-whoopsie
  [expression]
  (list let [result expression]
        (list println result)
        result))
;However, if you tried this, you’d get the exception Can't take the
;value of a macro: #'clojure.core/let. What’s going on here?
;The reason this happens is that your macro body tries to get the value
;that the symbol let refers to, whereas what you actually want to do is return
;the let symbol itself. There are other problems, too: you’re trying to get the
;value of result, which is unbound, and you’re trying to get the value of
;println instead of returning its symbol. Here’s how you would write the
;macro to do what you want


(defmacro my-print
  [expression]
  (list 'let ['result expression]
        (list 'println 'result)
        result))

;Here, you’re quoting each symbol you want to use as a symbol by
;prefixing it with the single quote character, '. This tells Clojure to turn off
;evaluation for whatever follows, in this case preventing Clojure from trying
;to resolve the symbols and instead just returning the symbols. The ability to
;use quoting to turn off evaluation is central to writing macros, so let’s give
;the topic its own sectio

;;Simple Quoting

;So far, you’ve seen macros that build up lists by using the list function to
;create a list along with ' (quote), and functions that operate on lists like
;first, second, last, and so on. Indeed, you could write macros that way
;until the cows come home. Sometimes, though, it leads to tedious and
;verbose code.
;Syntax quoting returns unevaluated data structures, similar to normal
;quoting. However, there are two important differences. One difference is
;that syntax quoting will return the fully qualified symbols (that is, with the
;symbol’s namespace included). Let’s compare quoting and syntax quoting.
;Quoting does not include a namespace if your code doesn’t include a
;namespace:

'+
; => +
'clojure.core/+
; => clojure.core/+
`(+ 1 ~(inc 1))

; => (clojure.core/+ 1 2)
`(+ 1 (inc 1))
; => (clojure.core/+ 1 (clojure.core/inc 1))

name "Xico"
(println "Churn your butter,#{name}!")


(list '+ 1 (inc 1))
; => (+ 1 2)
`(+ 1 ~(inc 1))
; => (clojure.core/+ 1 2)




;Using syntax quoting in a macro

(defmacro code-critic
    "Phrases are courtesy Hermes Conrad from Futurama"
    [bad good]
    (list 'do
              (list 'println
                          "Great squid of Madrid, this is bad code:"
                          (list 'quote bad))
              (list 'println
                          "Sweet gorilla of Manila, this is good code:"
                          (list 'quote good))))

(code-critic (1 + 1) (+ 1 1))

; conjure/out | Great squid of Madrid, this is bad code: (1 + 1)
; conjure/out | Sweet gorilla of Manila, this is good code: (+ 1 1)

(defmacro code-critic
    "Phrases are courtesy Hermes Conrad from Futurama"
    [bad good]
    (do (println "G :"
                 (quote ~bad))
        (print "S code:"
                 (quote ~good))))



;Refactoring a Macro and Unquote Splicing


(defn criticize-code
    [criticism code]
    `(println ~criticism (quote ~code)))


(defmacro code-critic
    [bad good]
    `(do ~(criticize-code "Cursed bacteria of Liberia, this is bad code:" bad)
         ~(criticize-code "Sweet sacred boa of western and samoa, this is good code :" good)))



;; Validatoin Functions

(def order-details
    {:name "Xico Francisco"
     :email "xicohotmail.com"})

(def order-details-validations
    {:name ["Please enter a name" not-empty]
     :email ["Pease enter an email address" not-empty
             "Yout email address doesn't look like an email"
             #(or (empty? %) (re-seq #"@" %))]})


(defn error-messages-for
    "Return a seq of error messages"
    [to-validate message-validator-pairs]
    (map first (filter #(not  ((second %) to-validate))
                       (partition 2 message-validator-pairs))))


(error-messages-for "" ["Please enter a name" not-empty])

(defn validate
    "Returns a map with a vector of errors for each key"
    [to-validate validations]
    (reduce (fn [errors validation]
                    (let [[fieldname validation-check-groups] validation
                          value (get to-validate fieldname)
                          error-messages (error-messages-for value validation-check-groups)]
                      (if (empty? error-messages)
                          errors
                          (assoc errors fieldname error-messages))))
            {}
            validations))

(validate order-details order-details-validations)
; => {:email ("Yout email address doesn't look like an email")}

(let [errors (validate order-details order-details-validations)]
  (if (empty? errors)
    (println :sucess)
    (println :failure errors)))

(defn if-valid
  [record validations sucess-code failure-code]
  (let [errors (validate record validations)]
    (if (empty? errors)
      sucess-code
      failure-code)))

(if-valid order-details order-details-validations errors
    (render :sucess)
    (render :failure errors))

(defmacro if-valid
  "Handle validation more concisely"
  [to-validate validations errors-name & then-else]
  (let [~erros-name (validate ~to-validate ~validations)]
    (if (empty? ~errors-name)
      ~@then-else)))

;This macro takes four arguments: to-validate, validations,
;errors-name, and the rest argument then-else. Using errors-name like
;this is a new strategy. We want to have access to the errors returned by the
;validate function within the then-else statements. To do this, we tell the
;macro what symbol it should bind the result to. The following macro
;expansion shows how this works


