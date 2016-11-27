(ns fplib.bll.services.user-service
	(:require 
		[fplib.bll.protocols.user-service-protocol :as user-protocol]
		[fplib.bll.protocols.common-service-protocol :as common-protocol]
		[fplib.dal.dao.user-data-access-object :as user-model]))

(deftype user-service [user-model]
	
	common-protocol/common-service-protocol

	(add-item
		[this options]
		(.add-item user-model options)))