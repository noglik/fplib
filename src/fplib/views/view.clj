(ns fplib.views.view
	(:use hiccup.page
				hiccup.element)
	(:require [fplib.views.renderer :as renderer]))

(defn render-home-page
	[]
	(renderer/render "home.html"))