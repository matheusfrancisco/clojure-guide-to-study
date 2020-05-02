(ns learning-jdbc.core
  (:require [clojre.java.jdbc :as db]
            [jdbc.pool.c3p0 :as pool]))


(def my-db {:subprotocol "postgresql"
            :subname "//127.0.0.1:clojure_test"
            :user "clojure_test"})


(def my-pool (pool/make-datasource-spec my-db))

(db/execute! my-pool
   ["DROP TABLE IF EXISTS employees"])

(db/execute! my-pool
   ["CREATE TABLE employees (id serial PRIMARY KEY,
    name text, email text)"])


(db/execute! my-pool
   ["INSERT INTO employees (name, email) VALUES (?, ?)
    "Joe" "ma@ma.com.br" "])

(db/insert! my-pool
  "employees"
  {:name "Jackie" :email "jack@jac.com"})

(def employees (db/query my-pool ["SELECT * FROM employees"]))
(db/query my-pool ["SELECT * FROM employees WHERE id=? LIMIT 1" 3])

(db/update! my-pool
    "employees"
    {:name "Joe"}
    ["name=? AND email=?" "Joe" "ma@exemple.com"])

(db/detele!  my-pool
            "employees"
            ["name=?" "Francine"])

(db/with-db-transaction [txn my-pool]
  (db/delete! txn "employees" ["name='Joey'"])
