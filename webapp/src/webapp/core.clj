(ns webapp.core
  (:require [selmer.parser :as tmpl]))

(tmpl/render "<hi> Hello, {{name}}</h1>" {:name "Frank"})
(tmpl/render-file "templates/hello.html" {:name "Fran"})


(defn myapp [{{name "name"} :params}]
  (respond-html (tmpl/render-file "hello.html" {:name "name"})))


(defn respond-html [s]
  {:body s
   :status 200
   :header {"Content-Type" "text/html"}})


(defn respond-html [template-path template-params]
  (respond-html (tmpl/render-file template-path template-params)))


(defn respond-html ( comp respond-html tmpl/render-file ))

(myapp {:params {"name" "Frank"}})
