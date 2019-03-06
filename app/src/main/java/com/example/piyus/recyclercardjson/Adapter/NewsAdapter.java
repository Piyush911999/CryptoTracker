package com.example.piyus.recyclercardjson.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.piyus.recyclercardjson.Model.NewsApi;
import com.example.piyus.recyclercardjson.Model.NewsItem;
import com.example.piyus.recyclercardjson.R;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {

    private List<NewsItem> newsLists;
    private Context context;
    private String date, time;

    public NewsAdapter(List<NewsItem> newsList, Context context) {
        this.newsLists = newsList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.news_item_card_view, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        NewsItem newsItem = newsLists.get(position);
        viewHolder.nTitle.setText(newsItem.getNewsTitle());
        viewHolder.nBody.setText(newsItem.getNewsBody());
        viewHolder.nSource.setText(newsItem.getNewsSource());
        date = findDate(newsItem.getNews_published_on());
        viewHolder.nDate.setText(date);
        time = findTime(newsItem.getNews_published_on());
        viewHolder.nTime.setText(time);

        Picasso.get()
        .load(newsItem.getNewsImageUrl())
        .into(viewHolder.imageView2);
    }

    private String findTime(long unixSeconds) {
        // convert seconds to milliseconds
        Date date = new Date(unixSeconds*1000L);
        // the format of your date 'z' is the timeZone
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a", Locale.ENGLISH);
        // give a timezone reference for formatting (see comment at the bottom)
        sdf.setTimeZone(TimeZone.getDefault());
        String formattedTime = sdf.format(date);
        //Toast.makeText(context, formattedDate, Toast.LENGTH_LONG).show();
        return formattedTime;
    }

    private String findDate(long unixSeconds) {
        //long unixSeconds = 1372339860;
        // convert seconds to milliseconds
        Date date = new Date(unixSeconds*1000L);
        // the format of your date 'z' is the timeZone
        //SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd-MMMM-yy hh:mm a");
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MMMM-yy", Locale.ENGLISH);
        // give a timezone reference for formatting (see comment at the bottom)
        sdf.setTimeZone(TimeZone.getDefault());
        String formattedDate = sdf.format(date);
        //Toast.makeText(context, formattedDate, Toast.LENGTH_LONG).show();
        return formattedDate;
    }

    @Override
    public int getItemCount() {
        return newsLists.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView nTitle, nBody, nSource, nDate, nTime;
        public ImageView imageView2;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            nTitle = itemView.findViewById(R.id.newsTitle);
            nBody = itemView.findViewById(R.id.newsBody);
            nSource = itemView.findViewById(R.id.newsSource);
            imageView2 = itemView.findViewById(R.id.newsImage);
            nDate = itemView.findViewById(R.id.newsDate);
            nTime = itemView.findViewById(R.id.newsTime);

        }
    }
}
