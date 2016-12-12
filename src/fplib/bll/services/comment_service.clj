(ns fplib.bll.services.comment-service
	(:require
		[fplib.bll.protocols.comment-service-protocol :as comment-protocol]
		[fplib.dal.dao.comment-data-access-object :as comment-model]
    [fplib.bll.services.log-service :as log]))

(deftype comment-service [comment-model]

	comment-protocol/comment-service-protocol

	(add-new-comment
		[this options]
		(.add-new-comment comment-model options)
    (log/logger-pattern (str "Add new comment") (:comment options)))

  (get-comments-by-idbook [this id]
  (let [response (.get-comments-by-idbook comment-model id)]
       (println "\n----------------COMMENTS-------------\n" response)
    		response))
)
