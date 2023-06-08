(ns carteira-de-acoes.core
  (:require [conversor.interpretador-de-opcoes :refer [interpretar-opcoes]]
            [conversor.formatar :refer [formata-detalhes]]
            [conversor.cambista :as cambista]))

;(defn -main
;  [& args]
;    (cambista/obter-acoes-brapi))

;(defn -main
;  [& args]
;  (let [{filtro :filtro} (interpretar-opcoes args)]
;    (println (formata-detalhes
;      (cambista/detalhar-acao-brapi filtro)
;      (cambista/obter-descricao filtro)))))

(defn -main
  [& args]
  (cambista/get_aplicacoes))

