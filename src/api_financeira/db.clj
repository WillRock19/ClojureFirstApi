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

(defn map-numbers-with-sign
      [transaction]
      (if (despesa? transaction)
        (unchecked-negate (:valor transaction))
        (:valor transaction)))

(defn balance []
  (reduce + (map map-numbers-with-sign (transactions))))