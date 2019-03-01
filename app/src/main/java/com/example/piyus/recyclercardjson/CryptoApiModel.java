package com.example.piyus.recyclercardjson;

public class CryptoApiModel {
    private String CRYPTO_BASE_URL = "https://min-api.cryptocompare.com";
    private String data_url = "/data/pricemultifull?";
    private String fromSymbol = "fsyms="; // https://min-api.cryptocompare.com/data/pricemulti?fsyms=BTC,ETH&tsyms=USD,EUR
    private String toSymbol = "&tsyms="; //fsyms=BTC,ETH,LTC,BCH,ION,LTE&tsyms=USD,EUR
    String[] symbolsArr = {"BTC", "XRP", "ETH", "EOS", "LTC", "BCH", "USDT", "BNB", "TRX", "XLM"};

    public String getCryptoBaseUrl() {
        return CRYPTO_BASE_URL;
    }

    public String getData_url() {
        return data_url;
    }

    public String getFromSymbol() {
        return fromSymbol;
    }

    public String getToSymbol() {
        return toSymbol;
    }
}
