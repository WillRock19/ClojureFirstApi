(ns api-financeira.db-test
    (:require [midje.sweet :refer :all]
              [api-financeira.db :refer :all]))

(defn ^:private clean-atom []
  (!reset my-registers []))

(facts "Transaction database"
  (against-background [(before :facts (clean-atom))])

  (fact "should start without transactions"
    (count (transactions)) => 0)

  (facts "when calling Register"
    (fact "should return same transaction received, adding the id property"
      (register {:valor 19, :tipo "receita"}) => {:id 1 :valor 19, :tipo "receita"}))

  (facts "when calling clean-registers"
    (fact "should clean all registers from database"
      (register {:valor 1, :tipo "test"})
      (register {:valor 1, :tipo "test"})
      (clean-registers)
      (count (transactions)) => 0)))