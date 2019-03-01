package com.example.piyus.recyclercardjson;

import android.app.ProgressDialog;
import android.os.Handler;
import android.support.v4.widget.CircularProgressDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    String[] symbolNamesArr = {"Bitcoin ", "Ripple ", "Ethereum ", "EOS ", "Litecoin ", "Bitcoin Cash ", "Tether ", "Binance Coin ", "Tron ", "Stellar "};
    String[] symbolsArr = {"BTC", "XRP", "ETH", "EOS", "LTC", "BCH", "USDT", "BNB", "TRX", "XLM"};
    String[] convert_to_currency_arr = {"USD", "EUR", "INR"};
    int images[] = {R.mipmap.ic_bitcoin, R.mipmap.ic_ripple, R.mipmap.ic_ethereum,
            R.mipmap.ic_eos, R.mipmap.ic_litecoin, R.mipmap.ic_bitcoin_cash,
            R.mipmap.ic_tether, R.mipmap.ic_binance, R.mipmap.ic_tron, R.mipmap.ic_stellar};

    int FLAG = 0;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private List<CoinsItem> coinsItems;
    Handler handler = new Handler();

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
        CryptoApiModel cryptoApiModel = new CryptoApiModel();
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

//        final ProgressDialog progressDialog = new ProgressDialog(this);
//        progressDialog.setMessage("Loading Data..");
//        progressDialog.show();
//
//        // Volley request for contacts
//        StringRequest stringRequest = new StringRequest(Request.Method.GET,
//                URL,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) { // whole JSON String
//                        progressDialog.dismiss();
//                        try {
//                            JSONObject jsonObject = new JSONObject(response);
//                            JSONArray array = jsonObject.getJSONArray(ARRAY_NAME);
//                            // to get data from array
//                            for(int i=0; i < array.length(); i++) {
//                                JSONObject obj = array.getJSONObject(i);
//                                ListItem listItem1 = new ListItem(obj.getString("name"), obj.getString("email"));
//                                listItems.add(listItem1);
//                            }
//                            adapter = new MyAdapter(listItems, getApplicationContext());
//                            recyclerView.setAdapter(adapter);
//
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//
//                    }
//                }
//        );// Volley Request for contacts


// Volley request for Crypto
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
                            // checking adapter change
                            if (recyclerView == null || recyclerView.getAdapter() == null) {
                                //Need to call setAdapter() first;
                                adapter = new MyAdapter(coinsItems, getApplicationContext());
                                recyclerView.setAdapter(adapter);
                            }else{
                                adapter.notifyItemRangeChanged(0, adapter.getItemCount());
                            } // checking adapter change

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
}
