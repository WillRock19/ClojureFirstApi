(ns api-financeira.handler
  (:require [cheshire.core :as json]
            [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.middleware.defaults :refer [wrap-defaults api-defaults]]))

(defn- content-as-json 
  [content]
  {:headers {"Content-Type" "application/json; charset=utf-8"}
   :body content})

(defroutes app-routes
  (GET "/" [] "Hello World")
  (GET "/ballance" [] (content-as-json (json/generate-string { :saldo 0 })))
  (route/not-found "The resource does not exist!"))

(def app
  (wrap-defaults app-routes api-defaults))
