(ns conversor.formatar
  (:require [clojure.string :as str]))

(defn split [x] (str/split x #"  "))

(defn extrai-Variacao [data]
  (- (get-in data ["regularMarketPreviousClose"]) (get-in data ["regularMarketOpen"])))

(defn extrai-Variacao-percent [data]
  (* 100 (/
    (- (get-in data ["regularMarketPreviousClose"]) (get-in data ["regularMarketOpen"]))
    (get-in data ["regularMarketPreviousClose"]))))



(defn formata-detalhes [data-brapi descricao]
  (let [data (get data-brapi 0)]
    (str (format
      "Nome: %s\nCodigo: %s\nTipo de ativo: %s\nDescricao: %s\nVariacao do dia: %.2f\nVariacao do dia em percentual: %.2f\nUltimo Preco: %f\nPreco Maximo: %f\nPreco Minimo: %f\nPreco de Abertura: %f\nPreco de fechamento: %f\nHora: %s"
      (get-in data ["longName"])
      (get-in data ["symbol"])
      (get-in data ["shortName"])
      descricao
      (extrai-Variacao data)
      (extrai-Variacao-percent data)
      (float (get-in data ["regularMarketPrice"]))
      (float (get-in data ["regularMarketDayHigh"]))
      (float (get-in data ["regularMarketDayLow"]))
      (float (get-in data ["regularMarketOpen"]))
      (float (get-in data ["regularMarketPreviousClose"]))
      (get-in data ["regularMarketTime"])
      ))))
