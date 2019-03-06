package com.example.piyus.recyclercardjson;

import android.support.v7.app.ActionBar;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.piyus.recyclercardjson.Adapter.MyAdapter;
import com.example.piyus.recyclercardjson.Adapter.NewsAdapter;
import com.example.piyus.recyclercardjson.Model.CoinsItem;
import com.example.piyus.recyclercardjson.Model.NewsApi;
import com.example.piyus.recyclercardjson.Model.NewsItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class NewsActivity extends AppCompatActivity {
    static final String URL = "https://min-api.cryptocompare.com/data/v2/news/?lang=EN";

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private List<NewsItem> newsApiListItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        setTitle("Today's Headlines");
        ActionBar bar = getSupportActionBar();
        bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#3A1212")));
        /*
            Method1:

            getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0xff00FFED));
            Method2:

            getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources()
                                .getColor(R.color.bg_color)));
            Method3:

            getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#3A1212")));
         */

        recyclerView = findViewById(R.id.recyclerViewNews);
        recyclerView.setHasFixedSize(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        newsApiListItems = new ArrayList<>();
        
        loadRecyclerNewsData();
    }

    private void loadRecyclerNewsData() {
        final NewsApi newsApi = new NewsApi();
        String newsUrl = newsApi.getCRYPTO_BASE_URL() + newsApi.getNews_data_url();
        //Volley Request for Crypto
        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                newsUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) { // whole JSON String
                        //progressDialog.dismiss();
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArray = jsonObject.getJSONArray("Data");
                            for (int i=0; i < jsonArray.length(); i++) {
                                JSONObject object1 = jsonArray.getJSONObject(i);
                                NewsItem newsItem1 = new NewsItem(
                                        object1.getString("imageurl"),
                                        object1.getString("title"),
                                        object1.getString("source"),
                                        object1.getString("body"),
                                        object1.getInt("published_on")
                                );
                                newsApiListItems.add(newsItem1);
                            }

                            adapter = new NewsAdapter(newsApiListItems, getApplicationContext());
                            recyclerView.setAdapter(adapter);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        );// Volley Request for Crypto

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    public void readMoreClk(View view) {
        Toast.makeText(getApplicationContext(), "Coming Soon...", Toast.LENGTH_LONG).show();
    }
}
