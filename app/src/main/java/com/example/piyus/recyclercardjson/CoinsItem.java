package com.example.piyus.recyclercardjson;

public class CoinsItem {
    private String coinSymbol, coinName, usdVal, eurVal, valueUpBy, percentage;
    private int images;


    public CoinsItem(String coinSymbol, int images, String coinName, String usdVal, String eurVal, String valueUpBy, String percentage) {
        this.coinSymbol = coinSymbol;
        this.images = images;
        this.coinName = coinName;
        this.usdVal = usdVal;
        this.eurVal = eurVal;
        this.valueUpBy = valueUpBy;
        this.percentage = percentage;
    }

    public String getCoinName() {
        return coinName;
    }

    public String getUsdVal() {
        return usdVal;
    }

    public String getEurVal() {
        return eurVal;
    }

    public int getImages() {
        return images;
    }

    public String getValueUpBy() {
        return valueUpBy;
    }

    public String getPercentage() {
        return percentage;
    }

    public String getCoinSymbol() {
        return coinSymbol;
    }

}
