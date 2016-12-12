(ns fplib.bll.services.book-service
  (:require [fplib.bll.protocols.common-service-protocol :as common-protocol]
            [fplib.bll.protocols.book-service-protocol :as book-protocol]
            [fplib.bll.services.log-service :as log]))

(deftype book-service [book-dao]

    common-protocol/common-service-protocol

    (add-item [this options]
          (log/logger-pattern (str "Admin") (str "Add new book"))
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
       (log/logger-pattern (str "Request for the book (" (:searchstring (:params option)) ")") (str "Output of books found"))
       (let [response (.get-books-by-request book-dao option)]
       response))
)
