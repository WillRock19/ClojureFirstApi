(ns api-financeira.aux-functions
  (:require   [api-financeira.handler :refer [app]]
              [ring.adapter.jetty :refer [run-jetty]]
              [clj-http.client :as http]))

(def ^:private server (atom nil))
(def ^:private server-port 3001)

(defn ^:private url-for [route]
    (format "http://localhost:%s/%s" server-port route))
  
(def ^:private request-for (comp http/get url-for))

(defn start-server 
([] 
  (start-server server-port))
([port]
  (swap! server (fn [_] (run-jetty app {:port port :join? false})))))

(defn stop-server []
  (.stop @server))

(defn content-of-request [route]
  (:body (request-for route)))