(ns fplib.dal.protocols.common-db-protocol)

(defprotocol common-db-protocol
	(get-all-items [this])
	(get-item [this options])
	(add-item [this options]))