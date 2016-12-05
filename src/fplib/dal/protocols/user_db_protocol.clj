(ns fplib.dal.protocols.user-db-protocol)

(defprotocol user-db-protocol
	(registration [this login password salt mail])
	(sign-in [this login password])
	(get-user-by-login [this login]))