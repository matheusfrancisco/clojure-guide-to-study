(ns mywebapp.core
  (:require [compojure.core :refer [defroutes GET POST]]
            [ring.adapter.jetty :as jetty]
            [ring.middleware.params :refer [wrap-params]]))

(defroutes myapproutes
  (GET "/" [] "Hello from Compojure"))

(defn myapp [request]
  (str "Hello, " (get (:params request) "name")))

(defn string-response-middleware [handler]
  (fn [request]
    (let [response (handler request)]
      (if (instance? String response)
        {:body response
         :status 200
         :headers {"Content-Type" "text/html"}}
        response))))

(def handler
  (-> myapp
      string-response-middleware
      wrap-params))

(defn -main []
  (jetty/run-jetty myapproutes {:port 3000}))
