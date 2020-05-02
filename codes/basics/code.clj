(ns authorizer.code
  (:require [cheshire.core :refer [parse-string generate-string]]
            [clojure.edn :as edn]
            [clojure.walk :as walk]
            [clojure.string :as str]
            [schema.core :as s]
            [clojure.data.json :as json])
  (:use [clojure pprint]
        [clojure.java.io]))
(def accounts {})

(defn create-account
  [account]
  (pprint account))


(def string-or-keyword (s/if keyword? s/Keyword s/Str))

(s/defn ^:private replace-char :- s/Keyword
        ;; Replaces the from character with the to character in s, which can be a String or a Keyword
        ;; Does nothing if s is a keyword that is in the exception set
        [s :- string-or-keyword, from :- Character, to :- Character, exceptions :- #{s/Keyword}]
        (if (contains? exceptions s)
          s
          (keyword (str/replace (name s) from to))))

(def underscore->dash-exceptions #{})

(s/defn ^:private replace-char-gen :- (s/pred fn?)
        ;; Will replace dashes with underscores or underscores with dashes for the keywords in a map
        ;; Ignores String values in a map (both keys and values)
        ([from :- Character, to :- Character] (replace-char-gen from to #{}))
        ([from :- Character, to :- Character, exceptions :- #{s/Keyword}]
         #(if (keyword? %) (replace-char % from to exceptions) %)))


(defn dash->underscore [json-doc]
  (walk/postwalk (replace-char-gen \- \_) json-doc))


(defn underscore->dash [json-doc]
  (walk/postwalk (replace-char-gen \_ \- underscore->dash-exceptions) json-doc))


(defn write-json [data]
  (-> data
      dash->underscore
      generate-string))


(defn read-json [data]
  (-> data
      underscore->dash
      (read-json true)))


(s/defn read-edn [v :- s/Str]
        (if (string? v) (edn/read-string {:readers *data-readers*} v) v))


(s/defn write-edn :- s/Str
        [v :- (s/pred coll?)]
        (pr-str v))


(def acc (json/read-str (json/write-str account_2)))
(pprint (conj accounts (json/read-str (json/write-str account_2))))
(pprint account_2)

(pprint (vals acc))

(pprint (get  (get acc "account") "active-card"))





(defn authorizer! [account operation])

(def account "{ 'account' :{'active-card':true,'available-limit':100},'violations':[]}")
(def account_2 {:account {:active-card true, :available-limit 100}, :violations []})


(defn parse-str-to-json
  [account]
  (pprint (json/write-str account_2)))


(defn write-file!
  [account-json]
  (pprint account-json)
  (with-open [wrtr (writer "operations.json" :append true)]
    (.write wrtr (parse-str-to-json account-json))))


(defn read-file
  []
  (json/read-str (slurp "operations.json")))


(defn account-exist?
  []
  false)


(defn string-keys-to-symbols [map]
  (reduce #(assoc %1 (-> (key %2) keyword) (val %2)) {} map))


(defn violations-write [account]
  (pprint  "account-already-initialized")
  (let [account-violations (assoc (string-keys-to-symbols (read-file)) :violations ["account-already-initialized"])]
    (write-file! account-violations))
  )


(defn create-account
  [account]
  (if (account-exist?)
    (write-file! account)
    (violations-write account)))


(create-account account_2)
;(pprint (string-keys-to-symbols (json/read-str (slurp "operations.json"))))]

