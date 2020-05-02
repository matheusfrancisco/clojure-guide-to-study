(ns project2.core
  (:import MyClass))

(def myinstance (MyClass. "Johnny"))

(.getName myinstance)

;(gen-class
;  :name project2.Myclass
;  :prefix "my-"
;  :methods [[getName [] String]]
;  :constructors {[String] []}
;  :state state
;  :init init
;)

;(defn my-init [name]
;  [[] {:name name}])

;(defn my-getName [this]
;  (get (.state this) :name))
