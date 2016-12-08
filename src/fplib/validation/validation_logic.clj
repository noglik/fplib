(ns fplib.validation.validation-logic
(:require
    [clojure.string :refer :all]))

(defn is-correct-email? [email]
   (let [value (java.util.regex.Pattern/compile "([a-z0-9_-]+\\.)*[a-z0-9_-]+@[a-z0-9_-]+(\\.[a-z0-9_-]+)*\\.[a-z]{2,6}")]
    (let [res (.matcher value email)]
      (.matches res))))

(defn is-int-in-interval? [number max min]
  (let [int-number (new Integer number)]
    (and (<= int-number max ) (>= int-number min))))

(defn is-same-pass? [pas1 pas2]
  (= pas1 pas2))
