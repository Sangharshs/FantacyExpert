package com.crictone.teamexpert.Adapters;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.crictone.teamexpert.MatchDetailsActivity;
import com.crictone.teamexpert.Model.Match_Model;
import com.crictone.teamexpert.R;
import com.crictone.teamexpert.util.Utility;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import cn.iwgang.countdownview.CountdownView;

import static com.crictone.teamexpert.util.ApiConfig.IMG;

public class ViewHolder extends RecyclerView.ViewHolder {
    ImageView team1_image, team2_image,dot_imageview;
    TextView team_name1, dummy;
    TextView team_name2;
    TextView match_name;
    CountDownTimer timer;
    TextView match_progress;
    TextView team_status;
    TextView text_days, text_hours,text_minutes,text_seconds;
    RatingBar ratingBar;
    CountdownView mCvCountdownView;

    public ViewHolder(@NonNull View itemView) {
        super(itemView);

     //      date = itemView.findViewById(R.id.date);
        mCvCountdownView = itemView.findViewById(R.id.mycountdown);

        team1_image = itemView.findViewById(R.id.team1_image);
        team2_image = itemView.findViewById(R.id.team2_image);

        match_name  = itemView.findViewById(R.id.match_name);

        dummy = itemView.findViewById(R.id.dummy);
        text_days      = itemView.findViewById(R.id.text_days);
        text_hours       = itemView.findViewById(R.id.text_hours);
        text_minutes     = itemView.findViewById(R.id.text_minute);
        text_seconds     = itemView.findViewById(R.id.text_seconds);

        dot_imageview = itemView.findViewById(R.id.image_dot);

        team_name1  = itemView.findViewById(R.id.team_name1);
        team_name2  = itemView.findViewById(R.id.team_name2);

        match_progress = itemView.findViewById(R.id.match_progress);
        team_status    = itemView.findViewById(R.id.team_status);

        ratingBar      = itemView.findViewById(R.id.ratingBar);

    }

    @SuppressLint("UseCompatLoadingForDrawables")
    public void onBind(Match_Model matchModel_list){
          if(matchModel_list.getPlayer1_profile() != null && !matchModel_list.getPlayer1_profile().isEmpty()) {
              Glide.with(team1_image.getContext()).load(IMG + matchModel_list.getPlayer1_profile()).into(team1_image);
          }
          if(matchModel_list.getPlayer1_profile() != null && !matchModel_list.getPlayer1_profile().isEmpty()) {
             Glide.with(team2_image.getContext()).load(IMG+matchModel_list.getPlayer2_profile()).into(team2_image);
          }
                team_name1.setText(matchModel_list.getPlayer1_name());
                team_name2.setText(matchModel_list.getPlayer2_name());

                match_name.setText(matchModel_list.getMatch_name());
                match_progress.setText(matchModel_list.getMatch_progress());

                dot_imageview.setVisibility(View.GONE);
                if(matchModel_list.getMatch_progress().equals("Covering")){
                    dot_imageview.setVisibility(View.VISIBLE);
                }
                if(matchModel_list.getMatch_progress().equals("Not Covering")){
                    match_progress.setTextColor(Color.RED);
                    dot_imageview.setImageResource(R.drawable.red_round);
                    dot_imageview.setVisibility(View.VISIBLE);
                }
                if(matchModel_list.getTeam_status().equals("Team Coming Soon")){
                     team_status.setTextColor(Color.RED);
                }
                if(matchModel_list.getTeam_status().equals("Pre-Toss Team is Published")){
                     team_status.setTextColor(itemView.getResources().getColor(R.color.orangeText));
                }
        if(matchModel_list.getTeam_status().equals("Final Team is Published")){
            team_status.setTextColor(itemView.getResources().getColor(R.color.green));
        }


                //dummy.setText(matchModel_list.getMatch_details());


                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String countDate = matchModel_list.getMatch_time();
                //"01-01-2021 00:00:00"
                Date now = new Date();
                try {
                    Date date = sdf.parse(String.valueOf(countDate));
                    long currentTime = now.getTime();
                    long newYearDate = date.getTime();
                    long countDownToNewYear = newYearDate - currentTime;
                     mCvCountdownView.start(countDownToNewYear);
                } catch (ParseException e) {
                    e.printStackTrace();
                }


                team_status.setText(matchModel_list.getTeam_status());

                ratingBar.setRating(matchModel_list.getMatch_rating());
                if(matchModel_list.getMatch_rating() == 0){
                    ratingBar.setVisibility(View.GONE);
                }
                //
                CountDownTimer unused = timer = new CountDownTimer(Utility.convertTimeInMillis("yyyy-MM-dd HH:mm:ss",matchModel_list.getMatch_time()) - System.currentTimeMillis(), 1000) {
                    public void onTick(long j) {
                        text_minutes.setText(Utility.updateTimeRemaining(j));
                    }

                    public void onFinish() {
                        text_minutes.setText(matchModel_list.getMatch_time());//replace(" ", "\n"));
                    }
                }.start();

                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                       if ((matchModel_list.getMatch_rating()) != 0) {
                           Intent intent = new Intent(v.getContext(), MatchDetailsActivity.class);
                           intent.putExtra("id", matchModel_list.getMatchId());
                           v.getContext().startActivity(intent);
                       }else {
                           LayoutInflater inflater = LayoutInflater.from(itemView.getContext());
                            View v1 = inflater.inflate(R.layout.item_click_dialog, null);
                            Button dismiss = v1.findViewById(R.id.dismiss);
                            final AlertDialog alertDialog = new AlertDialog.Builder(itemView.getContext())
                                    .setView(v1)
                                    .setCancelable(false)
                                    .create();
                            alertDialog.show();

                            dismiss.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    alertDialog.cancel();
                                }


                            });

                       }

                    }
                });
    }
}