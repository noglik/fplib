(ns fplib.dal.protocols.book-db-protocol)

(defprotocol book-db-protocol
	(get-new-books [this])
  (get-book-by-id [this id])
  (get-books-by-request [this option]))
