(ns fplib.dal.dao.comment-data-access-object
	(:require 
		[fplib.dal.protocols.comment-db-protocol :as comment-protocol]
		[fplib.dal.protocols.common-db-protocol :as common-protocol]
		[fplib.dal.models.comment-model :as comment-record]
		[clojure.java.jdbc :as jdbc]))

(deftype comment-data-access-object [db-map]

	common-protocol/common-db-protocol

	(add-item
		[this options]
		(jdbc/insert! db-map
									:comments
									{:comment			(:comment options)
									 :author_id 		(:author_id options)
									 :book_id 			(:book_id options)})))
