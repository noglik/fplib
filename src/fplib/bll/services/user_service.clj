(ns fplib.bll.services.user-service
	(:require 
		[fplib.bll.protocols.user-service-protocol :as user-protocol]
		[fplib.bll.protocols.common-service-protocol :as common-protocol]
		[fplib.dal.dao.user-data-access-object :as user-model]))

(deftype user-service [user-model]
	
	common-protocol/common-service-protocol

	(add-item
		[this options]
		(.add-item user-model options))

	user-protocol/user-service-protocol

	(sign-in
		[this login password]
		(.sign-in user-model login password))

	(get-user-by-login [this login]
		(def user (.get-user-by-login user-model login))
		(println user)
		user))