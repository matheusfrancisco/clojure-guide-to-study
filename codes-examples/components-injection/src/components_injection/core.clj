(ns components-injection.core
  (:require  [components-injection.components.server :as server]
             [com.stuartsierra.component :as component]))


(defn system-map [config]
  (component/system-map
    :http (server/new-server (:port config))))


(defn start-all []
  (def system (component/start (system-map {:port 8000}))))


(defn stop-all []
  (component/stop system))


(defn -main
  [& args]
  (start-all))
