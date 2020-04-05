(ns fwpd.core)

(def filename "suspects.csv")

(slurp filename)

(def vamp-keys [:name :glitter-index])

(defn str->int
  [str]
  (Integer. str))

(def conversions {:name identity
                  :glitter-index str->int})

(defn convert
  [vamp-key value]
  ((get conversions vamp-key) value))


(defn parse
  "Convert a CSV into rows of columns"
  [string]
  (map #(clojure.string/split % #",")
       (clojure.string/split string #"\n")))

(defn mapify
  "Return a seq of maps like {:name \"Edward Cullen\" :glit ter-index 10}"
  [rows]
  (map (fn [unmapped-row]
         (reduce (fn [row-map [vamp-key value]]
                   (assoc row-map vamp-key (convert vamp-key value)))
                 {}
                 (map vector vamp-keys unmapped-row)))
       rows))


;In this function, map transforms each row—vectors like ["Bella Swan"
;0]—into a map by using reduce in a manner similar to the first example in
;“reduce” on page 80. First, map creates a seq of key-value pairs like
;([:name "Bella Swan"] [:glitter-index] 0). Then, reduce builds
;up a map by associating a vamp key with a converted vamp value into rowmap. Here’s the first row mapified:


(defn glitter-filter
  [minimum-glitter records]
  (filter #(>= (:glitter-index %) minimumglitter) records)

(glitter-filter 3 (mapify (parse (slurp filename))))
