
### Macro `defstreams`

Easier for defining streams in another file to be included in riemann.config

```clojure
(defstreams store-agent-streams
  (where (system "twemproxy") store-agent-twemproxy-streams)
  (where (system "redis") store-agent-redis-streams)
  (where (system "memcached") store-agent-memcached-streams))
```

### Mongodb

Put this in riemann.config

```clojure
(mongodb-connect! {:host "cq01-rdqa-dev048.cq01" :port 8013})
```

```clojure
;; DOC: http://clojuremongodb.info/articles/getting_started.html
;; Examples:
;;
;; 1. changedb 
;; (mg/set-db! (mg/get-db "monger-test"))
;;
;; 2. insert 
;; * with explicit document id (recommended) 
;;   (mgc/insert "documents" { :_id (ObjectId.) :first_name "John" :last_name "Lennon" })
;; * without document id (when you don't need to use it after storing the document)
;;   (insert "document" { :first_name "John" :last_name "Lennon" })
;;
;; 3. multiple documents at once
;; (mgc/insert-batch "document" [{ :first_name "John" :last_name "Lennon" }
;;                               { :first_name "Paul" :last_name "McCartney" }])
;; 4. from http://riemann.io/howto.html
;; (where (service "foo")
;;  (fn [event]
;;    ; Log a message
;;    (info "I got an event:" event)
;;
;;    ; Then extract some fields and insert them into a DB.
;;    (save-to-my-database (:description event) (:metric event))))
```

### JRuby

Content of `ruby-code.rb`:

```
require 'java'

# namespace
def clojure
  Java::Clojure
end

def riemann
  Java::Riemann
end

java_import clojure.lang.Keyword

# test methods

def hello()
  "hello"
end

def echo(e)
  e
end

def add(a, b)
  a + b
end

def _(name)
  # clojure keyword wraper
  Keyword.intern(name)
end

def host(event)
  # rimann event as hash
  p event[_("func")]
  top = event[_("func")]
  p event[_("where")]
  event[_("host")]
end
```

Call ruby functions in riemann:

```clojure
(prn ((clc "hello")))
(prn ((clc "echo") "random"))
(prn ((clc "add") 1 2))
```
