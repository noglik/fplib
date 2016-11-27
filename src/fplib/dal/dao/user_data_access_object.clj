(ns fplib.dal.dao.user-data-access-object
	(:require 
		[fplib.dal.protocols.user-db-protocol :as user-protocol]
		[fplib.dal.protocols.common-db-protocol :as common-protocol]
		[fplib.dal.models.user-model :as user-record]
		[clojure.java.jdbc :as jdbc]))

(deftype user-data-access-object [db-map]

	common-protocol/common-db-protocol

	(add-item
		[this options]
		(jdbc/insert! db-map
									:users
									{:login			(:login options)
									 :password	(:password options)
									 :salt      123
									 :mail			(:mail options)
									 :is_admin  false})))
