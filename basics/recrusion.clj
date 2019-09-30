(ns learnclojure)


(defn my-sum [total vals]
  (if empty? vals)
    total
     (my-sum (+ (firs vals) total) (rest vals))))

(defn my-sum2
  ([vals] (my-sum 0 vals))
  ([total vals]
   (if (empty? vals)
     total
     (my-sum (+ (first vals) total) (rest vals)))))


(defn sum [vals]
  (loop [total 0 vals vals]
    (if (empty? vals)
      total
      (recur (+ (first vals) total) (rest vals)))))

(reduce (fn [total vals] (+ total vals)))


(defn filter-even [acc next-val]
  (if (even? next-val)
    (conj acc next-val)
    acc))

(reduce filter-even [] [0 1 2 3 4 5])

(filter even? [0 1 2 3 4 5 6])


(defn group-even [acc next-val]
  (let [key (if (even? next-val) :even :odd)]
    (update-in acc [key] #(conj % next-val))))

(reduce group-even {} [0 1 2 3 4 5 6])
