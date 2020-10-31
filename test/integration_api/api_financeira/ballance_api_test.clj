(ns api-financeira.ballance-api-test
    (:require   [api-financeira.aux-functions :refer :all]
                [cheshire.core :as json]
                [midje.sweet :refer :all]
                [clj-http.client :as http]))

(defn convert-to-json-request [body]
  { :content-type :json
    :body (json/generate-string body) })

(against-background [(before :facts (start-server))
                     (after  :facts (stop-server))]
  (fact "The initial ballance is zero" :acceptance
    (json/parse-string (content-of-request "/balance") true) => {:saldo 0})


  (fact "The ballance will be 10 after I register a transaction with that value" :acceptance
    (http/post (url-for "/transaction")
               (convert-to-json-request {:valor 10 :tipo "receita"}))

    (json/parse-string (content-of-request "/balance") true) => {:saldo 10}))



