(ns api-financeira.handler
  (:require [cheshire.core :as json]
            [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.middleware.defaults :refer [wrap-defaults api-defaults]]
            [ring.middleware.json :refer [wrap-json-body]]
            [api-financeira.db :as db]))

(defn- content-as-json 
  [content]
  {:headers {"Content-Type" "application/json; charset=utf-8"}
   :body content})

(defroutes app-routes
  (GET "/" [] "Hello World")
  (GET "/ballance" [] (content-as-json (json/generate-string { :saldo 0 })))
  (POST "/transaction" my-request (db/register (:body my-request)))
  (route/not-found "The resource does not exist!"))

(def app (->
           (wrap-defaults app-routes api-defaults)
           (wrap-json-body)))
