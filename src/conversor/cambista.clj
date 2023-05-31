(ns conversor.cambista
  (:require [clj-http.client :as http-client]
             [cheshire.core :refer [parse-string]]))

(def chave "U9M6M8NUUZ440R6A")

(def api-url
  "https://free.currencyconverterapi.com/api/v6/convert")

(def urlsearch
  "https://www.alphavantage.co/query?function=SYMBOL_SEARCH")

;; (http-client/get urlsearch {:query-params {"keywords" "PETR" "apikey" chave}})


(defn obter-acoes [filtro]
  (-> (:body (http-client/get urlsearch
                               {:query-params {"keywords" filtro "apikey" chave}}))
      (parse-string)
      (get-in	["bestMatches"])))