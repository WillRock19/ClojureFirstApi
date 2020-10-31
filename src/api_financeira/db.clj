(ns api-financeira.db)

(def my-registers (atom []))

(defn transactions []
  @my-registers)

(defn register [transaction]
  (let [updated-database (swap! my-registers conj transaction)]
    (merge transaction {:id (count updated-database)})))

(defn clean-registers []
  (reset! my-registers []))