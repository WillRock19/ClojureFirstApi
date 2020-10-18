(ns api-financeira.handler
  (:require [cheshire.core :as json]
            [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]))

(defroutes app-routes
  (GET "/" [] "Hello World")
  (GET "/ballance" [] (json/generate-string { :saldo 0 }))
  (route/not-found "The resource does not exist!"))

(def app
  (wrap-defaults app-routes site-defaults))
