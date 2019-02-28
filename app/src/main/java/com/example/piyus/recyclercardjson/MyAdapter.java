package com.example.piyus.recyclercardjson;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    private List<CoinsItem> coinsItems;
    private static final String RED = "#FF0000", GREEN = "#00E676";

    private Context context;

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
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        CoinsItem coinsItem = coinsItems.get(position);

        holder.textViewName.setText(coinsItem.getCoinName());
        holder.textViewHead.setText(coinsItem.getUsdVal());
        holder.textViewDesc.setText(coinsItem.getEurVal());
        holder.imageView.setImageResource(coinsItem.getImages());
        holder.textViewUpBy.setText(coinsItem.getValueUpBy());
        holder.textViewPercentage.setText(coinsItem.getPercentage());
        //set color according to rise || fall
        if(Double.parseDouble(coinsItem.getValueUpBy()) > 0){
            holder.textViewPercentage.setTextColor(Color.parseColor(GREEN));
            holder.textViewUpBy.setTextColor(Color.parseColor(GREEN));
        } else {
            holder.textViewPercentage.setTextColor(Color.parseColor(RED));
            holder.textViewUpBy.setTextColor(Color.parseColor(RED));
        }

    }

    @Override
    public int getItemCount() {
        return coinsItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView textViewName, textViewHead, textViewDesc, textViewUpBy, textViewPercentage;
        ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewName = itemView.findViewById(R.id.textViewName);
            textViewHead = itemView.findViewById(R.id.textViewHead);
            textViewDesc = itemView.findViewById(R.id.textViewDesc);
            imageView = itemView.findViewById(R.id.coinLogo);
            textViewUpBy = itemView.findViewById(R.id.textViewUpBy);
            textViewPercentage = itemView.findViewById(R.id.textViewPercentage);

        }
    }
}
