(ns conversor.cambista
  (:require [clj-http.client :as http-client]
             [cheshire.core :refer [parse-string]]))


; AlphaVantage
(def chave "U9M6M8NUUZ440R6A")

(def urlsearch
  "https://www.alphavantage.co/query?function=SYMBOL_SEARCH")

(def urldetalhes
  ": https://www.alphavantage.co/query?function=TIME_SERIES_DAILY_ADJUSTED")

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

(defn detalhar-acao-alpha [symbol]
  (-> (:body (http-client/get urlsearch
                              {:query-params {"symbol" symbol "apikey" chave}}))))



; Brapi

(def urllista
  "https://brapi.dev/api/quote/list")

(def urlticket
  "https://brapi.dev/api/quote/")

(defn filtro_name [acao]
  (str "Symbol: " (get-in acao ["stock"]) " Name: " (get-in acao ["name"])))

(defn formata_acoes_brapi [ data ]
  (map filtro_name data))

(defn obter-acoes-brapi []
  (-> (:body (http-client/get urllista))
      (parse-string)
      (get-in ["stocks"])
      (formata_acoes_brapi)))

(defn detalhar-acao-brapi [symbol]
  (-> (:body (http-client/get (str urlticket symbol)))
      (parse-string)
      (get-in ["results"])))