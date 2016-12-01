(ns fplib.bll.services.book-service
  (:require [fplib.bll.protocols.common-service-protocol :as common-protocol]))

(deftype book-service [book-dao]

    common-protocol/common-service-protocol

    (add-item [this options]
       (.add-item book-dao options)
    )
)