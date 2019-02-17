package com.example.zhan.heathmanage.Main.FindFragment.Bean;

/**
 * Created by moos on 2018/4/20.
 */

public class ReplyDetailBean {
    private String nickName;
    private String authorName;
    private String userLogo;
    private String id;
    private String commentId;
    private String content;
    private String status;
    private String createDate;
    private String replyCommentId;
    public ReplyDetailBean(String nickName, String content) {
        this.nickName = nickName;
        this.content = content;
    }

    public ReplyDetailBean() {

    }

    public ReplyDetailBean(String nickName, String authorName, String content) {
        this.nickName = nickName;
        this.authorName = authorName;
        this.content = content;
    }

    public String getReplyCommentId() {
        return replyCommentId;
    }

    public void setReplyCommentId(String replyCommentId) {
        this.replyCommentId = replyCommentId;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }
    public String getNickName() {
        return nickName;
    }

    public void setUserLogo(String userLogo) {
        this.userLogo = userLogo;
    }
    public String getUserLogo() {
        return userLogo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setCommentId(String commentId) {
        this.commentId = commentId;
    }
    public String getCommentId() {
        return commentId;
    }

    public void setContent(String content) {
        this.content = content;
    }
    public String getContent() {
        return content;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    public String getStatus() {
        return status;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }
    public String getCreateDate() {
        return createDate;
    }

}
