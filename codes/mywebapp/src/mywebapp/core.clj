(ns mywebapp.core
  (:require [compojure.core :refer [context defroutes GET POST]]
            [ring.adapter.jetty :as jetty]
            [ring.middleware.params :refer [wrap-params]]))

(defroutes myapproutes
  (context "/admin" []
      (GET "/login" [] (str "Logging in id" id))
      (GET "/logout" [] "Logging out"))
  (GET "/" [] "Hello from Compojure")
  (POST "/:name" [name] (fn [req] (str "Hello, "(-> req :route-params :name))))
  (GET "/hex-id/:id" :id #"[a-fA-F0-9]+"] [id] (str "ID: " id))
  (GET "/route1" [] "Hello from route1")
  (GET "/route2" [] "Hello form route2")
  (route/resources "/static"))

(myapproutes {:uri "/" :request-method :get})
(myapproutes {:uri "/admin/1/login" :request-method :get})


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
