(ns riemann.streams)

(defn where-rewrite
  "Rewrites lists recursively. Replaces (metric x y z) with a test matching
  (:metric event) to any of x, y, or z, either by = or re-find. Replaces any
  other instance of metric with (:metric event). Does the same for host,
  service, event, state, time, ttl, tags (which performs an exact match of the
  tag vector), tagged (which checks to see if the given tag is present at all),
  metric_f, and description."
  [expr]
  (let [syms #{'host
               'service
               'state
               'metric
               'metric_f
               'time
               'ttl
               'description
               'tags
               'tagged
               'tagged-all
               'tagged-any
               ;; ksarch-store
               'source
               'system
               'app
               'partition
               'hostname
               'ip
               'module
               'item}]
    (if (list? expr)
      ;; This is a list.
      (if (syms (first expr))
        ;; list starting with a magic symbol
        (let [[field & values] expr]
          (if (= 1 (count values))
            ;; Match one value
            (where-test field (first values))
            ;; Any of the values
            (concat '(or) (map (fn [value] (where-test field value)) values))))

        ;; Other list
        (map where-rewrite expr))

      ;; Not a list
      (if (syms expr)
        ;; Expr *is* a magic sym
        (list (keyword expr) 'event)
        expr))))

(ns riemann.config)
