(ns api-financeira.ballance-api-test
    (:require   [api-financeira.aux-functions :refer :all]
                [cheshire.core :as json]
                [midje.sweet :refer :all]
                [clj-http.client :as http]
                [api-financeira.db :as db]))

(defn convert-to-json-request [body]
  { :content-type :json
    :body (json/generate-string body) })

(against-background [(before :facts [(start-server) (db/clean-registers)])
                     (after  :facts (stop-server))]
  (fact "The initial ballance is zero" :acceptance
    (json/parse-string (content-of-request "/balance") true) => {:saldo 0})


  (fact "The balance will be 10 after I register a transaction with that value" :acceptance
    (http/post (url-for "/transaction")
               (convert-to-json-request {:valor 10 :tipo "receita"}))

    (json/parse-string (content-of-request "/balance") true) => {:saldo 10})

    (fact "The balance will be 1000 after create two registers of type 'receita' with value -1000 and one of type 'despesa' with value 3000" :acceptance
        (http/post (url-for "/transaction")
                   (convert-to-json-request {:valor 1000 :tipo "receita"}))
        (http/post (url-for "/transaction")
                   (convert-to-json-request {:valor 1000 :tipo "receita"}))
        (http/post (url-for "/transaction")
                   (convert-to-json-request {:valor 3000 :tipo "despesa"}))

        (json/parse-string (content-of-request "/balance") true) => {:saldo -1000}))



