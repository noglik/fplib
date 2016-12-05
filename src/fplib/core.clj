(ns fplib.core
	(:use compojure.core
				ring.middleware.cookies)
	(:require 
					[compojure.route :as route]
					[compojure.handler :as handler]
					[ring.middleware.json :as middleware]
					[ring.util.response :as response]
					[ring.middleware.session :as ss]

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


;------------------------SESSION ACTIONS-----------------------
(defn add-user-to-session [request response user]
	(assoc response
		:session (-> (:session request)
								 (assoc :login (:login user))
								 (assoc :is_admin (:is_admin user))
								 (assoc :mail (:mail user))
								 (assoc :id (:id user))))
	(println "ADD USER TO SESSION"))

(defn remove-user-from-session [response]
	(assoc response
		:cookies {"id" {:value nil}}))

(defn get-user-from-session [request]
	(def user-info {:id (get-in request [:session :userid])
									:mail (get-in request [:session :mail])
									:is_admin (get-in request [:session :is_admin])})
	user-info)

;;user actions
;registration
(defn add-user
	[request]
	(.add-item user-service (:params request))
	(response/redirect "/"))

;authorization
(defn auth-user
	[request]
	(let [current-user (.sign-in user-service
															 (get-in request [:params :login])
															 (get-in request [:params :password]))
				request-login (get-in request [:params :login])]

			 (if (= current-user nil)
			 	(println "User find error")
			 	(do 
			 		(println "User session opened")
			 		(-> (response/redirect "/")
			 				(add-user-to-session request current-user))))))

;;book-actions
(defn add-book
  [session request]
  (.add-item book-service (:params request)))

(defn get-home-page
	[session]
	(do
	;(response/redirect "/")
	(view/home session (.get-all-items book-service))))

(defn get-book
	[session id]
	(do
	(response/redirect "/book/:id")
	(view/book (.get-book-by-id book-service id))))

;;comment-actions
(defn add-comment
	[session request]
	(.add-item comment-service (:params request)))

;Определяем роуты приложения
(defroutes app-routes
					 (GET "/" [:as request] (get-home-page (:session request)))
					 (GET "/registration" [] (view/registration))
					 (POST "/registration" request (add-user request))
					 (POST "/book/add" request (add-book (:session request) request))
					 (GET "/book/add" [] (view/add-book))
					 (GET "/book/:id" [:as request id] (get-book (:session request)id))
					 (GET "/auth" [] (view/authorization))
					 (POST "/auth" request (auth-user request))
					 (route/resources "/")
		    	 (route/not-found "Page not found")) 

(def engine
  (-> (handler/site app-routes) 
  	  (middleware/wrap-json-body {:keywords? true})
  	  (ss/wrap-session)
  	  (wrap-cookies)))
