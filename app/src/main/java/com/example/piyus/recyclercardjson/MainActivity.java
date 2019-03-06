package com.example.piyus.recyclercardjson;

import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.piyus.recyclercardjson.Adapter.MyAdapter;
import com.example.piyus.recyclercardjson.Model.CoinsItem;
import com.example.piyus.recyclercardjson.Model.CryptoApiModel;

import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    String[] symbolNamesArr = {"Bitcoin ", "Ripple ", "Ethereum ", "EOS ", "Litecoin ", "Bitcoin Cash ", "Tether ", "Binance Coin ", "Tron ", "Stellar "};
    String[] convert_to_currency_arr = {"USD", "EUR", "INR"};
    int images[] = {R.mipmap.ic_bitcoin, R.mipmap.ic_ripple, R.mipmap.ic_ethereum,
            R.mipmap.ic_eos, R.mipmap.ic_litecoin, R.mipmap.ic_bitcoin_cash,
            R.mipmap.ic_tether, R.mipmap.ic_binance, R.mipmap.ic_tron, R.mipmap.ic_stellar};
    String[] symbolsArr = {"BTC", "XRP", "ETH", "EOS", "LTC", "BCH", "USDT", "BNB", "TRX", "XLM"};

    int FLAG = 0;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter rvAdapter;
    private List<CoinsItem> coinsItems;
    Handler handler = new Handler();


    private DrawerLayout dl;
    private ActionBarDrawerToggle abdt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FLAG = 1;

        recyclerView = findViewById(R.id.recyclerView);
        // to keep every item of recycler view of fixed size
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // to remove flickering || blinking effect
        RecyclerView.ItemAnimator animator = recyclerView.getItemAnimator();
        if (animator instanceof SimpleItemAnimator) {
            ((SimpleItemAnimator) animator).setSupportsChangeAnimations(false);
        }

        coinsItems = new ArrayList<>();
        // Start the initial runnable task by posting through the handler
        handler.post(runnableCode);

        // menu nav bar
        dl = findViewById(R.id.dl);
        abdt = new ActionBarDrawerToggle(this, dl, R.string.Open, R.string.Close);
        abdt.setDrawerIndicatorEnabled(true);

        dl.addDrawerListener(abdt);
        abdt.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        NavigationView nav_view = findViewById(R.id.nav_view);
        nav_view.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.myProfile){
                    Toast.makeText(getApplicationContext(), "Profile", Toast.LENGTH_LONG).show();
                    //Intent intent = new Intent(MainActivity.this, NewsActivity.class);
                    //startActivity(intent);
                    dl.closeDrawers();
                }
                else if (id == R.id.myNews){
                    Toast.makeText(getApplicationContext(), "News", Toast.LENGTH_LONG).show();
                    dl.closeDrawers();
                }
                else if (id == R.id.mySettings){
                    Toast.makeText(getApplicationContext(), "Settings", Toast.LENGTH_LONG).show();
                    dl.closeDrawers();
                }
                return true;
            }
        });
    }

    // Define the code block to be executed
    private Runnable runnableCode = new Runnable() {
        @Override
        public void run() {
            // Do something here on the main thread
            Log.d("Handlers", "Called on main thread");
            loadRecyclerViewData();
            // Repeat this the same runnable code block again another 2 seconds
            // 'this' is referencing the Runnable object
            handler.postDelayed(this, 1000);
        }
        // Removes pending code execution
        //handler.removeCallbacks(runnableCode);
    };

    @Override
    protected void onPause() {
        FLAG = 0;
        //Removes pending code execution
        handler.removeCallbacks(runnableCode);
        super.onPause();
    }

    @Override
    protected void onResume() {
        if(FLAG == 0) {
            runnableCode.run();
        }
        super.onResume();
    }

    private void loadRecyclerViewData() {
        final CryptoApiModel cryptoApiModel = new CryptoApiModel();
        String fromSymbolStrings = "";
        String toCurrencyStrings = "";
        for (int i=0; i<symbolsArr.length; i++){
            fromSymbolStrings = fromSymbolStrings + symbolsArr[i] + ",";
        }
        for (int i=0; i<convert_to_currency_arr.length; i++){
            toCurrencyStrings = toCurrencyStrings + convert_to_currency_arr[i] + ",";
        }
        //String crypto_url = CRYPTO_BASE_URL + data_url + fromSymbol + fromSymbolStrings + toSymbol + toCurrencyStrings;
        String crypto_url = cryptoApiModel.getCryptoBaseUrl() + cryptoApiModel.getData_url() + cryptoApiModel.getFromSymbol() + fromSymbolStrings + cryptoApiModel.getToSymbol() + toCurrencyStrings;

        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                crypto_url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) { // whole JSON String
                        //progressDialog.dismiss();
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            coinsItems.clear();
                            for (int i = 0; i < symbolsArr.length; i++) {
                                //JSONObject obj = array.getJSONObject(i);
                                JSONObject rawdata = jsonObject.getJSONObject("RAW");
                                JSONObject coinObj = rawdata.getJSONObject(symbolsArr[i]);
                                JSONObject currencyObj = coinObj.getJSONObject("USD");
                                CoinsItem coinsItem1 = new CoinsItem(symbolsArr[i],
                                        images[i],
                                        symbolNamesArr[i] + "(" + symbolsArr[i] + ")",
                                        "USD: " + currencyObj.getString("PRICE"),
                                        "vol: " + currencyObj.getString("TOTALVOLUME24H"),
                                        currencyObj.getString("CHANGE24HOUR").length() <= 6 ? currencyObj.getString("CHANGE24HOUR") : currencyObj.getString("CHANGE24HOUR").substring(0, 6),
                                        currencyObj.getString("CHANGEPCT24HOUR").length() <= 6 ? currencyObj.getString("CHANGEPCT24HOUR") : currencyObj.getString("CHANGEPCT24HOUR").substring(0, 5) + "%");
                                coinsItems.add(coinsItem1);
                            }
                            // checking rvAdapter change
                            if (recyclerView == null || recyclerView.getAdapter() == null) {
                                //Need to call setAdapter() first;
                                rvAdapter = new MyAdapter(coinsItems, getApplicationContext());
                                recyclerView.setAdapter(rvAdapter);
                            }else{
                                rvAdapter.notifyItemRangeChanged(0, rvAdapter.getItemCount());
                            } // checking rvAdapter change

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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return abdt.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);
    }

}
