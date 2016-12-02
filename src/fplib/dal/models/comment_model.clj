(ns fplib.dal.models.comment-model)

(defrecord comment-record
  [id
   comment
   author_id
   book_id])