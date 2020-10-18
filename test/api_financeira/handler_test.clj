(ns api-financeira.handler-test
  (:require [midje.sweet :refer :all]
            [ring.mock.request :as mock]
            [api-financeira.handler :refer :all]))


(facts "On a call to the root route"
  (let [response (app (mock/request :get "/"))]
    (fact "the response status should be 200"
      (:status response) => 200)
      
    (fact "the return body should be 'Hello World'"
      (:body response) => "Hello World")))

(facts "On a call to a invalid route"
  (let [response (app (mock/request :get "/invalid"))]
    (fact "the response status should be 404"
      (:status response) => 404)
      
    (fact "the return body should be 'The resource does not exist'"
      (:body response) => "The resource does not exist!")))