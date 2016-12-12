(ns fplib.bll.services.user-service
	(:require
		[fplib.bll.protocols.user-service-protocol :as user-protocol]
		[fplib.bll.protocols.common-service-protocol :as common-protocol]
		[fplib.dal.dao.user-data-access-object :as user-model]
    [fplib.validation.validation-logic :refer :all]
    [fplib.bll.services.log-service :as log]))

(deftype user-service [user-model]

	common-protocol/common-service-protocol

	(add-item
		[this options]
		(if (and (is-correct-email? (:mail options)) (is-same-pass? (:password options) (:password1 options)))
	      (do
			    (.add-item user-model options)
          (log/logger-pattern (:login options) (str "User has logged in system")))
	      (println options)))

	user-protocol/user-service-protocol

	(sign-in
		[this login password]
		(.sign-in user-model login password)
    (log/logger-pattern login (str "User SIGN IN in system")))

	(get-user-by-login [this login]
		(let [user (.get-user-by-login user-model login)]
		(println user)
		user))
)
