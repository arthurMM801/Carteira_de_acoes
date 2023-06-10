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

;(defn -main
;  [& args]
;  (cambista/get_aplicacoes))


(defn exibe-detalhes []
  (println "Escreva o nome da acao que voce quer ver!")
  (let [acao (read-string (read-line))]
    (println
      (formata-detalhes
        (cambista/detalhar-acao-brapi acao)
        (cambista/obter-descricao acao)))))

(defn menu [opcao]
  (println)
  (if
    (= opcao 5) (println "Obrigado por usar nossos servicos")
    (do
      (println "Bem vindo a sua carteira de acoes.")
      (println "Escolha o que deseja fazer.")
      (println "1- Listar Acoes da bolsa.")
      (println "2- Exibir os detalhes de um acao.")
      (println "3- Verificar Saldo.")
      (println "4- Listar aplicacoes.")
      (println "5- Encerrar.")
      (let [opcao (read-string (read-line))]
        (do
          (cond
            (= opcao 1) (cambista/obter-acoes-brapi)
            (= opcao 2) (exibe-detalhes)
            (= opcao 3) (println (cambista/get-saldo))
            (= opcao 4) (println (cambista/get-aplicacoes))
            (= opcao 5) ""
            :else (println "Opcao invalida"))
          (println)
          (menu opcao))))))


(defn -main
    [& args]
    (menu 0))