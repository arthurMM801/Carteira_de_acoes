(ns conversor.cambista
  (:require [clj-http.client :as http-client]
             [cheshire.core :refer [parse-string]]))


; AlphaVantage
(def chave "U9M6M8NUUZ440R6A")

(def urlsearch
  "https://www.alphavantage.co/query?function=SYMBOL_SEARCH")

(def urldetalhes
  ": https://www.alphavantage.co/query?function=TIME_SERIES_DAILY_ADJUSTED")

(def urldescricao
  "https://www.alphavantage.co/query?function=OVERVIEW")

(defn filtro_symbol [acao]
  (get-in acao ["1. symbol"]))

(defn formata_acoes_alpha [ data ]
  (map filtro_symbol data))

(defn obter-acoes-alpha [filtro]
  (-> (:body (http-client/get urlsearch
                              {:query-params {"keywords" filtro "apikey" chave}}))
      (parse-string)
      (get-in ["bestMatches"])
      (formata_acoes_alpha)))

(defn obter-descricao [symbol]
  (-> (:body (http-client/get urldescricao
                              {:query-params {"symbol" (str symbol ".sao") "apikey" chave}}))
      ))

(defn detalhar-acao-alpha [symbol]
  (-> (:body (http-client/get urlsearch
                              {:query-params {"symbol" (str symbol ".sao") "apikey" chave}}))))


; Brapi

(def urllista
  "https://brapi.dev/api/quote/list")

(def urlticket
  "https://brapi.dev/api/quote/")

(defn filtro_name [acao]
  (str (format "Symbol: %s Name: %s" (get-in acao ["stock"]) (get-in acao ["name"]))))

(defn formata_lista_brapi [ data ]
  (map filtro_name data))

(defn obter-acoes-brapi []
  (mapv println  (-> (:body (http-client/get urllista))
      (parse-string)
      (get-in ["stocks"])
      (formata_lista_brapi))))


(defn formata_acoes_brapi [ data ]
  (str "Nome: " (get-in data ["longName"])))

(defn detalhar-acao-brapi [symbol]
  (-> (:body (http-client/get (str urlticket symbol)))
      (parse-string)
      (get-in ["results"])
      ))



;; API Carteira de Acoes

(def listaTransacoesUrl "http://localhost:3000/aplicacoes")

(defn formata_lista_aplicacoes [lista]
 (mapv (fn [transacao]
          (let [id (:acao transacao)
                acao (:acao transacao)
                valor (float (* (:cotacao transacao) (:quantidade transacao)))]
            (str (format "id %s - Acao: %s - Valor: %.2f" id acao valor))))
        lista))

(defn get_aplicacoes []
    (mapv println (-> (:body (http-client/get listaTransacoesUrl))
        (parse-string true)
        (formata_lista_aplicacoes))))
