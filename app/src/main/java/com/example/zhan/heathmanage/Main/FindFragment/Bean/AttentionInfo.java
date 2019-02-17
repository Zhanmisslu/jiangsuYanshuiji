package com.example.zhan.heathmanage.Main.FindFragment.Bean;

public class AttentionInfo {
    private String Image;//头像
    private String Time;//发布时间
    private String NickName;//昵称
    private String Pciture;//图片
    private String Content;//内容
    private String SupportNum;//赞的数量
    private String PostingId;//帖子编号
    private String CommentNum;//评论数量
    private String userId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
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

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    public String getNickName() {
        return NickName;
    }

    public void setNickName(String nickName) {
        NickName = nickName;
    }

    public String getPciture() {
        return Pciture;
    }

    public void setPciture(String pciture) {
        Pciture = pciture;
    }

    public String getCommentNum() {
        return CommentNum;
    }

    public void setCommentNum(String commentNum) {
        CommentNum = commentNum;
    }

    public String getPostingId() {
        return PostingId;
    }

    public void setPostingId(String postingId) {
        PostingId = postingId;
    }
}
