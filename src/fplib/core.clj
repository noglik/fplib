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
					[fplib.bll.services.user-service :as user-service-d]
					[fplib.dal.dao.book-data-access-object :as book-dao]
					[fplib.bll.services.book-service :as book-service-d]))


;Users
(def usr-dao (user-dao/->user-data-access-object db/db-map))
(def user-service (user-service-d/->user-service usr-dao))

;books
(def bk-dao (book-dao/->book-data-access-object db/db-map))					
(def book-service (book-service-d/->book-service bk-dao))

(defn add-user
	[request]
	(.add-item user-service (:params request)))

(defn add-book
  [request]
  (.add-item book-service (:params request)))


;Определяем роуты приложения
(defroutes app-routes
					 (GET "/" [] (view/home))
					 (GET "/registration" [] (view/registration))
					 (POST "/registration" request (add-user request))
					 (POST "/book/add" request (add-book request))
					 (GET "/book" [] (view/book))
					 (GET "/auth" [] (view/authorization))
					 (route/resources "/")
		       (route/not-found "Page not found")) 

(def engine
  (-> (handler/site app-routes) (middleware/wrap-json-body {:keywords? true})))
