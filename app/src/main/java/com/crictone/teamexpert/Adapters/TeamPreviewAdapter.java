package com.crictone.teamexpert.Adapters;

import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.CookieManager;
import android.webkit.WebView;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.crictone.teamexpert.Model.TeamPreviewModel;
import com.crictone.teamexpert.R;

import java.util.List;

import static android.webkit.WebSettings.MIXED_CONTENT_ALWAYS_ALLOW;
import static com.crictone.teamexpert.util.ApiConfig.IMG;

public class TeamPreviewAdapter extends RecyclerView.Adapter<TeamPreviewAdapter.Viewholder>{

    List<TeamPreviewModel> team_prevew_list;

    public TeamPreviewAdapter(List<TeamPreviewModel> team_prevew_list) {
        this.team_prevew_list = team_prevew_list;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.team_preview_item,parent,false);
        return new Viewholder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {
        Glide.with(holder.itemView.getContext()).load(IMG + team_prevew_list.get(position).getTeam_preview_image()).into(holder.image_preview);
            if(!team_prevew_list.get(position).getTeam_preview_text().isEmpty()) {
            holder.webView.loadDataWithBaseURL((String)
                    null, "<html><head><style type=\"text/css\">@font-face {font-family: MyFont;}body {font-family: MyFont;font-size: medium;text-align: justify;}</style></head><body>"
                    + team_prevew_list.get(position).getTeam_preview_text() +
                    "</body></html>", "text/html", "UTF-8", (String) null);
                holder.webView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        return true;
                    }
                });
                holder.webView.setLongClickable(false);

            }
        //holder.post_data.loadDataWithBaseURL();setText(team_prevew_list.get(position).getTeam_preview_text());
    }

    @Override
    public int getItemCount() {
        return team_prevew_list.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder{
        WebView webView;
        ImageView image_preview;

        public Viewholder(@NonNull View itemView) {
            super(itemView);

            webView = itemView.findViewById(R.id.team_preview_text);
            image_preview = itemView.findViewById(R.id.team_preview_image);

            webView.getSettings().setJavaScriptEnabled(true);
            webView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
            webView.requestFocus();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

                webView.getSettings().setMixedContentMode(MIXED_CONTENT_ALWAYS_ALLOW);
                CookieManager.getInstance().setAcceptThirdPartyCookies(webView, true);

            }
        }
    }
}
