package com.example.piyus.recyclercardjson.Model;

public class NewsItem {

    private String newsImageUrl, newsTitle, newsSource, newsBody;
    private int news_published_on;

    public NewsItem(String newsImageUrl, String newsTitle, String newsSource, String newsBody, int news_published_on) {

        this.newsImageUrl = newsImageUrl;
        this.newsTitle = newsTitle;
        this.newsSource = newsSource;
        this.newsBody = newsBody;
        this.news_published_on = news_published_on;
    }

    public String getNewsImageUrl() {
        return newsImageUrl;
    }

    public String getNewsTitle() {
        return newsTitle;
    }

    public String getNewsSource() {
        return newsSource;
    }

    public String getNewsBody() {
        return newsBody;
    }

    public int getNews_published_on() {
        return news_published_on;
    }
}
