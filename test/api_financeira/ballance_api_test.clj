(ns api-financeira.ballance-api-test
    (:require   [api-financeira.handler :refer [app]]
                [midje.sweet :refer :all]
                [ring.adapter.jetty :refer [run-jetty]]))

(def server (atom nil))

(defn start-server [port]
  (swap! server (fn [_] (run-jetty app {:port port :join? false}))))

(defn stop-server []
  (.stop @server))

(fact "Inicia e para o servidor"
    (start-server 3000)
    (stop-server))