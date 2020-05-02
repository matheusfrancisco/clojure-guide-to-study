
(defmacro backwards
  [form]
  (reverse form))

(backwards (" backwards  " "am " "I " str))
;=> "I am  backwards  "

;; The backwards macro allows Clojure to successfully evaluate
;; the expression, even though it doesn't follow Clojure's built-in syntax rules
;;, which require an expression's operand
;; to appear first (not to metion the rule that an expression not be written in reverse order)
;; 
;Without backwards, the expression would fail harder than
;millennia of alchemists ironically spending their entire lives pursuing an
;impossible means of achieving immortality. With backwards, you’ve
;created your own syntax! You’ve extended Clojure so you can write code
;however you please! Better than turning lead into gold, I tell you!


; An Overview of Clojure's Evaluation Model
;
;


(def addition-list (list + 1 2))
(eval addition-list)
(eval (concat addition-list [10]))
;(list 'def 'lucky-number (concat addition-list [10]))

(read-string "(+ 1 2)")
(list? (read-string "(+ 1 2)"))
(conj (read-string "(+ 1 2)") :zagglewag)



(eval (read-string "(+ 1 2)"))

(#(+ 1 %) 3)


(let [x 5]
    (+ x 3))


(if true 2 3)
(quote (a b c))

;;
;;
;;Syntatic Abstraction and the -> Macro

(defn read-resource
    "Read a resource into a string"
    [path]
    (read-string (slurp (clojure.java.io/resource path))))

(defn read-resource-threading
    [path]
    (-> path
        clojure.java.io/resource
        slurp
        read-string))

;You can read this as a pipeline that goes from top to bottom instead of
;from inner parentheses to outer parentheses. First, path gets passed to
;io/resource, then the result gets passed to slurp, and finally the result of
;that gets passed to read-string.
;These two ways of defining read-resource are entirely equivalent.
;However, the second one might be easier understand because we can
;approach it from top to bottom, a direction we’re used to. The -> also lets us
;omit parentheses, which means there’s less visual noise to contend with.
;This is a syntactic abstraction because it lets you write code in a syntax
;that’s different from Clojure’s built-in syntax but is preferable for human
;consumption. Better than lead into gold!!!
