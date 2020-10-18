(ns api-financeira.ballance-api-test
    (:require   [api-financeira.handler :refer [app]]
                [midje.sweet :refer :all]
                [ring.adapter.jetty :refer [run-jetty]]
                [clj-http.client :as http]))

(def server (atom nil))

(defn start-server [port]
  (swap! server (fn [_] (run-jetty app {:port port :join? false}))))

(defn stop-server []
  (.stop @server))

(fact "O saldo inicial é zero" :acceptance
  (let [server-port 3001
        request-url (format "http://localhost:%s/ballance" server-port)]

    (start-server 3001)

    ;; Make an HTTP request and verify that the return body has the value 0
    (:body (http/get request-url)) => "0"

    (stop-server)))