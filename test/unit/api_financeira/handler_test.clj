(ns api-financeira.handler-test
  (:require [cheshire.core :as json]
            [midje.sweet :refer :all]
            [ring.mock.request :as mock]
            [api-financeira.handler :refer :all]
            [api-financeira.db :as db]))

(facts "On a call to the root route"
  (let [response (app (mock/request :get "/"))]
    (fact "the response status should be 200"
      (:status response) => 200)
      
    (fact "the return body should be 'Hello World'"
      (:body response) => "Hello World")))

(facts "On a call to an invalid route"
  (let [response (app (mock/request :get "/invalid"))]
    (fact "the response status should be 404"
      (:status response) => 404)
      
    (fact "the return body should be 'The resource does not exist'"
      (:body response) => "The resource does not exist!")))

(facts "On a call to the ballance route"
  (against-background (json/generate-string {:saldo 0}) => "{\"saldo\": 0}")

  (let [response (app (mock/request :get "/ballance"))]
    (fact "the response status should be 200"
      (:status response) => 200)
    
    (fact "the return body should be '0'"
      (:body response) => "{\"saldo\": 0}")
      
    (fact "the response's format should be 'Content-Type'"
      (get-in response [:headers "Content-Type"]) => "application/json; charset=utf-8")))

(facts "On a call to the transaction route"
  (against-background (db/register { :valor 10 :tipo "receita" })
                         => { :id 1, :valor 10, :tipo "receita" })
  (let [response (app (-> (mock/request :post "/transaction")
                          (mock/json-body {:valor 10 :tipo "receita"})))]
    (fact "the response status should be 201"
      (:status response) => 201)

    (fact "the return body should be a JSON with the sent content and an id"
      (:body response) => "{\"id\": 1,\"valor\":10,\"tipo\":\"receita\"}")))