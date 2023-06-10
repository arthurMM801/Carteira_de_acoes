(ns conversor.cambista
  (:require [clj-http.client :as http-client]
            [cheshire.core :refer [parse-string generate-string]]
            [conversor.formatar :as formatar]))


; AlphaVantage
(def chave "U9M6M8NUUZ440R6A")

(def urldescricao
  "https://www.alphavantage.co/query?function=OVERVIEW")


(defn obter-descricao [symbol]
  (-> (:body (http-client/get urldescricao
                              {:query-params {"symbol" (str symbol ".sao") "apikey" chave}}))))



; Brapi
(def urllista
  "https://brapi.dev/api/quote/list")

(def urlticket
  "https://brapi.dev/api/quote/")

(defn obter-acoes-brapi []
  (mapv println  (-> (:body (http-client/get urllista))
      (parse-string)
      (get-in ["stocks"])
      (formatar/formata-lista-brapi))))

(defn detalhar-acao-brapi [symbol]
  (-> (:body (http-client/get (str urlticket symbol)))
      (parse-string)
      (get-in ["results"])))



;; API Carteira de Acoes

(def listaTransacoesUrl "http://localhost:3000/aplicacoes")

(defn get-aplicacoes []
    (mapv println (-> (:body (http-client/get listaTransacoesUrl))
        (parse-string true)
        (formatar/formata-lista-aplicacoes))))

(defn get-aplicacoes-filtro []
  (println "Escreva o tipo das acoes que voce quer ver! (ON|PN)")
  (let [tipo (read-line)]
    (mapv println (-> (:body (http-client/get listaTransacoesUrl
                                              {:query-params {"tipo" tipo}}))
                      (parse-string true)
                      (formatar/formata-lista-aplicacoes)))))


;; verifica saldo

(def urlSaldo "http://localhost:3000/saldo")
(defn get-saldo []
    (->
      (:body (http-client/get urlSaldo))
      (parse-string)
      (get-in ["saldo"])))

(defn get-investimento []
  (format "Investido: %.2f" (float (- 1000 (get-saldo)))))


;; Listar transacoes

(def urlTrasacoes "http://localhost:3000/transacoes")


;; Transacoes

(defn fazer-transacao [objeto]
  (let [headers {"Content-Type" "application/json"}]
    (println objeto)
    (http-client/post urlTrasacoes {:body (generate-string objeto) :headers headers})))



(defn ler-valor [mensagem]
  (println mensagem)
  (read-line))


(defn resgate-ou-aplicacao []
  (println "Digite o nome da acao")
  (let [acao (ler-valor "Informe o codigo da acao: ")
        dados (get (detalhar-acao-brapi acao) 0)
        cotacao (get-in dados ["regularMarketPrice"])
        tipo (formatar/filtrar-tipo (get-in dados ["shortName"]))
        quantidade (ler-valor "Informe a quantidade: ")
        operacao (ler-valor "Informe a operação: "); corrigido aqui
        objeto {:cotacao cotacao ; convertido para Double
                :acao acao
                :tipo tipo
                :quantidade (Integer/parseInt quantidade)
                :operacao operacao}]
    (fazer-transacao objeto)))


