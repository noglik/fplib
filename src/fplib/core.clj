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
					[fplib.bll.services.book-service :as book-service-d]
					[fplib.dal.dao.comment-data-access-object :as comment-dao]
					[fplib.bll.services.comment-service :as comment-service-d]
					))


;Users
(def usr-dao (user-dao/->user-data-access-object db/db-map))
(def user-service (user-service-d/->user-service usr-dao))

;books
(def bk-dao (book-dao/->book-data-access-object db/db-map))					
(def book-service (book-service-d/->book-service bk-dao))

;comments
(def cmn-dao (comment-dao/->comment-data-access-object db/db-map))					
(def comment-service (comment-service-d/->comment-service cmn-dao))


;;------------------------SESSION ACTIONS-----------------------
; (defn add-user-to-session [request response user]
; 	(assoc response
; 		:session (-> (:session request)
; 								 (assoc :login (:login user))
; 								 (assoc :is_admin (:is_admin user))
; 								 (assoc :mail (:mail user))
; 								 (assoc :id (:id user)))))

; (defn remove-user-from-session [response]
; 	(assoc response
; 		:cookies {"id" {:value nil}}))

; (defn get-user-from-session [request]
; 	(def user-info {:id (get-in request [:session :userid])
; 									:mail (get-in request [:session :mail])
; 									:is_admin (get-in request [:session :is_admin])})
; 	user-info)

;;user actions
(defn add-user
	[request]
	(.add-item user-service (:params request)))

;;book-actions
(defn add-book
  [request]
  (.add-item book-service (:params request)))

(defn get-home-page
	[]
	(do
	(response/redirect "/")
	(view/home (.get-all-items book-service))))

(defn get-book
	[session id]
	(do
	(println id)
	(response/redirect "/book/:id")
	(view/book (.get-book-by-id book-service id))))

;;comment-actions
(defn add-comment
	[request]
	(.add-item comment-service (:params request)))

;Определяем роуты приложения
(defroutes app-routes
					 (GET "/" [] (get-home-page))
					 (GET "/registration" [] (view/registration))
					 (POST "/registration" request (add-user request))
					 (POST "/book/add" request (add-book request))
					 (GET "/book/add" [] (view/add-book))
					 (GET "/book/:id" [:as request id] (get-book (:session request)id))
					 (GET "/auth" [] (view/authorization))
					 (route/resources "/")
		    	   	 (route/not-found "Page not found")) 

(def engine
  (-> (handler/site app-routes) 
  	  (middleware/wrap-json-body {:keywords? true})))
