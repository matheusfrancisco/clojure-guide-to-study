(ns components-injection.components.server
  (:require  [ring.adapter.jetty :refer [run-jetty]]
             [reitit.ring :as ring]
             [com.stuartsierra.component :as component]
             [components-injection.components.routers :as r]
             [ring.middleware.json :refer [wrap-json-body wrap-json-response]]))



(defn create-server [port]
  (run-jetty (-> #'components-injection.components.routers/router
                 (wrap-json-body {:keywords? true})
                 (wrap-json-response)) {:port port}))

(defn stop [server]
  ((:close server)))


(defrecord Server [port]
  component/Lifecycle

  (start [component]
    (let [server (create-server port)]
      (assoc component :web-server server)))

  (stop [component]
    (stop (:web-server component))
    (assoc component :web-server nil)))


(defn new-server [port]
  (map->Server {:port port}))

