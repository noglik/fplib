(ns fplib.dal.dao.book-data-access-object
  (:use [fplib.dsl.execs]
        [fplib.dsl.struct-func]
        [fplib.dsl.renders])

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
                   :year (:year options)
                   :description (:description options)
                   :link (:link options)
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

 ; (get-book-by-id [this id]
 ;     (first (jdbc/query db-map ["SELECT b.id, b.name, b.author,
 ;                                    b.year, b.description,
 ;                                    b.link, b.genre
 ;                                    FROM book as b
 ;                                    WHERE id = ?" id]
 ;                                    :row-fn #(book-model/->book-record
 ;                                             (:id %1)
 ;                                              (:name %1)
 ;                                              (:author %1)
 ;                                              (:year %1)
 ;                                              (:description %1)
 ;                                              (:link %1)
 ;                                              (:genre %1)))))

  (get-book-by-id [this id]
      (first (fetch (select (fields {:id :id
                                     :name :name
                                     :author :author
                                     :year :year
                                     :description :description
                                     :link :link
                                     :genre :genre})
                            (from :book)
                            (where (== :id id)))
                        #(book-model/->book-record
                                               (:id %1)
                                               (:name %1)
                                               (:author %1)
                                               (:year %1)
                                               (:description %1)
                                               (:link %1)
                                               (:genre %1)))))


  (get-books-by-request [this option]
                         (into [] (jdbc/query db-map
                          ["SELECT id, name, author,
                                     year, description,
                                     link, genre
                           FROM book
                           WHERE match(name, description, genre, author)
                           AGAINST (? IN boolean mode)" (str (:searchstring (:params option)) '*')]
                           :row-fn #(book-model/->book-record
                                               (:id %1)
                                               (:name %1)
                                               (:author %1)
                                               (:year %1)
                                               (:description %1)
                                               (:link %1)
                                               (:genre %1)))))
)
