package com.example.zhan.heathmanage.Main.EvaluteFragment.Beans;

public class Details {
    private int title_img;
    private String title;
    private String title_content;
    private String Measurements;//测量值
    private String appraisal;//评价
    private String content;
    private String Measurements_low;//测量值的所对应的上下限
    private String Measurements_high;

    public void setTitle_img(int title_img) {
        this.title_img = title_img;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle_content() {
        return title_content;
    }

    public void setTitle_content(String title_content) {
        this.title_content = title_content;
    }

    public String getMeasurements() {
        return Measurements;
    }

    public void setMeasurements(String measurements) {
        Measurements = measurements;
    }

    public String getAppraisal() {
        return appraisal;
    }

    public void setAppraisal(String appraisal) {
        this.appraisal = appraisal;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getMeasurements_low() {
        return Measurements_low;
    }

    public void setMeasurements_low(String measurements_low) {
        Measurements_low = measurements_low;
    }

    public String getMeasurements_high() {
        return Measurements_high;
    }

    public void setMeasurements_high(String measurements_high) {
        Measurements_high = measurements_high;
    }
}
