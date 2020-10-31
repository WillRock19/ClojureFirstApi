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

(defn calculate-balance
  [total-amount, transaction]
  (let [current-value (:valor transaction)]
    (if (despesa? transaction)
      (- total-amount current-value)
      (+ total-amount current-value))))

(defn balance []
  (reduce calculate-balance 0 (transactions)))