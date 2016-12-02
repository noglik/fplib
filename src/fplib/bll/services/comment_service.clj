(ns fplib.bll.services.comment-service
	(:require
		[fplib.bll.protocols.comment-service-protocol :as comment-protocol]
		[fplib.dal.dao.comment-data-access-object :as comment-model]))

(deftype comment-service [comment-model]

	comment-protocol/comment-service-protocol

	(add-new-comment
		[this options]
		(.add-new-comment comment-model options))

  (get-comments-by-idbook [this id]
  (def response (.get-comments-by-idbook comment-model id))
       (println "\n----------------COMMENTS-------------\n" response)
    response)
)
