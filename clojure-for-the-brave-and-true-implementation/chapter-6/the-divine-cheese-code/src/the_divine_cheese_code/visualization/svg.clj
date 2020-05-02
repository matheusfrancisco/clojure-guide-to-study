(ns the-divine-cheese-code.visualization.svg
  (:require [clojure.string :as s])
  (:refer-clojure :exclude [min max]))


;You define the comparator-over-maps function at. This is probably
;the trickiest bit, so bear with me. comparator-over-maps is a function that
;returns a function. The returned function compares the values for the keys
;provided by the ks parameter using the supplied comparison function,
;comparison-fn.
;You use comparator-over-maps to construct the min and max
;functions ➍, which you’ll use to find the top-left and bottom-right corners
;of our drawing. Here’s min in action:
;
;(min [{:a 1 :b 3} {:a 5 :b 0}])
;; => {:a 1 :b 0}

;When you call min, it calls zipmap , which takes two arguments, both
;seqs, and returns a new map. The elements of the first seq become the keys,
;and the elements of the second seq become the values:
;
;(zipmap [:a :b][1 2])
; => {:a 1 :b 2}}
;At , the first argument to zipmap is ks, so the elements of ks will be
;the keys of the returned map. The second argument is the result of the map
;call at . That map call actually performs the comparison.
;Finally, at  you use comparator-over-maps to create the comparison
;functions. If you think of the drawing as being inscribed in a rectangle, min
;is the corner of the rectangle closest to (0, 0) and max is the corner farthest
;from it

(defn comparator-over-maps
  [comparasion-fn ks]
  (fn [maps]
    (zipmap ks
            (map (fn [k] (apply comparasion-fn (map k maps)))
                 ks))))


(def min (comparator-over-maps clojure.core/min [:lat :lng]))
(def max (comparator-over-maps clojure.core/max [:lat :lng]))


(defn translate-to-00
    [locations]
    (let [mincoords (min locations)]
      (map #(merge-with - % mincoords) locations)))

(defn scale
    [width height locations]
    (let [maxcoords (max locations)
          ratio {:lat (/ height (:lat maxcoords))
                 :lng (/ width (:lng  maxcoords))}]
      (map #(merge-with * % ratio) locations)))



;(merge-with - {:lat 50 :lng 10} {:lat 5 :lng 5})
; {;lat 45 :lng 5}

(defn latlng->point
  "Convert lat/lng map to comma-separated string"
  [latlng]
  (str (:lng latlng) "," (:lat latlng)))


(defn points
  [locations]
  (clojure.string/join " " (map latlng->point locations)))


(defn line
    [points]
    (str "<polyline points=\"" points "\" />"))


(defn transform
  "Just chains other functions"
  [width height locations]
  (->> locations
       translate-to-00
       (scale width height)))

(defn xml
    "svg 'template', which also flips the coordinate system"
    [width height locations]
    (str "<svg height=\"" height "\" width=\"" width "\">"
         ;; These two <g> tags flip the coordinate system
         "<g transform=\"translate (0, "height ") \">"
         "<g transform=\"scale (1, -1) \">"
         (-> (transform width height locations)
             points
             line)
         "</g></g>"
         "</svg>"))

