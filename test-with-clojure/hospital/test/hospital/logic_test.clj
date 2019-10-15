(ns hospital.logic-test
  (:use clojure.pprint)
  (:require [clojure.test :refer :all]
            [hospital.logic :refer :all]
            [hospital.model :as h.model]
            [schema.core :as s]))

(s/set-fn-validation! true)

(deftest cabe-na-fila?-test
  (let [hospital-cheio
        {:espera [1 35 42 64 21]}]
    ; boundary tests
    ; exatamente na borda e one off. -1, +1., <=, >=, =.
    ; checklist na minha cabeca
    ;zero boundary
    (testing "Que cabe na fila"
      (is (cabe-na-fila? {:espera []} :espera)))
    ; borda do limite
    (testing "Quen não cabe na fila quando a fila está cheiga"
      ;é de simples leitura pois é sequencial
      ; mas a desvantagem é que podemos errar em fazer coisas sequencias
      (is (not (cabe-na-fila? {:espera [1 2 3 4 5]}, :espera)))
      ;bom test não sequencial
      (is (not (cabe-na-fila? hospital-cheio, :espera))))

    ; one off da borda do limite pra cima
    (testing "Que não cabe na fila quando tem mais que uma fila cheia"
      (is (not (cabe-na-fila? {:espera [1 2 3 4 5 6]}, :espera))))

    (testing "Que não cabe na fila quando tem gente mas não está cheia"
      (is (cabe-na-fila? {:espera [1 2 3 4]}, :espera))
      (is (cabe-na-fila? {:espera [1 2]}, :espera)))
    (testing "Que ... quando o departamento não existe"
      (is (not (cabe-na-fila? {:espera [1 2 3 4]}, :raio-x)))))
  )


(deftest chega-em-test
  (let [hospital-cheio
        {:espera [1 35 42 64 21]}]
    (testing "aceita pessoas enquanto cabem pessoas na fila"
      ;implementacao ruim pois testa que escrevemos o que escrevemos.
      ;isto é, testa que erramos o que erramos,  e que acertamos o que acertamos.
      ;(is (= (update {:espera [1, 2]} :espera conj 5)
      ;      (chega-em {:espera [1, 2]}, :espera, 5)))
      ;antes de refatorar o chega em
      (is (= {:espera [1, 2, 3, 4, 5]}
             (chega-em {:espera [1, 2, 3, 4]}, :espera, 5)))
      (is (= {:espera [1, 2, 5]}
             (chega-em {:espera [1, 2]}, :espera, 5)))

      ;dps q refatorou chega em
      ;(is (= {:hospital {:espera [1, 2, 3, 4, 5]}, :resultado :sucesso}
      ;       (chega-em {:espera [1, 2, 3, 4]}, :espera, 5)))
      ;
      ;(is (= {:hospital {:espera [1, 2, 5]}, :resultado :sucesso}
      ;       (chega-em {:espera [1, 2]}, :espera, 5)))
      )


    (testing "não aceita quando não cabe na fila"
      ; verificando que uma exception foi jogada;
      ; código clássico horrível, usamos uma exception GENERICA.
      ; mas qq outro erro generico vai jogar essa exception, e nós vamos achar que deu certo, quando deu ERRADO
      ; quando deu errado
       (is (thrown? clojure.lang.ExceptionInfo
               (chega-em hospital-cheio, :espera 76)))
      ;(is (thrown? IllegalStateException
      ;            (chega-em hospital-cheio, :espera 76)))

      ;string é ruim pois pode quebrar facilmente
      ;  (is (thrown?? clojure.lang.ExceptionInfo "Não cabe ninguém neste departamento."
      ;              (chega-em hospital-cheio, :espera 76)))
      ; outra abordagem, do nil
      ; uma funcao que retorna nil pode ser um problema se um atom invocar ela e estiver no limite vai virar nill
      ;(is (nil? (chega-em hospital-cheio, :espera 76) ))

      ; mas o perigo do swap, teriamos que trabalhar outro ponto a condicao de erro
      ;(is (nil? (chega-em hospital-cheio, :espera 76) ))

      ;OUtra maneira de testar
      ; onde ao invés de como Java, utilizar o TIPO da exception para entender
      ; o TIPO (outro tipo)  de erro que ocrreu, estou usando os dados da
      ; exception para isso
      ; menos sensivel que a mensagem de erro (mesmo que usasse regex)
      ;(is (try
      ;      (chega-em hospital-cheio, :espera 76)
      ;      false
      ;      (catch clojure.lang.ExceptionInfo e
      ;        (= :impossivel-colocar-pessoa-na-fila (:tipo (ex-data e)))
      ;        )))

      ;(is (= {:hospital hospital-cheio, :resultado :impossivel-colocar-pessoa-na-fila}
      ;       (chega-em {:espera [1 35 42 64 21]}, :espera 76))))
      ))
    )

(deftest trasfere-test
  (testing "aceita pessoas se cabe"
    (let [hospital-original {:espera (conj h.model/fila-vazia "5"), :raio-x h.model/fila-vazia}]
      (is (= {:espera []
              :raio-x ["5"]}
             (transfere hospital-original :espera :raio-x)))
      )
    ;(conj h.model/fila-vazia 51 5),
    (let [hospital-original {:espera (conj h.model/fila-vazia "51" "5") :raio-x (conj h.model/fila-vazia "13")}]
      (pprint (transfere hospital-original :espera :raio-x))
      (is (= {:espera ["5"]
              :raio-x ["13" "51"]}
             (transfere hospital-original :espera :raio-x)))
      )
    )
  (testing "recusa pessoas se não cabe"
    (let [hospital-cheio {:espera (conj h.model/fila-vazia "5"), :raio-x (conj h.model/fila-vazia "1" "2" "53" "42" "23")}]
      (is (thrown? clojure.lang.ExceptionInfo
             (transfere hospital-cheio :espera :raio-x)))
      ))
  ; será que faz sentido eu garantir que o schema está do outro lado?
  ; lambrando que este teste não garante exatamente isso, garante só o erro do nil
  ; ... é obvio que  ninguem vai apagar um teste automatizao do nada
  ; mas nao é obvio que ninguem vai apagar uma restricao de um schema. pq naquele momento pode fazer sentido
  ; dado que a pessoa nao tem contexto ....
  ; por isso é legal tenha uma tendencia de criar testes em situacoes criticas
  (testing "Não pode invocar transferência sem hospital"
    (is (thrown? clojure.lang.ExceptionInfo (transfere nil :espera :raio-x))))

  (testing "condicoes obrigatorias "
    (let [hospital {:espera (conj h.model/fila-vazia "5"), :raio-x (conj h.model/fila-vazia "1" "2" "53" "42" "23")}]
      (is (thrown? AssertionError (transfere hospital :nao-existe :raio-x)))
      (is (thrown? AssertionError (transfere hospital :raio-x :nao-existe ))))
      )
  )
