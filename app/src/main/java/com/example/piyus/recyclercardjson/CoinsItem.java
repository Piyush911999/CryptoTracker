package com.example.piyus.recyclercardjson;

public class CoinsItem {
    private String coinName, usdVal, eurVal, valueUpBy, percentage;
    private int images;

    public CoinsItem(int images, String coinName, String usdVal, String eurVal, String valueUpBy, String percentage) {
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


}
