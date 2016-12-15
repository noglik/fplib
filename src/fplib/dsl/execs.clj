(ns fplib.dsl.execs
 (:use  [fplib.dsl.emit]
        [fplib.dsl.renders]
        [fplib.dsl.struct-func])
  (:require [fplib.dal.db :as db]
            [clojure.java.jdbc :as jdbc]))


(defn- to-sql-params
  [relation]
  (let [{s :sql p :args} (as-sql relation)]
    (vec (cons s p))))


(defn fetch
  ([relation] (jdbc/query db/db-map (to-sql-params relation)))
  ([relation func] (jdbc/query db/db-map
                               (to-sql-params relation)
                               :row-fn func)))

;(cons s p), s-first element, p - it's the rest (..)
;vec (..) create a new vector [..]
