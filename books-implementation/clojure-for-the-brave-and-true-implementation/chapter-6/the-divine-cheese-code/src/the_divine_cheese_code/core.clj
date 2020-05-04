(ns the-divine-cheese-code.core
  (:require [clojure.java.browse :as browse]
            [the-divine-cheese-code.visualization.svg :refer [xml]])
  (:gen-class))

;; namespaces imports
;;(require 'the-divine-cheese-code.visualization.svg)
;;(refer 'the-divine-cheese-code.visualization.svg)
;; is equivalent  to
;;(use 'the-divine-cheese-code.visualization.svg)

;;
;; (require '[the-divine-cheese-code.visualization.svg :as svg])
;; ensure that the svg code is evaluated
;;(require 'the-divine-cheese-code.visualization.svg)
;; fully qualified name to reference svg functions
;;(refer 'the-divine-cheese-code.visualization.svg)

;;;or
;;(require 'the-divine-cheese-code.visualization.svg)
;;(refer 'the-divine-cheesecode.visualization.svg :as :only ['points])


(def heists [{:location "Cologne, Germany"
              :cheesename "Archbishop Hildebold's Cheese Pretzel"
              :lat 50.95
              :lng 6.97}
             {:location "Zurich, Switzerland"
              :cheese-name "The Standard Emmental"
              :lat 47.37
              :lng 8.55}
             {:location "Marseille, France"
              :cheese-name "Le Fromage de Cosquer"
              :lat 43.30
              :lng 5.37}
             {:location "Zurich, Switzerland"
              :cheese-name "The Lesser Emmental"
              :lat 47.37
              :lng 8.55}
             {:location "Vatican City"
              :cheese-name "The Cheese of Turin"
              :lat 41.90
              :lng 12.45}])


(defn url
  [filename]
  (str "file:///"
       (System/getProperty "user.dir")
       "/"
       filename))


(defn template
  [contents]
  (str "<style>polyline { fill:none; stroke:#5881d8; stroke-width:3}</style>"
       contents))

(defn -main
    [& args]
    (let [filename "map.html"]
      (->> heists
           (xml 50 100)
           template
           (spit filename))
      (browse/browse-url (url filename))))
