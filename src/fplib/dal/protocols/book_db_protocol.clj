(ns fplib.dal.protocols.book-db-protocol)

(defprotocol book-db-protocol
	(get-new-books [this])
  (get-book-by-id [this id]))
