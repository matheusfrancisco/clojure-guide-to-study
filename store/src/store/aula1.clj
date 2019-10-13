(ns store.aula1)

;["daniela" "guilherme" "carlos" "paulo" "lucia" "ana"]
;{"guilherme" 37, "paulo" 39}
;'(1 2 3 4 5)
;[[0 1]]
;#{}

; map
; reduce
; filter

(map println ["daniela" "guilherme" "carlos" "paulo" "lucia" "ana"])
(println (first ["daniela" "guilherme" "carlos" "paulo" "lucia" "ana"]))
(println (rest ["daniela" "guilherme" "carlos" "paulo" "lucia" "ana"]))
(println (rest []))
(println (next ["daniela" "guilherme" "carlos" "paulo" "lucia" "ana"]))
(println (next []))

(println (first (next ["daniela" "guilherme" "carlos" "paulo" "lucia" "ana"])))
(println (seq []))
(println (seq [1 2 3 4 5]))



(println "\n\n\n My MAP")
;loop infinit
(defn meu-map-inf-loop
  [funcao sequencia]
  (let [primeiro (first sequencia)]
    (funcao primeiro)
    (meu-map-inf-loop funcao (rest sequencia))))

;(meu-map println ["matheus" "jao"])
(println "My MAP inf loop")
;if false ...
(defn meu-map-if-stop-in-null
  [funcao sequencia]
  (let [primeiro (first sequencia)]
    (if primeiro
      (do
        (funcao primeiro)
        (meu-map-if-stop-in-null funcao (rest sequencia))))))

;(meu-map println ["matheus" false "jao"])
(println "My MAP null")
(defn
  [funcao sequencia]
  (let [primeiro (first sequencia)]
    (if (not (nil? primeiro))
      (do
        (funcao primeiro)
        (meu-map-if-not funcao (rest sequencia))))))


;(meu-map println ["matheus" false "jao"])
;(meu-map println ["matheus" "false" "jao"])
;(meu-map println [])

(println "My MAP Recur")

; TAIL RECURSION
(defn meu-map
  [funcao sequencia]
  (let [primeiro (first sequencia)]
    (if (not (nil? primeiro))
      (do
        (funcao primeiro)
        (recur funcao (rest sequencia))))))

(meu-map println (range 100))
