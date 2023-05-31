(ns conversor.cambista
  (:require [clj-http.client :as http-client]
             [cheshire.core :refer [parse-string]]))

(def chave "U9M6M8NUUZ440R6A")

(def api-url
  "https://free.currencyconverterapi.com/api/v6/convert")

(def urlsearch
  "https://www.alphavantage.co/query?function=SYMBOL_SEARCH")

(def urldetalhes
  ": https://www.alphavantage.co/query?function=TIME_SERIES_DAILY_ADJUSTED")

;; (http-client/get urlsearch {:query-params {"keywords" "PETR" "apikey" chave}})



(defn obter-acoes [filtro]
  (map (fn [x] ( (get x "1. symbol"))) (-> (:body (http-client/get urlsearch
                               {:query-params {"keywords" filtro "apikey" chave}}))
      (parse-string)
      (get-in ["bestMatches"]))))


(defn detalhar-acao [symbol]
  (-> (:body (http-client/get urlsearch
                              {:query-params {"symbol" symbol "apikey" chave}}))
      (parse-string)
      (get-in ["bestMatches"])))