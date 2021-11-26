package com.crictone.teamexpert.Model;

public class TeamPreviewModel {

    private String id;
    private String team_preview_image;
    private String team_preview_text;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTeam_preview_image() {
        return team_preview_image;
    }

    public void setTeam_preview_image(String team_preview_image) {
        this.team_preview_image = team_preview_image;
    }

    public String getTeam_preview_text() {
        return team_preview_text;
    }

    public void setTeam_preview_text(String team_preview_text) {
        this.team_preview_text = team_preview_text;
    }

    public TeamPreviewModel() {
        this.id = id;
        this.team_preview_image = team_preview_image;
        this.team_preview_text = team_preview_text;
    }
}
