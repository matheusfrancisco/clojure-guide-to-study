(defproject example-pjc "0.0.1"
  :description "Example project without lein new"
  :dependencies [[org.eclipse.jgit/org.eclipse.jgit "5.5.1.201910021850-r"]
                 [org.eclipse.jgit/org.eclipse.jgit.ssh.apache "5.5.1.201910021850-r"]]
  :profiles {:dev {:dependencies [[org.clojure/clojure "1.10.1"]]}}
  :main ^:skip-aot example-pjc.data
  :plugins [[lein-marginalia "0.9.1"]]
  :java-source-paths ["src/java"]
  :repositories {"jgit-repository" "https://repo.eclipse.org/content/groups/releases/"})
