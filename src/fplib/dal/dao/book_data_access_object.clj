(ns fplib.dal.dao.book-data-access-object
  (:require [fplib.dal.protocols.common-db-protocol :as common-protocol] 
            [fplib.dal.models.book-model :as book-model] 
            [fplib.dal.db :as db] 
            [clojure.java.jdbc :as jdbc])) 

(deftype book-data-access-object [db-map]

  common-protocol/common-db-protocol

  (add-item [this options]
     (jdbc/insert! db/db-map
	               :book
	               {
				   :name (:name options) 
                   :book_year (:year options) 
                   :short_description (:description options) 
                   :link_download (:link options)
				   :genre (:genre options)})) 				   				
)
