(ns carteira-de-acoes.core
  (:require	[conversor.formatador :refer [formatar]]
            [conversor.interpretador-de-opcoes :refer [interpretar-opcoes]]
            [conversor.cambista :refer [obter-acoes-alpha obter-acoes-brapi detalhar-acao-brapi]]))


;(defn -main
;  [& args]
;  (let [{de :de para :para} (interpretar-opcoes args)]
;    (-> (cotar de para)
;         (formatar de para)
;         (prn))))


(defn -main
  [& args]
  (let [{filtro :filtro} (interpretar-opcoes args)]
    (->
      (obter-acoes-brapi)
      (prn))))