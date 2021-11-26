package com.crictone.teamexpert.Model;

public class GameModel {
private String  gameIcon;
private String game_name;
private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public GameModel() {

    }

    public String getGameIcon() {
        return gameIcon;
    }

    public void setGameIcon(String gameIcon) {
        this.gameIcon = gameIcon;
    }

    public String getGame_name() {
        return game_name;
    }

    public void setGame_name(String game_name) {
        this.game_name = game_name;
    }

    public GameModel(String gameIcon, String game_name) {
        this.gameIcon = gameIcon;
        this.game_name = game_name;
    }
}
