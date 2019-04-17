package com.example.searchviewtest;

import android.media.Image;

public class TodayGame {

    String date,team1,team2,link;
    Image team1Image,team2Image;

    public TodayGame(String date, String team1, String team2, String link, Image team1Image, Image team2Image) {
        this.date = date;
        this.team1 = team1;
        this.team2 = team2;
        this.link = link;
        this.team1Image = team1Image;
        this.team2Image = team2Image;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTeam1() {
        return team1;
    }

    public void setTeam1(String team1) {
        this.team1 = team1;
    }

    public String getTeam2() {
        return team2;
    }

    public void setTeam2(String team2) {
        this.team2 = team2;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public Image getTeam1Image() {
        return team1Image;
    }

    public void setTeam1Image(Image team1Image) {
        this.team1Image = team1Image;
    }

    public Image getTeam2Image() {
        return team2Image;
    }

    public void setTeam2Image(Image team2Image) {
        this.team2Image = team2Image;
    }
}
