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
									 :is_admin  false}))

	user-protocol/user-db-protocol

	(sign-in 
		[this login password]
		(first (jdbc/query db-map
											["SELECT *
												FROM users
												WHERE login=? AND password=?" login password])))

	(get-user-by-login
		[this login]
		(first (jdbc/query db-map
											["SELECT id, login, password, mail
												FROM users
												WHERE login=?" login]
												:row-fn #(user-record/->user-record
																	(:id %1)
																	(:login %1)
																	(:password %1)
																	(:mail %1)
																	(:is_admin %1))))))
