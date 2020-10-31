(ns api-financeira.ballance-api-test
    (:require   [api-financeira.aux-functions :refer :all]
                [cheshire.core :as json]
                [midje.sweet :refer :all]
                [clj-http.client :as http]))

(against-background [(before :facts (start-server))
                     (after  :facts (stop-server))]
  (fact "The initial ballance is zero" :acceptance
    (json/parse-string (content-of-request "/ballance") true) => {:saldo 0})


  (fact "The ballance will be 10 when the only transaction is a revenue of 10" :acceptance
    (http/post (url-for "/transaction")
       { :body (json/generate-string { :valor 10 :tipo "receita" })})

    (json/parse-string (content-of-request "/ballance") true) => {:saldo 10}))



