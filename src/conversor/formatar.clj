(ns conversor.formatar)

(defn extrai-Variacao [data]
  (- (get-in data ["regularMarketPreviousClose"]) (get-in data ["regularMarketOpen"])))

(defn extrai-Variacao-percent [data]
  (* 100 (/
    (- (get-in data ["regularMarketPreviousClose"]) (get-in data ["regularMarketOpen"]))
    (get-in data ["regularMarketPreviousClose"]))))

(defn filtrar-tipo [resposta]
  (let [tipo-ativo (re-find #"PN|ON" resposta)]
    (when tipo-ativo tipo-ativo) ))

(defn valida-descricao [descricao]
  (if (= descricao "{}")
    "O ativo nao tem descricao" descricao))

(defn formata-detalhes [data-brapi descricao]
  (let [data (get data-brapi 0)]
    (str (format
      "Nome: %s\nCodigo: %s\nTipo de ativo: %s\nDescricao: %s\nVariacao do dia: %.2f\nVariacao do dia em percentual: %.2f\nUltimo Preco: %f\nPreco Maximo: %f\nPreco Minimo: %f\nPreco de Abertura: %f\nPreco de fechamento: %f\nHora: %s"
      (get-in data ["longName"])
      (get-in data ["symbol"])
      (filtrar-tipo (get-in data ["shortName"]))
      (valida-descricao descricao)
      (extrai-Variacao data)
      (extrai-Variacao-percent data)
      (float (get-in data ["regularMarketPrice"]))
      (float (get-in data ["regularMarketDayHigh"]))
      (float (get-in data ["regularMarketDayLow"]))
      (float (get-in data ["regularMarketOpen"]))
      (float (get-in data ["regularMarketPreviousClose"]))
      (get-in data ["regularMarketTime"])
      ))))

(defn filtro-name [acao]
  (str (format "Symbol: %s Name: %s"
               (get-in acao ["stock"])
               (get-in acao ["name"]))))
(defn formata-lista-brapi [ data ]
  (map filtro-name data))

(defn formata-saldo [saldo]
  (format "Saldo %s" saldo))

(defn formata-lista-aplicacoes [lista]
  (println)
  (mapv (fn [transacao]
          (let [acao (:acao transacao)
                valor (float (* (:cotacao transacao) (:quantidade transacao)))]
            (str (format "Acao: %s - Valor: %.2f" acao valor))))
        lista))
