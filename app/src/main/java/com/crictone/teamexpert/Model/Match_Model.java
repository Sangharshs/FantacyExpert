package com.crictone.teamexpert.Model;

public class Match_Model {
    String player1_name;
    String player1_profile;
    String player2_name;
    String player2_profile;
    String match_name;
    String match_time;
    float  match_rating;
    String match_progress;
    String team_status;
    String start_date;
    String match_details;
    String team1_player;
    String team2_player;
    String team1_fullname;
    String team2_fullname;
    String matchCategory;
    String matchId;

    public Match_Model(String player1_name, String player1_profile, String player2_name, String player2_profile, String match_name, String match_time, float match_rating, String match_progress, String team_status, String start_date, String match_details, String team1_player, String team2_player, String team1_fullname, String team2_fullname, String matchCategory, String matchId) {
        this.player1_name = player1_name;
        this.player1_profile = player1_profile;
        this.player2_name = player2_name;
        this.player2_profile = player2_profile;
        this.match_name = match_name;
        this.match_time = match_time;
        this.match_rating = match_rating;
        this.match_progress = match_progress;
        this.team_status = team_status;
        this.start_date = start_date;
        this.match_details = match_details;
        this.team1_player = team1_player;
        this.team2_player = team2_player;
        this.team1_fullname = team1_fullname;
        this.team2_fullname = team2_fullname;
        this.matchCategory = matchCategory;
        this.matchId = matchId;
    }

    public Match_Model() {

    }

    public String getPlayer1_name() {
        return player1_name;
    }

    public void setPlayer1_name(String player1_name) {
        this.player1_name = player1_name;
    }

    public String getPlayer1_profile() {
        return player1_profile;
    }

    public void setPlayer1_profile(String player1_profile) {
        this.player1_profile = player1_profile;
    }

    public String getPlayer2_name() {
        return player2_name;
    }

    public void setPlayer2_name(String player2_name) {
        this.player2_name = player2_name;
    }

    public String getPlayer2_profile() {
        return player2_profile;
    }

    public void setPlayer2_profile(String player2_profile) {
        this.player2_profile = player2_profile;
    }

    public String getMatch_name() {
        return match_name;
    }

    public void setMatch_name(String match_name) {
        this.match_name = match_name;
    }

    public String getMatch_time() {
        return match_time;
    }

    public void setMatch_time(String match_time) {
        this.match_time = match_time;
    }

    public float getMatch_rating() {
        return match_rating;
    }

    public void setMatch_rating(float match_rating) {
        this.match_rating = match_rating;
    }

    public String getMatch_progress() {
        return match_progress;
    }

    public void setMatch_progress(String match_progress) {
        this.match_progress = match_progress;
    }

    public String getTeam_status() {
        return team_status;
    }

    public void setTeam_status(String team_status) {
        this.team_status = team_status;
    }

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public String getMatch_details() {
        return match_details;
    }

    public void setMatch_details(String match_details) {
        this.match_details = match_details;
    }

    public String getTeam1_player() {
        return team1_player;
    }

    public void setTeam1_player(String team1_player) {
        this.team1_player = team1_player;
    }

    public String getTeam2_player() {
        return team2_player;
    }

    public void setTeam2_player(String team2_player) {
        this.team2_player = team2_player;
    }

    public String getTeam1_fullname() {
        return team1_fullname;
    }

    public void setTeam1_fullname(String team1_fullname) {
        this.team1_fullname = team1_fullname;
    }

    public String getTeam2_fullname() {
        return team2_fullname;
    }

    public void setTeam2_fullname(String team2_fullname) {
        this.team2_fullname = team2_fullname;
    }

    public String getMatchCategory() {
        return matchCategory;
    }

    public void setMatchCategory(String matchCategory) {
        this.matchCategory = matchCategory;
    }

    public String getMatchId() {
        return matchId;
    }

    public void setMatchId(String matchId) {
        this.matchId = matchId;
    }
}
