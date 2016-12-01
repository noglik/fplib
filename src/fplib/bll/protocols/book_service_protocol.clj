(ns fplib.bll.protocols.book-service-protocol)

(defprotocol book-service-protocol
  (get-new-books [this])
  (get-book-by-id [this id])
)
