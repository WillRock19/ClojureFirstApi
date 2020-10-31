(ns api-financeira.handler
  (:require [cheshire.core :as json]
            [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.middleware.defaults :refer [wrap-defaults api-defaults]]
            [ring.middleware.json :refer [wrap-json-body]]
            [api-financeira.db :as db]))

(defn ^:private content-as-json
  ([content] (content-as-json content nil))
  ([content status]
     {:status  (or status 200)
      :headers {"Content-Type" "application/json; charset=utf-8"}
      :body    (json/generate-string content)}))

(defroutes app-routes
  (GET "/" [] "Hello World")
  (GET "/ballance" [] (content-as-json {:saldo (db/balance) }))
  (POST "/transaction" my-request (->
                                    (db/register (:body my-request))
                                    (content-as-json 201)))
  (route/not-found "The resource does not exist!"))

(def app (->
           (wrap-defaults app-routes api-defaults)
           (wrap-json-body { :keywords? true :bigdecimals? true })))
