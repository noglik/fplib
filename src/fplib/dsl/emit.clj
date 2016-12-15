(ns fplib.dsl.emit)

(defrecord Sql [sql args])

(defprotocol Sql-like
  (as-sql [this]))

(defn- join-sqls
  ([] (Sql. "" nil))
  ([^Sql s1 ^Sql s2]
    (Sql. (str (.sql s1) " " (.sql s2)) (concat (.args s1) (.args s2)))))

; вспомогательная функция
(defn quote-name
  [s]
  (let [x (name s)]
    (if (= "*" x)
      x
      (str \` x \`))))

(extend-protocol Sql-like

  ; для любого `x` (= (as-sql (as-sql x)) (as-sql x))
  Sql
  (as-sql [this] this)

  ; по умолчанию считаем все объекты параметрами для запросов
  Object
  (as-sql [this] (Sql. "?" [this]))

  ; экранируем имена таблиц и столбцов
  clojure.lang.Keyword
  (as-sql [this] (Sql. (quote-name this) nil))

  ; символами обозначаем ключевые слова SQL
  clojure.lang.Symbol
  (as-sql [this] (Sql. (name this) nil))

  clojure.lang.Sequential
  (as-sql [this]
    (reduce join-sqls (map as-sql this)))

  ; для nil специальное ключевое слово
  nil
  (as-sql [this] (Sql. "NULL" nil)))
