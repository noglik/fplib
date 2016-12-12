(ns fplib.bll.services.log-service
  (:import (java.util Date))
  (:import (java.io FileWriter)))

(def logger (agent (FileWriter. "logger.txt" true)))

(defn- logMessage [out, message]
  (.write out (str message "\n"))
  (.flush out)
  out)

(defn logger-pattern [info message]
  (let [ date (Date.) returnMessage (str info " (" date "): " message "\n")]
    (send logger logMessage returnMessage)))
