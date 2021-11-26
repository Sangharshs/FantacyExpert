package com.crictone.teamexpert.Adapters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.crictone.teamexpert.Model.NewsModel;
import com.crictone.teamexpert.NewsActivity;
import com.crictone.teamexpert.R;

import java.util.List;

import static com.crictone.teamexpert.util.ApiConfig.IMG;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.Viewholder> {

    List<NewsModel> news_Model_list;

    public NewsAdapter(List<NewsModel> news_Model_list) {
        this.news_Model_list = news_Model_list;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_item,parent,false);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {
          holder.newsContent.setText(news_Model_list.get(position).getNewsContent());
          holder.newsHead.setText(news_Model_list.get(position).getNewsHead());
          Glide.with(holder.itemView.getContext()).load(IMG+news_Model_list.get(position).getNewsImg()).into(holder.newsImg);

          holder.itemView.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  Intent intent = new Intent(v.getContext(), NewsActivity.class);
                  intent.putExtra("content",news_Model_list.get(position).getNewsContent());
                  v.getContext().startActivity(intent);
              }
          });
    }

    @Override
    public int getItemCount() {
        return news_Model_list.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {
        ImageView newsImg;
        TextView newsHead;
        TextView newsContent;
        public Viewholder(@NonNull View itemView) {
            super(itemView);
            newsImg = itemView.findViewById(R.id.newsImg);
            newsHead = itemView.findViewById(R.id.newsHead);
            newsContent = itemView.findViewById(R.id.newsContent);
        }
    }
}
