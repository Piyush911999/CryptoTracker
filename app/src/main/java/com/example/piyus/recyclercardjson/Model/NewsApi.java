package com.example.piyus.recyclercardjson.Model;

public class NewsApi {
    private String CRYPTO_BASE_URL = "https://min-api.cryptocompare.com";
    private String data_url = "/data/v2/news/?lang=EN";

    public String getCRYPTO_BASE_URL() {
        return CRYPTO_BASE_URL;
    }

    public String getData_url() {
        return data_url;
    }
}
