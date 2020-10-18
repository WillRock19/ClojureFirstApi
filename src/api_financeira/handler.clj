(ns api-financeira.handler
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]))

(defroutes app-routes
  (GET "/" [] "Hello World")
  (GET "/ballance" [] "0")
  (route/not-found "The resource does not exist!"))

(def app
  (wrap-defaults app-routes site-defaults))
