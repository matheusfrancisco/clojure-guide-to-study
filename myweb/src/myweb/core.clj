(ns myweb.core
  (:require [net.cgrand.enlive-html :as enlive])
            [ring.adapter.jetyy :as jetty])


;(deftemplate hello-tpl-1 "hello.html"
;  [name]
;  [:h1] (enlive/html-content (str "Hello", name)))


;(deftemplate hello-tpl "hello.html"
; [name]
;  [:h1] (enlive/do->
;          (enlive/wrap :main)
;          (enlive/html-content "Hello"))
;)


(enlive/defsnippet table-row-tpl "hello.html" [:table :tbody [:tr enlive/first-of-type]]
    [rowdata]
    [[:td (enlive/nth-of-type 1)]] (enlive/html-content (:id rowdata))
    [[:td (enlive/nth-of-type 2)]] (enlive/html-content (:name rowdata))
    [[:td (enlive/nth-of-type 3)]] (enlive/html-content (:date rowdata)))


(enlive/deftemplate table-tpl "hello.html"
  [rows]
  [:h1] (enlive/html-content "Custormes")
  [:table :tbody] (enlive/content (map table-row-tpl rows)))


(defn myapp [req]
  {:body (table-tpl [{:id 1 :name "Jao" :date "Today"}
                     {:id 2 :name "Janie" :date "Yesterday"}
                     {:id 3 :name "Mat" :date "Today"}])
   :status 200
   :headers {"Content-Type" "text/html"}}
  )

(defn -main []
  (jetty/run-jetty myapp {:port 3000}))
