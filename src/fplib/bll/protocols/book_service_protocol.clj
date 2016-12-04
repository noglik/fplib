(ns fplib.bll.protocols.book-service-protocol)

(defprotocol book-service-protocol
  (get-new-books [this])
  (get-book-by-id [this id])
  (get-books-by-request [this option])
)
