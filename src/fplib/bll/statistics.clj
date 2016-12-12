(ns fplib.bll.statistics
    (:require [fplib.bll.services.log-service :as log]))

(def count-of-registration (atom 0))
(def count-to-log 5)

(defn registration-of-user []
  (swap! count-of-registration inc)
  (if (== (mod @count-of-registration count-to-log) 0)
  (log/logger-pattern (str "Count of registration") (str " = " @count-of-registration))))
