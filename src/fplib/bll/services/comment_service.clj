(ns fplib.bll.services.comment-service
	(:require 
		[fplib.bll.protocols.comment-service-protocol :as comment-protocol]
		[fplib.bll.protocols.common-service-protocol :as common-protocol]
		[fplib.dal.dao.comment-data-access-object :as comment-model]))

(deftype comment-service [comment-model]
	
	common-protocol/common-service-protocol

	(add-item
		[this options]
		(.add-item comment-model options)))