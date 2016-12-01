(defproject fplib "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :plugins [[lein-ring "0.9.7"]
  					[lein-localrepo "0.5.3"]
  					[compojure "1.5.1"]]
  :dependencies [[org.clojure/clojure "1.8.0"]
  							 [org.clojure/java.jdbc "0.4.2"]
  							 [mysql/mysql-connector-java "5.1.38"]
  							 [ring/ring "1.5.0"]
  							 [ring/ring-json "0.4.0"]
  							 [metosin/ring-http-response "0.8.0"]
  							 [compojure "1.5.1"]
  							 [selmer "1.0.9"]
  							 [buddy "1.1.0"]
  							 [conman "0.6.2"]]
  :dev-depndencies [[lein-ring "0.4.0"]]
  :ring {:handler fplib.core/engine
  			 :auto-reload? true
  			 :auto-refresh? false})
