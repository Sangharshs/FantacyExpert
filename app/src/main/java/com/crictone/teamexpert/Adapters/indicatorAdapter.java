package com.crictone.teamexpert.Adapters;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.crictone.teamexpert.Model.GameModel;
import com.crictone.teamexpert.R;

import java.util.List;

import static com.crictone.teamexpert.util.ApiConfig.IMG;

public class indicatorAdapter extends RecyclerView.Adapter<indicatorAdapter.indicatorViewHolder>{

    Context RestaurantContx;
    List<GameModel> restgaurants;
    callback listener;
    View selectView;
    int selectedIndex;


    public indicatorAdapter(Context restaurantContx, List<GameModel> restgaurants,callback listener) {
        RestaurantContx = restaurantContx;
        this.restgaurants = restgaurants;
        this.listener = listener;
        selectedIndex = 0;
    }


    @NonNull
    @Override
    public indicatorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(RestaurantContx);
        View itemView = inflater.inflate(R.layout.layout_indicator_item,null,false);
        return new indicatorViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final indicatorViewHolder holder, final int position) {
        GameModel restaurant = restgaurants.get(position);
        Resources resources = RestaurantContx.getResources();
        holder.gamename.setText(restaurant.getGame_name());
        Glide.with(holder.itemView.getContext()).load(IMG+restgaurants.get(position).getGameIcon()).into(holder.logo);

        if(selectedIndex == position){
            holder.bottomei.setVisibility(View.VISIBLE);
            holder.logo.setColorFilter(ContextCompat.getColor(holder.itemView.getContext(), R.color.app_color));
            holder.gamename.setTextColor(Color.parseColor("#0349FF"));
            selectedIndex = position;
            selectView = holder.clicker;
        }else {
            holder.bottomei.setVisibility(View.GONE);
            holder.gamename.setTextColor(Color.parseColor("#d3d3d3"));
            holder.logo.setColorFilter(ContextCompat.getColor(holder.itemView.getContext(), R.color.lightGrey));
        }

        holder.clicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(selectedIndex != position){
                    holder.bottomei.setVisibility(View.INVISIBLE);
                    holder.gamename.setTextColor(Color.parseColor("#d3d3d3"));
                    holder.logo.setColorFilter(ContextCompat.getColor(holder.itemView.getContext(), R.color.lightGrey));
                }
                selectView = holder.clicker;
                selectedIndex = position;
                listener.onTitleClicked(position);
            }
        });
    }

    public void setSelectedIndex(int position){
        selectedIndex = position;
    }

    public interface callback{
        void onTitleClicked(int position);
    }

    @Override
    public int getItemCount() {
        return restgaurants.size();
    }



    public class indicatorViewHolder extends RecyclerView.ViewHolder {
        //define sub views
        ImageView logo;
        TextView gamename;
        View clicker;
        View bottomei;
        public indicatorViewHolder(@NonNull View itemView) {
            super(itemView);
            //instantiate views
            logo = itemView.findViewById(R.id.logo);
            clicker = itemView.findViewById(R.id.clickr);
            gamename = itemView.findViewById(R.id.gamename);
            bottomei = itemView.findViewById(R.id.bottom_indicator);
        }
    }

}