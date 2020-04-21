(ns example-pjc.data
  (:import
    (java.util HashMap Date)
    (org.eclipse.jgit.api Git))
  (:import ShiverMeTimbers))


(def data [{:customer-id "cid-1"
            :customer "Customer 1"
            :policy "Policy 1"
            :term 1000
            :settlement 300
            :brio-nr 100}
           {:customer-id "cid-2"
            :customer "Customer 2"
            :policy "Policy 2"
            :term 1000
            :settlement 300
            :brio-nr 100}
           {:customer-id "cid-3"
            :customer "Customer 3"
            :policy "Policy 3"
            :term 1000
            :settlement 300
            :brio-nr 100}
           {:customer-id "cid-4"
            :customer "Customer 4"
            :policy "Policy 4"
            :term 1000
            :settlement 300
            :brio-nr 100}])

(println (map (fn [val]
       (if (= "cid-3" (:customer-id val))
         ;; Do what you need to
         (assoc val :match true)
         ;; Otherwise, leave it be
         val))
     data))


(println "-----------------")
(println (Date.))


(defn -main [& args]
  (println (Date.))
  (ShiverMeTimbers/main)
  (ShiverMeTimbers/thisMain))
;{:customer-id "cid-1",
;  :customer "Customer 1",
;  :policy "Policy 1",
;  :term 1000,
;  :settlement 300,
;  :brio-nr 100}
; {:customer-id "cid-2",
;  :customer "Customer 2",
;  :policy "Policy 2",
;  :term 1000,
;  :settlement 300,
;  :brio-nr 100}
; {:customer-id "cid-4",
;  :customer "Customer 4",
;  :policy "Policy 4",
;  :term 1000,
;  :settlement 300,
;  :brio-nr 100}
; {:customer-id "cid-3",
;  :customer "Customer 3",
;  :policy "Policy 3",
;  :term 1000,
;  :settlement 300,
;  :brio-nr 100,
;  :match true}
