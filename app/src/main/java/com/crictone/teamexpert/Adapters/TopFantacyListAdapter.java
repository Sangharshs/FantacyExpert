package com.crictone.teamexpert.Adapters;

import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.crictone.teamexpert.Model.TopFantacyAppsModel;
import com.crictone.teamexpert.R;

import java.util.ArrayList;
import java.util.List;

import static com.crictone.teamexpert.util.ApiConfig.IMG;

public class TopFantacyListAdapter extends RecyclerView.Adapter<TopFantacyListAdapter.Viewholder> {
    List<TopFantacyAppsModel> topFantacyAppLists = new ArrayList<>();
    public TopFantacyListAdapter(List<TopFantacyAppsModel> topFantacyAppLists) {
        this.topFantacyAppLists = topFantacyAppLists;
    }



    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.top_fantacy_app,null,false);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {
    holder.txtAppName.setText(topFantacyAppLists.get(position).getTxtAppName());
    holder.txtRefferalCode.setText(topFantacyAppLists.get(position).getTxtReferalCode());
    holder.withdrawType.setText(topFantacyAppLists.get(position).getTxtWithdrawType());
    holder.minimumWithdraw.setText(topFantacyAppLists.get(position).getMinimumWithdraw());
    holder.signUPbonus.setText(topFantacyAppLists.get(position).getSignUPbonus());

    holder.installButton.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String url = topFantacyAppLists.get(position).getInstallUrl().toString();
            if(!url.isEmpty()) {
                Uri uri = Uri.parse(url);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                v.getContext().startActivity(intent);
            }
        }
    });
        Glide.with(holder.appIcon.getContext()).load(IMG+topFantacyAppLists.get(position).getApp_icon()).into(holder.appIcon);

    }

    @Override
    public int getItemCount() {
        return topFantacyAppLists.size();
    }

    public  class Viewholder extends RecyclerView.ViewHolder {
        TextView signUPbonus,minimumWithdraw, withdrawType, txtRefferalCode,txtAppName;
        ImageView appIcon;
        Button installButton;
        public Viewholder(@NonNull View itemView) {
            super(itemView);

            signUPbonus = itemView.findViewById(R.id.txtSignupBonus);
            minimumWithdraw = itemView.findViewById(R.id.txtMinimumWithdraw);
            withdrawType = itemView.findViewById(R.id.txtWithdrawType);
            txtRefferalCode = itemView.findViewById(R.id.txtRefrealCode);
            txtAppName    = itemView.findViewById(R.id.txtAppName);
            appIcon = itemView.findViewById(R.id.imgIcone);

            installButton = itemView.findViewById(R.id.btnInstall);
        }
    }
}
