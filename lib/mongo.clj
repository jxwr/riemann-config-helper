(require '[monger.core :as mg]
         '[monger.collection :as mgc])

(import '[com.mongodb MongoOptions ServerAddress])
(import '[org.bson.types ObjectId]
        '[com.mongodb DB WriteConcern])

(defn mongodb-connect! [addr]
  (mg/connect! addr)
  (mg/set-db! (mg/get-db "riemann")))

(defn mongo-save-event 
  ([coll]
     (fn [event] (mgc/insert coll event)))
  ([db coll]
     (fn [event] (mgc/insert db coll event))))

(defn mongo-save 
  ([coll dict]
     (fn [event] (mgc/insert coll dict)))
  ([db coll dict]
     (fn [event] (mgc/insert db coll dict))))
