package com.crictone.teamexpert.Model;

public class SliderItem {

    String banner;
    String url;
    String slider_category;

    public SliderItem(String slider_category) {
        this.slider_category = slider_category;
    }

    public String getSlider_category() {
        return slider_category;
    }

    public void setSlider_category(String slider_category) {
        this.slider_category = slider_category;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public SliderItem(String banner, String url) {
        this.banner = banner;
        this.url = url;
    }

    public SliderItem() {

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


}
