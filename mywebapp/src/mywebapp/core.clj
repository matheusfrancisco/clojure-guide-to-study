(ns mywebapp.core
  (:require [ring.adapter.jetty :as jetty]
            [ring.middleware.params :refer [wrap-params]]))

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
  (jetty/run-jetty handler {:port 3000}))
