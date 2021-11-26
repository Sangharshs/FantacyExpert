package com.crictone.teamexpert.Model;

public class SliderModel {
    String banner;
    String url;
    String slider_category;



    public SliderModel() {

    }

    public SliderModel(String banner, String url, String slider_category) {
        this.banner = banner;
        this.url = url;
        this.slider_category = slider_category;
    }

    public String getBanner() {
        return banner;
    }

    public void setBanner(String banner) {
        this.banner = banner;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getSlider_category() {
        return slider_category;
    }

    public void setSlider_category(String slider_category) {
        this.slider_category = slider_category;
    }
}
