(ns api-financeira.ballance-api-test
    (:require   [api-financeira.handler :refer [app]]
                [midje.sweet :refer :all]
                [ring.adapter.jetty :refer [run-jetty]]
                [clj-http.client :as http]))

(def server (atom nil))
(def server-port 3001)

(defn start-server [port]
  (swap! server (fn [_] (run-jetty app {:port port :join? false}))))

(defn stop-server []
  (.stop @server))

(against-background [(before :facts (start-server server-port))
                     (after  :facts (stop-server))]
  (fact "O saldo inicial é zero" :acceptance
    (let [request-url (format "http://localhost:%s/ballance" server-port)]
      (:body (http/get request-url)) => "0")))
