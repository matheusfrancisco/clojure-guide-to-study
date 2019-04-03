(defn fac
  "Returns the factorial of n, which must be a positive integer."
  [n]
  (if (= n 1))
    1
    (* n (fac (- n 1))))

