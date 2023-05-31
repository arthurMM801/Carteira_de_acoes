(ns conversor.interpretador-de-opcoes
  (:require [clojure.tools.cli :refer [parse-opts]]))


(def opcoes-do-programa
  [["-f" "--filtro nome da acao" "moeda base para conversao" :default	"PETR"]])

(defn	interpretar-opcoes	[argumentos]
  (:options	(parse-opts	argumentos	opcoes-do-programa)))