package com.example.zhan.heathmanage.Main.FindFragment.Service;

public interface AttentionDao {
    public void Support(String postingId);
    public void getCommnetList(String postingId);
    public void getAllCommnetList(String postingId);
    public void getReplyList(String commentId);
    public  void Comment(String uesrphone,String postingId,String postingComment);
    public  void Reply(String userId,String CommentId,String replyContent);
    public void getAttentionList(String userId);
    public void CancelLikePosting(String postingId);
    public void GetFollowUserList(String userId);
}
