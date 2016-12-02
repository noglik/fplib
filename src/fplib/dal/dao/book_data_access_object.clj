(ns fplib.dal.dao.book-data-access-object
  (:require [fplib.dal.protocols.common-db-protocol :as common-protocol]
            [fplib.dal.protocols.book-db-protocol :as book-protocol]
            [fplib.dal.models.book-model :as book-model]
            [fplib.dal.db :as db]
            [clojure.java.jdbc :as jdbc]))

(deftype book-data-access-object [db-map]

  common-protocol/common-db-protocol

  (add-item [this options]
     (jdbc/insert! db-map
                 :book
                 {
                   :name (:name options)
                   :author (:author options)
                   :year_book (:year options)
                   :description (:description options)
                   :link_download (:link options)
                   :genre (:genre options)}))

  (get-all-items [this]
     (into [] (jdbc/query db-map
                          ["SELECT *
                            FROM `book`"])))


  book-protocol/book-db-protocol

  (get-new-books [this]
      (into [] (jdbc/query db-map
                          ["SELECT *
                            FROM `book`
                            WHERE row_count() <= 10"])))

  (get-book-by-id [this id]
      (first (jdbc/query db/db-map ["SELECT b.id, b.name, b.author,
                                     b.book_year, b.description,
                                     b.link_download, b.genre
                                     FROM book as b
                                     WHERE id = ?" id]
                                     :row-fn #(book-model/->book-record
                                               (:id %1)
                                               (:name %1)
                                               (:author %1)
                                               (:year %1)
                                               (:description %1)
                                               (:link %1)
                                               (:genre %1)))))
)
