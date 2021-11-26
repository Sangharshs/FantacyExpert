package com.crictone.teamexpert.Model;

public class TopFantacyAppsModel {
    String signUPbonus;
    String minimumWithdraw;
    String txtWithdrawType;
    String txtReferalCode;
    String app_icon;
    String txtAppName;
    String app_name;

    public String getApp_name() {
        return app_name;
    }

    public void setApp_name(String app_name) {
        this.app_name = app_name;
    }

    public TopFantacyAppsModel(String app_name) {
        this.app_name = app_name;
    }

    public String getInstallUrl() {
        return installUrl;
    }

    public void setInstallUrl(String installUrl) {
        this.installUrl = installUrl;
    }

    public TopFantacyAppsModel() {
        this.installUrl = installUrl;
    }

    String installUrl;

    public TopFantacyAppsModel(String signUPbonus, String minimumWithdraw, String txtWithdrawType, String txtReferalCode, String app_icon, String txtAppName) {
        this.signUPbonus = signUPbonus;
        this.minimumWithdraw = minimumWithdraw;
        this.txtWithdrawType = txtWithdrawType;
        this.txtReferalCode = txtReferalCode;
        this.app_icon = app_icon;
        this.txtAppName = txtAppName;
    }

    public String getSignUPbonus() {
        return signUPbonus;
    }

    public void setSignUPbonus(String signUPbonus) {
        this.signUPbonus = signUPbonus;
    }

    public String getMinimumWithdraw() {
        return minimumWithdraw;
    }

    public void setMinimumWithdraw(String minimumWithdraw) {
        this.minimumWithdraw = minimumWithdraw;
    }

    public String getTxtWithdrawType() {
        return txtWithdrawType;
    }

    public void setTxtWithdrawType(String txtWithdrawType) {
        this.txtWithdrawType = txtWithdrawType;
    }

    public String getTxtReferalCode() {
        return txtReferalCode;
    }

    public void setTxtReferalCode(String txtReferalCode) {
        this.txtReferalCode = txtReferalCode;
    }

    public String getApp_icon() {
        return app_icon;
    }

    public void setApp_icon(String app_icon) {
        this.app_icon = app_icon;
    }

    public String getTxtAppName() {
        return txtAppName;
    }

    public void setTxtAppName(String txtAppName) {
        this.txtAppName = txtAppName;
    }
}
