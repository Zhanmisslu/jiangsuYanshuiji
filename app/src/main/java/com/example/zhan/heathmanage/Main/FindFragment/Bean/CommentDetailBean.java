package com.example.zhan.heathmanage.Main.FindFragment.Bean;

import java.util.List;

/**
 * Created by moos on 2018/4/20.
 */

public class CommentDetailBean {
    private String id;//用户id
    private String nickName;
    private String userLogo;
    private String content;
    private String imgId;
    private int replyTotal;
    private String createDate;
    private String postingCommentId;//评论编号
    private List<ReplyDetailBean> replyList;

    public CommentDetailBean() {

    }

    public CommentDetailBean(String nickName, String content, String createDate,String userLogo) {
        this.nickName = nickName;
        this.content = content;
        this.createDate = createDate;
        this.userLogo=userLogo;
    }

    public String getPostingCommentId() {
        return postingCommentId;
    }

    public void setPostingCommentId(String postingCommentId) {
        this.postingCommentId = postingCommentId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public void setContent(String content) {
        this.content = content;
    }
    public String getContent() {
        return content;
    }

    public void setImgId(String imgId) {
        this.imgId = imgId;
    }
    public String getImgId() {
        return imgId;
    }

    public void setReplyTotal(int replyTotal) {
        this.replyTotal = replyTotal;
    }
    public int getReplyTotal() {
        return replyTotal;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }
    public String getCreateDate() {
        return createDate;
    }

    public void setReplyList(List<ReplyDetailBean> replyList) {
        this.replyList = replyList;
    }
    public List<ReplyDetailBean> getReplyList() {
        return replyList;
    }
}
