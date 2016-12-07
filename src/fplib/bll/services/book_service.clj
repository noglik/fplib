(ns fplib.bll.services.book-service
  (:require [fplib.bll.protocols.common-service-protocol :as common-protocol]
            [fplib.bll.protocols.book-service-protocol :as book-protocol]))

(deftype book-service [book-dao]

    common-protocol/common-service-protocol

    (add-item [this options]
          (.add-item book-dao options))

    (get-all-items[this]
       (let [response (.get-all-items book-dao)]
       ;(println "\n----------------BOOKS-------------\n" response)
        response))

    book-protocol/book-service-protocol

    (get-new-books[this]
       (let [response (.get-new-books book-dao)]
       response))

    (get-book-by-id [this id]
       (let [response (.get-book-by-id book-dao id)]
       response))

    (get-books-by-request [this option]
       (let [response (.get-books-by-request book-dao option)]
       response))
)
