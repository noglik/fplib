(ns fplib.views.view
	(:use hiccup.page
				hiccup.element)
	(:require [fplib.views.renderer :as renderer]))

(defn home
	[session books]
	(println session)
	(renderer/render "home.html" {:session session :books books}))
	
(defn add-book
	[]
	(renderer/render "addBook.html"))
	
(defn registration
	[]
	(renderer/render "reg.html"))

(defn authorization
	[]
	(renderer/render "auth.html"))

(defn book
	[book]
	(renderer/render "book.html" {:book book}))