(ns api-financeira.db)

(defn ^:private despesa?
  [transacao]
  (= (:tipo transacao) "despesa"))

(def my-registers (atom []))

(defn transactions []
  @my-registers)

(defn register [transaction]
  (let [updated-database (swap! my-registers conj transaction)]
    (merge transaction {:id (count updated-database)})))

(defn clean-registers []
  (reset! my-registers []))

(defn calculate-ballance
  [transactions, accumulated-value]
  (if (empty? transactions)
    accumulated-value
    (let [first-transaction (first transactions)]
        (if (despesa? first-transaction)
            (calculate-ballance (rest transactions) (- accumulated-value (:valor first-transaction)))
            (calculate-ballance (rest transactions) (+ accumulated-value (:valor first-transaction)))))))

(defn balance []
  (calculate-ballance (transactions) 0))