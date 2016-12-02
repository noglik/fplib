(ns fplib.views.view
	(:use hiccup.page
				hiccup.element)
	(:require [fplib.views.renderer :as renderer]))

(defn home
	[books]
	(println books)
	(renderer/render "home.html" {:books books}))
	
(defn add-book
	[]
	(renderer/render "addBook.html"))
	
(defn registration
	[]
	(renderer/render "reg.html"))

(defn authorization
	[]
	(renderer/render "auth.html"))