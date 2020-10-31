(ns api-financeira.db-test
    (:require [midje.sweet :refer :all]
              [api-financeira.db :refer :all]))

(defn ^:private clean-atom []
  (reset! my-registers []))

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
      (count (transactions)) => 0))

  (facts "when calling balance function"
    (fact "should return zero if the values of receita and despesa are the same"
      (register {:valor 10, :tipo "receita"})
      (register {:valor 10, :tipo "despesa"})
      (balance) => 0)

    (fact "should return the sum  of every valor if all register are 'receita'"
      (register {:valor 10, :tipo "receita"})
      (register {:valor 30, :tipo "receita"})
      (register {:valor 55, :tipo "receita"})
      (balance) => 95)

    (fact "should return a positive number if all registers are 'receita'"
      (register {:valor 10, :tipo "receita"})
      (register {:valor 30, :tipo "receita"})
      (register {:valor 55, :tipo "receita"})
      (pos? (balance)) => true)

    (fact "should return the sum  of every valor if all registers are 'despesa'"
      (register {:valor 10, :tipo "despesa"})
      (register {:valor 30, :tipo "despesa"})
      (register {:valor 55, :tipo "despesa"})
      (balance) => -95)

    (fact "should return a negative number if all registers are 'despesa'"
      (register {:valor 10, :tipo "despesa"})
      (register {:valor 30, :tipo "despesa"})
      (register {:valor 55, :tipo "despesa"})
      (neg? (balance)) => true)

    (fact "should return the sum of 'receitas' minus the sum of 'despesas'"
      (register {:valor 500, :tipo "despesa"})
      (register {:valor 678, :tipo "receita"})
      (register {:valor 430, :tipo "despesa"})
      (register {:valor 300, :tipo "receita"})
      (balance) => 48)))