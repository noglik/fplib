(ns fplib.dal.protocols.user-db-protocol)

(defprotocol user-db-protocol
	(registration [this login password salt mail]))