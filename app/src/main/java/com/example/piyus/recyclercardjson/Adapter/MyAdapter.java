package com.example.piyus.recyclercardjson.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.piyus.recyclercardjson.CoinDetailsSharedActivity;
import com.example.piyus.recyclercardjson.Model.CoinsItem;
import com.example.piyus.recyclercardjson.MainActivity;
import com.example.piyus.recyclercardjson.R;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    private List<CoinsItem> coinsItems;
    private static final String RED = "#FF0000", GREEN = "#00E676", BLACK = "#000000";

    private Context context;
    MainActivity mainActivity;

    public MyAdapter(List<CoinsItem> coinsItems, Context context) {
        this.coinsItems = coinsItems;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {

        final CoinsItem coinsItem = coinsItems.get(position);

        holder.textViewName.setText(coinsItem.getCoinName());
        holder.textViewHead.setText(coinsItem.getUsdVal());
        holder.textViewDesc.setText(coinsItem.getEurVal());
        holder.imageView.setImageResource(coinsItem.getImages());
        holder.textViewUpBy.setText(coinsItem.getValueUpBy());
        holder.textViewPercentage.setText(coinsItem.getPercentage());
        //set color according to rise || fall
        try {
            if(Double.parseDouble(coinsItem.getValueUpBy()) > 0){
                holder.textViewPercentage.setTextColor(Color.parseColor(GREEN));
                holder.textViewUpBy.setTextColor(Color.parseColor(GREEN));
            } else if(Double.parseDouble(coinsItem.getValueUpBy()) < 0){
                holder.textViewPercentage.setTextColor(Color.parseColor(RED));
                holder.textViewUpBy.setTextColor(Color.parseColor(RED));
            } else {
                holder.textViewPercentage.setTextColor(Color.parseColor(BLACK));
                holder.textViewUpBy.setTextColor(Color.parseColor(BLACK));
            }
        } catch(Exception e) {
             Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
        }

        holder.cardContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(context, "" + coinsItem.getCoinName(), Toast.LENGTH_SHORT).show();
                Intent sharedIntent = new Intent(context, CoinDetailsSharedActivity.class);
                sharedIntent.putExtra("COIN_SYMBOL", coinsItem.getCoinSymbol());
                sharedIntent.putExtra("COIN_NAME", coinsItem.getCoinName());
                // to avoid crashing during intent transition
                sharedIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(sharedIntent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return coinsItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView textViewName, textViewHead, textViewDesc, textViewUpBy, textViewPercentage;
        ImageView imageView;
        RelativeLayout cardContainer;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewName = itemView.findViewById(R.id.textViewName);
            textViewHead = itemView.findViewById(R.id.textViewHead);
            textViewDesc = itemView.findViewById(R.id.textViewDesc);
            imageView = itemView.findViewById(R.id.coinLogo);
            textViewUpBy = itemView.findViewById(R.id.textViewUpBy);
            textViewPercentage = itemView.findViewById(R.id.textViewPercentage);
            cardContainer = itemView.findViewById(R.id.cardContainer);

        }
    }
}
