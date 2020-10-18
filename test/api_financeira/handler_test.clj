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
  (fact "the response status should be 404"
    (let [response (app (mock/request :get "/invalid"))]
      (:status response) => 404)))