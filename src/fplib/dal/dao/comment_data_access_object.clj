(ns fplib.dal.dao.comment-data-access-object
	(:require
		[fplib.dal.protocols.comment-db-protocol :as comment-protocol]
		[fplib.dal.models.comment-model :as comment-model]
    [fplib.dal.db :as db]
		[clojure.java.jdbc :as jdbc]))

(deftype comment-data-access-object [db-map]

	comment-protocol/comment-db-protocol

	(add-new-comment
		[this options]
		(jdbc/insert! db-map
									:comments
									{:comment			(:comment options)
									 :author_id 		(:author_id options)
									 :book_id 			(:book_id options)}))

  (get-comments-by-idbook [this id]
         (into [] (jdbc/query db-map
                              ["SELECT c.id, u.login, c.comment
                                FROM comments as c
                                JOIN users as u ON c.id = u.id
                                WHERE book_id = ?" id]
                              :row-fn #(comment-model/->comment-record
                                         (:id %1)
                                         (:comment %1)
                                         (:login %1)))))


)
