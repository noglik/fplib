(ns fplib.bll.services.user-service
	(:require
		[fplib.bll.protocols.user-service-protocol :as user-protocol]
		[fplib.bll.protocols.common-service-protocol :as common-protocol]
		[fplib.dal.dao.user-data-access-object :as user-model]
    [fplib.validation.validation-logic :refer :all]
		[fplib.bll.services.log-service :as log]
		[fplib.bll.statistics :as statistics]
		[buddy.hashers :as hashers]))

;password hashing
(defn crypt-pass
  [password]
	(let [salt (str (rand-int 123456789))
		hash-password (hashers/encrypt password {:alg :pbkdf2+sha256 :salt salt})] {:hash hash-password :salt salt}))

(defn crypt-with-salt
	[password salt]
	(hashers/encrypt password {:alg :pbkdf2+sha256 :salt (str salt)}))

(deftype user-service [user-model]

	common-protocol/common-service-protocol

	(add-item
		[this options]
		(if (and (is-correct-email? (:mail options)) (is-same-pass? (:password options) (:password1 options)))
	      (do
					(println "Problem with mail or password")
					(log/logger-pattern (:login options) (str "User has logged in system"))
					(statistics/registration-of-user)
					(let [hash (crypt-pass (:password options))]
			    (.add-item user-model (assoc options :password (:hash hash) :salt (:salt hash)))))
	      (println "bll " options))
				)

	user-protocol/user-service-protocol

	(sign-in
		[this login password]
		(log/logger-pattern login (str "User SIGN IN in system"))
		(let [user (.get-user-by-login user-model login)]
			(if (is-same-pass? (crypt-with-salt password (:salt user)) (:password user) )
				(do
					(println "WHOOOOHOOO")
					(.sign-in user-model login (:password user))
					))))

	(get-user-by-login [this login]
		(let [user (.get-user-by-login user-model login)]
		(println user)
		user))
)
