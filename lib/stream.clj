
(defn stream-seq [& children]
  (fn [e] (call-rescue e children)))

(defmacro defstreams [name & streams]
  "a wrapper of streams, make it easy to define top-level-like 
   stream in other config files"
  `(def ~name (stream-seq ~@streams)))
