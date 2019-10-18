(ns hospital.core
  (:require [clojure.test.check.generators :as gen]
            [hospital.model :as h.model]
            [schema-generators.generators :as g]))

(println (gen/sample gen/boolean 3))
(println (gen/sample gen/string))
(println (gen/sample gen/int))
(println (gen/sample gen/string-alphanumeric 100))

(println (gen/sample (gen/vector gen/int) 5))
(println (gen/sample (gen/vector gen/int 1 5) 5))
(println (gen/sample (gen/vector gen/int 10 ) 5))


; generators do schema deduz generators a partir do schema
(println (g/sample 5 h.model/PacienteID))
(pprint (g/sample 5 h.model/Departamento))
(pprint (g/sample 10 h.model/Hospital))
(println "gerando com generate")
(pprint (g/generate h.model/Hospital))