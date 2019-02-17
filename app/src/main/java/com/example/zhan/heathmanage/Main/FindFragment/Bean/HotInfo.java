package com.example.zhan.heathmanage.Main.FindFragment.Bean;

public class HotInfo {
    private String Image;//头像
    private String nickName;//昵称
    private String pciture;//图片
    private String Content;//内容
    private String SupportNum;//赞的数量
    private String PostingId;
    private String Time;

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    public String getPostingId() {
        return PostingId;
    }

    public void setPostingId(String postingId) {
        PostingId = postingId;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getPciture() {
        return pciture;
    }

    public void setPciture(String pciture) {
        this.pciture = pciture;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

    public String getSupportNum() {
        return SupportNum;
    }

    public void setSupportNum(String supportNum) {
        SupportNum = supportNum;
    }
}
