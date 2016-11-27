(ns fplib.core
	(:use compojure.core)
	(:require 
					[compojure.route :as route]
					[compojure.handler :as handler]
					[ring.middleware.json :as middleware]
					[ring.util.response :as response]
					[fplib.views.view :as view]
					[fplib.dal.db :as db]
					[fplib.dal.dao.user-data-access-object :as user-dao]
					[fplib.bll.services.user-service :as user-service-d]))

;Users
(def usr-dao (user-dao/->user-data-access-object db/db-map))
(def user-service (user-service-d/->user-service usr-dao))

(defn add-user
	[request]
	(.add-item user-service (:params request)))

(defroutes app-routes
					 (GET "/" [] (view/render-home-page))
					 (POST "/registration" request (add-user request)))

(def engine
  (-> (handler/site app-routes) (middleware/wrap-json-body {:keywords? true})))
