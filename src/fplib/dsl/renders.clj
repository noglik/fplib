(ns fplib.dsl.renders
  (:use [fplib.dsl.emit]
        [fplib.dsl.struct-func]))


(declare render-select)
(declare render-expression)


(def none (->Sql "" nil))


(defrecord Select [fields where joins tables limit]
  Sql-like
  (as-sql [this] (as-sql (render-select this))))


(def empty-select (map->Select {}))


(defmacro select
  [& body]
  `(-> empty-select ~@body))


(defn render-limit [s]
  (if-let [l (:limit s)]
    ['LIMIT l]
    none))


(defn render-field
  [[alias name]]
  (if (= alias name)
    name
    [(render-expression name) 'AS alias]))


(defn render-fields
  [{:keys [fields]}]
  (if (or (nil? fields) (= fields :*))
    '*
    (interpose (symbol ",") (map render-field fields))))


(defn render-where
  [{:keys [where]}]
  (if where
    ['WHERE (render-expression where)]
    none))


(defn render-operator
  [op & args]
  (let [ra (map render-expression args)
        lb (symbol "(")
        rb (symbol ")")]
    (if (function-symbol? op)
      ;count, max, etc
      [op lb (interpose (symbol ",") ra) rb]
      ;+ *
      [lb (interpose op (map render-expression args)) rb])))


(defn render-expression
  [etree]
  (if (and (sequential? etree) (symbol? (first etree)))
    (apply render-operator etree)
    etree))


(defn render-tables
  [[table alias]]
  (if (= table alias)
    table
    [table 'AS alias]))


(defn render-join
  [j]
  (get
    {nil (symbol ",")
     :left '[LEFT OUTER JOIN],
     :right '[RIGHT OUTER JOIN],
     :inner '[INNER JOIN],
     :full '[FULL JOIN]} j j))

(defn render-from
  [{:keys [tables joins]}]
  ; секции FROM может и не быть!
  (if (not (empty? joins))
    ['FROM
     ;first join
     (let [[alias jn] (first joins)
           tbl (tables alias)]
       ; первый джоин должен делаться при помощи `(from ..)`
       (assert (nil? jn))
       (render-tables [tbl alias]))
     ; перебираем оставшиеся джоины
    (for [[alias jn clause] (rest joins)
           :let [tbl (tables alias)]]
       [(render-join jn) ; связка JOIN XX или запятая
        (render-tables [tbl alias]) ; имя таблицы и алиас
        (if clause ['ON (render-expression clause)] none)]) ; секция 'ON'
     ]
    none))


(defn render-select
  [select]
  ['SELECT
   (mapv #(% select)
         [render-fields
          render-from
          render-where
          render-limit])])

;mapv Возвращает вектор, состоящий из результата всех 4-ух функций
