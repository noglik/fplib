(ns fplib.bll.services.user-service
	(:require
		[fplib.bll.protocols.user-service-protocol :as user-protocol]
		[fplib.bll.protocols.common-service-protocol :as common-protocol]
		[fplib.dal.dao.user-data-access-object :as user-model]
    [fplib.validation.validation-logic :refer :all]))

(deftype user-service [user-model]

	common-protocol/common-service-protocol

	(add-item
		[this options]
    (if (is-correct-email? (:mail options))
      (do
		    (.add-item user-model options))
      (println "Incorrect email")))
)
