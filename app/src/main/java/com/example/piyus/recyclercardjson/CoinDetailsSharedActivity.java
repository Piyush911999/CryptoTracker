package com.example.piyus.recyclercardjson;

import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.piyus.recyclercardjson.Model.CryptoApiModel;

import org.json.JSONException;
import org.json.JSONObject;

public class CoinDetailsSharedActivity extends AppCompatActivity {
    TextView coinName2Shared, coinValInUSDShared, coinVolShared, coinValUpByShared, coinPercentUpByShared;
    String fromSymbolString = "", toCurrencyString = "USD", coinNameString = "", volumeString = "vol: ", usdString = "USD: ";
    private static final String RED = "#FF0000", GREEN = "#00E676", BLACK = "#000000";
    String valueOfCoinChangedBy = "0", percentOfCoinChangedBy = "0";
    Handler handler = new Handler();
    int images[] = {R.mipmap.ic_bitcoin, R.mipmap.ic_ripple, R.mipmap.ic_ethereum,
            R.mipmap.ic_eos, R.mipmap.ic_litecoin, R.mipmap.ic_bitcoin_cash,
            R.mipmap.ic_tether, R.mipmap.ic_binance, R.mipmap.ic_tron, R.mipmap.ic_stellar};
    ImageView coinLogoImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coin_details_shared);

        coinName2Shared = findViewById(R.id.coinName2Shared);
        coinValInUSDShared = findViewById(R.id.coinValInUSDShared);
        coinVolShared = findViewById(R.id.coinVolShared);
        coinValUpByShared = findViewById(R.id.coinValUpByShared);
        coinPercentUpByShared = findViewById(R.id.coinPercentUpByShared);
        coinLogoImageView = findViewById(R.id.coinLogoShared);

        Intent intent = getIntent();
        fromSymbolString = intent.getStringExtra("COIN_SYMBOL");
        coinNameString = intent.getStringExtra("COIN_NAME");
        //Toast.makeText(getApplicationContext(), fromSymbolString, Toast.LENGTH_SHORT).show();
        findAndSetCoinLogo();
        handler.post(runnableCode);
    }

    private void findAndSetCoinLogo() {
        switch (fromSymbolString) {
            case "BTC":
                coinLogoImageView.setImageResource(images[0]);
                break;
            case "XRP":
                coinLogoImageView.setImageResource(images[1]);
                break;
            case "ETH":
                coinLogoImageView.setImageResource(images[2]);
                break;
            case "EOS":
                coinLogoImageView.setImageResource(images[3]);
                break;
            case "LTC":
                coinLogoImageView.setImageResource(images[4]);
                break;
            case "BCH":
                coinLogoImageView.setImageResource(images[5]);
                break;
            case "USDT":
                coinLogoImageView.setImageResource(images[6]);
                break;
            case "BNB":
                coinLogoImageView.setImageResource(images[7]);
                break;
            case "TRX":
                coinLogoImageView.setImageResource(images[8]);
                break;
            case "XLM":
                coinLogoImageView.setImageResource(images[9]);
                break;
            default:
                Toast.makeText(getApplicationContext(), "Image Resource not found", Toast.LENGTH_SHORT).show();

        }
    }

    // Define the code block to be executed
    private Runnable runnableCode = new Runnable() {
        @Override
        public void run() {
            // Do something here on the main thread
            Log.d("Handlers", "Called on another thread");
            loadCryptoData();
            // Repeat this the same runnable code block again another 2 seconds
            // 'this' is referencing the Runnable object
            handler.postDelayed(this, 1000);
        }
        // Removes pending code execution
        //handler.removeCallbacks(runnableCode);
    };

    @Override
    protected void onPause() {
        // Removes pending code execution
        handler.removeCallbacks(runnableCode);
        super.onPause();
    }

    private void loadCryptoData() {
        CryptoApiModel cryptoApiModel = new CryptoApiModel();
        String crypto_url = cryptoApiModel.getCryptoBaseUrl() + cryptoApiModel.getData_url() + cryptoApiModel.getFromSymbol() + fromSymbolString + cryptoApiModel.getToSymbol() + toCurrencyString;

        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                crypto_url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonObject = new JSONObject(response);
                            JSONObject rawdata = jsonObject.getJSONObject("RAW");
                            JSONObject coinObj = rawdata.getJSONObject(fromSymbolString);
                            JSONObject currObj = coinObj.getJSONObject(toCurrencyString);
                            valueOfCoinChangedBy = currObj.getString("CHANGE24HOUR").length() <= 6 ? currObj.getString("CHANGE24HOUR") : currObj.getString("CHANGE24HOUR").substring(0, 6);
                            percentOfCoinChangedBy = currObj.getString("CHANGEPCT24HOUR").length() <= 6 ? currObj.getString("CHANGEPCT24HOUR") : currObj.getString("CHANGEPCT24HOUR").substring(0, 5) + "%";
                            coinName2Shared.setText(coinNameString);
                            coinValInUSDShared.setText(usdString + currObj.getString("PRICE"));
                            coinVolShared.setText(volumeString + currObj.getString("TOTALVOLUME24H"));
                            coinValUpByShared.setText(valueOfCoinChangedBy);
                            coinPercentUpByShared.setText(percentOfCoinChangedBy);
                            //set color according to rise || fall
                            try {
                                if(Double.parseDouble(valueOfCoinChangedBy) > 0){
                                    coinValUpByShared.setTextColor(Color.parseColor(GREEN));
                                    coinPercentUpByShared.setTextColor(Color.parseColor(GREEN));
                                } else if(Double.parseDouble(valueOfCoinChangedBy) < 0){
                                    coinValUpByShared.setTextColor(Color.parseColor(RED));
                                    coinPercentUpByShared.setTextColor(Color.parseColor(RED));
                                } else {
                                    coinValUpByShared.setTextColor(Color.parseColor(BLACK));
                                    coinPercentUpByShared.setTextColor(Color.parseColor(BLACK));
                                }
                            } catch(Exception e) {
                                Toast.makeText(getApplicationContext(), "Error 222", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}
