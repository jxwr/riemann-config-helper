(require '[clojure.string :as string])
(use 'clojure.test)

(def hostMap (ref {}))

(defn shorten-hostname [host]
  (string/replace host #".baidu.com" ""))

(defn hostname 
  ([] (shorten-hostname (.getHostName (java.net.InetAddress/getLocalHost))))
  ([addr] (let [ip (first (string/split addr #":"))
                port (second (string/split addr #":"))
                getHostName #(. (java.net.InetAddress/getByName %) getHostName)]
            (if (nil? (@hostMap addr))
              (dosync 
               (ref-set hostMap 
                        (merge @hostMap {addr (let [host (-> ip getHostName shorten-hostname)]
                                                (if (nil? port)
                                                  host
                                                  (str host ":" port)))}))))
