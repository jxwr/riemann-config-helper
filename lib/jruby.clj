(import java.io.FileReader)
(import java.io.BufferedReader)
(import org.jruby.embed.ScriptingContainer)

(def container (ScriptingContainer.))

(defn new-script-reader
  "create a Java Reader pointing at the ruby script"
  [ruby-script]
  (let [file-reader (FileReader. ruby-script)
        buffered-reader (BufferedReader. file-reader)]
    buffered-reader))

(defn jruby-receiver [file-name]
  (. container runScriptlet (new-script-reader file-name) file-name))

(def receiver (jruby-receiver "ruby-code.rb"))

(defn call-ruby-method 
  ([method args] 
     (case (count args)
       0 (. container callMethod receiver method Object)
       1 (. container callMethod receiver method (first args) Object)
       (. container callMethod receiver method (to-array args) Object))))

(defn clc [name]
  "cross language call"
  (fn [& args] 
    (call-ruby-method name args)))
