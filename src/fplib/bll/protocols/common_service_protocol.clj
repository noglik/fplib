(ns fplib.bll.protocols.common-service-protocol)

(defprotocol common-service-protocol
	(get-all-irems [this])
	(get-item [this options])
	(add-item [this options]))