(ns api-example.core
  (:require  [ring.adapter.jetty :refer [run-jetty]]
             [reitit.ring :as ring]
             [ring.middleware.json :refer [wrap-json-body wrap-json-response]]
             [ring.util.response :refer [response]]))


(def todos (atom [{:id 1 :name "Learning Clojure" :status "WIP"}
                  {:id 2 :name "Learning Python" :status "WIP"}
                  {:id 3 :name "Learning English" :status "WIP"}]))

(defn get-todos [request]
  (response @todos))


(defn get-todo [{{id :id} :path-params}]
  (response (first (filter #(= (:id %) (Integer. id)) @todos))))


(defn incresa-id [todo]
  (let [new-items (select-keys todo [:name :status])
       new-id {:id (+ 1 (:id todo))}]
    (merge new-id new-items)))


(defn add-todo
  [{todo :body}]
  (if (not-empty (filter #(= (:id %) (:id todo)) @todos))
    (do (let [new-todo (incresa-id todo)]
          (swap! todos conj new-todo)
      (response new-todo)))
    (do (swap! todos conj todo)
        (response todo))))



(defn remove-todo
  [{todo :body}]
  (response @todos))

(def router
  (ring/ring-handler
    (ring/router
      [["/" {:get get-todos :post add-todo}]
       ["/:id" {:get get-todo :delete remove-todo}]])))

(defn -main
  [& args]
  (run-jetty (-> #'router (wrap-json-body {:keywords? true}) (wrap-json-response)) {:port 8000})
  (println  "Web server todo list runing at port: 8080"))
