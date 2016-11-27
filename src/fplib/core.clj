(ns fplib.core
	(:use compojure.core)
	(:require 
					[compojure.route :as route]
					[compojure.handler :as handler]
					[ring.middleware.json :as middleware]
					[ring.util.response :as response]
					[fplib.views.view :as view]))

(defroutes app-routes
					 (GET "/" [] (view/render-home-page)))

(def engine
  (-> (handler/site app-routes) (middleware/wrap-json-body {:keywords? true})))
