(ns carteira-de-acoes.core
  (:require [conversor.interpretador-de-opcoes :refer [interpretar-opcoes]]
            [conversor.formatar :refer [formata-detalhes]]
            [conversor.cambista :refer [obter-acoes-alpha detalhar-acao-alpha
                                        obter-acoes-brapi detalhar-acao-brapi
                                        obter-descricao]]))


;(defn -main
;  [& args]
;  (let [{de :de para :para} (interpretar-opcoes args)]
;    (-> (cotar de para)
;         (formatar de para)
;         (prn))))


;(defn -main
;  [& args]
;  (let [{filtro :filtro} (interpretar-opcoes args)]
;    (->
;      (obter-acoes-brapi)
;      (prn))))

(defn -main
  [& args]
  (let [{filtro :filtro} (interpretar-opcoes args)]
    (println (formata-detalhes
      (detalhar-acao-brapi filtro)
      (obter-descricao filtro)))))