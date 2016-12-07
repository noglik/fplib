(ns fplib.views.view
	(:use hiccup.page
				hiccup.element)
	(:require [fplib.views.renderer :as renderer]))

(defn home
	[session books]
	(println session)
	(renderer/render "home.html" {:session session :books books}))

(defn add-book
	[session]
	(renderer/render "addBook.html" {:session session}))

(defn registration
	[]
	(renderer/render "reg.html"))

(defn authorization
	[]
	(renderer/render "auth.html"))

(defn book
	[session book comments]
	(renderer/render "book.html" {:session session :book book :comments comments}))

(defn search
	[session books]
	(renderer/render "search.html" {:session session :books books}))
