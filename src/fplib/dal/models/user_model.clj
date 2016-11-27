(ns fplib.dal.models.user-model)

(defrecord user-record
	[id
	 login
	 password
	 salt
	 mail
	 is_admin])