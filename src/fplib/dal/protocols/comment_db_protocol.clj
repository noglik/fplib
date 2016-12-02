(ns fplib.dal.protocols.comment-db-protocol)

(defprotocol comment-db-protocol
  	(add-new-comment [this option])
  	(get-comments-by-idbook [this id]))
