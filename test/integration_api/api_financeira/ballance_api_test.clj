(ns api-financeira.ballance-api-test
    (:require   [api-financeira.aux-functions :refer :all]
                [midje.sweet :refer :all]))

(against-background [(before :facts (start-server server-port))
                     (after  :facts (stop-server))]
  (fact "The initial ballance is zero" :acceptance
    (content-of-request "/ballance") => "0"))
