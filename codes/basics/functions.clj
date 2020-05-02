
(def hello (fn [] "Hello"))

(defn hello [] "Hello")
(defn hello [name] (str "Hello, "name))


(defn "Greets a person name <name> with their <title>" [name, title] (str "Hello, " title "  " name))

(require '[clojure.repl :refer [doc]]) nil
(doc hello)

(defn hello [& args]
  (str "Hello, " (apply str args)))

(hello "Fred" "Julie")

(defn hello
   ([] "Hello, World")
   ([name] (str "Hello, "name)))
