(ns fplib.bll.protocols.comment-service-protocol)

(defprotocol comment-service-protocol
    	(add-new-comment [this option])
  	(get-comments-by-idbook [this id]))
