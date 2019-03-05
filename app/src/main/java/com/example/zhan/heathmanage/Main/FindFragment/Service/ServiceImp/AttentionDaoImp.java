package com.example.zhan.heathmanage.Main.FindFragment.Service.ServiceImp;

import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import com.example.zhan.heathmanage.Internet.Net;
import com.example.zhan.heathmanage.Internet.OKHttp;
import com.example.zhan.heathmanage.Main.FindFragment.Activity.AttentionActivity;
import com.example.zhan.heathmanage.Main.FindFragment.Activity.FanListActivity;
import com.example.zhan.heathmanage.Main.FindFragment.Activity.InvitationInfoActivity;
import com.example.zhan.heathmanage.Main.FindFragment.Adapter.FanListAdapter;
import com.example.zhan.heathmanage.Main.FindFragment.Bean.AttentionInfo;
import com.example.zhan.heathmanage.Main.FindFragment.Bean.CommentDetailBean;
import com.example.zhan.heathmanage.Main.FindFragment.Bean.HotInfo;
import com.example.zhan.heathmanage.Main.FindFragment.Bean.PeopleInfo;
import com.example.zhan.heathmanage.Main.FindFragment.Bean.ReplyDetailBean;
import com.example.zhan.heathmanage.Main.FindFragment.Fragment.AttentionFragment;
import com.example.zhan.heathmanage.Main.FindFragment.Service.AttentionDao;
import com.example.zhan.heathmanage.MyApplication;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class AttentionDaoImp implements AttentionDao {
    List<AttentionInfo> AttentionList;
    AttentionFragment attentionFragment;
    List<CommentDetailBean> commentDetailBeanList;
    InvitationInfoActivity invitationInfoActivity;
    CommentDetailBean commentDetailBean;
    ReplyDetailBean replyDetailBean;
    List<ReplyDetailBean> replyDetailBeanList;
    AttentionInfo attentionInfo;
    List<AttentionInfo>attentionInfoList;
    AttentionActivity attentionActivity;
    List<PeopleInfo> attentionList;//关注的好友列表
    PeopleInfo peopleInfo;
    List<PeopleInfo> fanList;//粉丝列表
    FanListActivity fanListActivity;

    public AttentionDaoImp(FanListActivity fanListActivity) {
        this.fanListActivity = fanListActivity;
    }

    public AttentionDaoImp(AttentionActivity attentionActivity) {
        this.attentionActivity = attentionActivity;
    }
    public AttentionDaoImp() {

    }
    public AttentionDaoImp(List<AttentionInfo> attentionList, AttentionFragment attentionFragment) {
        AttentionList = attentionList;
        this.attentionFragment = attentionFragment;
    }

    public AttentionDaoImp(List<CommentDetailBean> commentDetailBeanList, InvitationInfoActivity invitationInfoActivity) {
        this.commentDetailBeanList = commentDetailBeanList;
        this.invitationInfoActivity = invitationInfoActivity;
    }

    public AttentionDaoImp(List<AttentionInfo> attentionList) {
        AttentionList = attentionList;
    }

    public AttentionDaoImp(InvitationInfoActivity invitationInfoActivity) {
        this.invitationInfoActivity = invitationInfoActivity;
    }

    public AttentionDaoImp(AttentionFragment attentionFragment) {
        this.attentionFragment = attentionFragment;
    }

    @Override
    public void Support(String postingId) {
        String url= Net.UploadSupport+"?postingId="+postingId;

        OKHttp.sendOkhttpGetRequest(url, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Looper.prepare();
                Toast.makeText(MyApplication.getContext(),"╮(╯▽╰)╭连接不上了",Toast.LENGTH_SHORT).show();
                Looper.loop();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String ResponseData=response.body().string();
                try {
                    JSONObject jsonObject=new JSONObject(ResponseData);
                    JSONObject jsonObject1=jsonObject.getJSONObject("LikePosting");
                    String warning=jsonObject1.getString("warning");
                    if (warning.equals("点赞成功")){
                        Looper.prepare();
                        Toast.makeText(MyApplication.getContext(),"点赞成功",Toast.LENGTH_SHORT).show();
                        Looper.loop();
                    }else {
                        Looper.prepare();
                        Toast.makeText(MyApplication.getContext(),"点赞失败",Toast.LENGTH_SHORT).show();
                        Looper.loop();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void getCommnetList(String postingId) {

    }

    @Override
    public void getReplyList(String commentId) {
        replyDetailBeanList=new ArrayList<>();
        String url=Net.GetReplyList+"?commentId="+commentId;
        OKHttp.sendOkhttpGetRequest(url, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Looper.prepare();
                Toast.makeText(MyApplication.getContext(),"╮(╯▽╰)╭连接不上了",Toast.LENGTH_SHORT).show();
                Looper.loop();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String ResponseData=response.body().string();
                try {
                    JSONObject jsonObject=new JSONObject(ResponseData);
                    JSONArray jsonArray = jsonObject.getJSONArray("ShowReplyComment");
                    JSONObject jsonObject2 = jsonArray.getJSONObject(0);
                    //JSONObject jsonObject2=jsonObject.getJSONObject("ShowPostingComment");
                    String warning=jsonObject2.getString("warning");
                    if(warning.equals("1")){//没有评论
                        Looper.prepare();
                        Toast.makeText(MyApplication.getContext(),"暂无评论",Toast.LENGTH_SHORT).show();
                        Looper.loop();
                    }else {
                        // JSONArray jsonArray = jsonObject.getJSONArray("GetFollowUserList");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            replyDetailBean=new ReplyDetailBean();
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                            String replyCommentContent=jsonObject1.getString("replyCommentContent");
                            String userPhoto=jsonObject1.getString("userPhoto");
                            String replyCommentTime=jsonObject1.getString("replyCommentTime");
                            String userNickName=jsonObject1.getString("userNickName");
                            replyDetailBean.setContent(replyCommentContent);
                            replyDetailBean.setNickName(userNickName);
                            replyDetailBean.setUserLogo(userPhoto);
                            replyDetailBean.setCreateDate(replyCommentTime);

                            commentDetailBeanList.add(commentDetailBean);
                        }
                        commentDetailBean.setReplyList(replyDetailBeanList);
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

//    @Override
//    public void getCommnetList(String postingId) {
//        String url=Net.GetCommentList+"?postingId="+postingId;
//        commentDetailBeanList=new ArrayList<>();
//        OKHttp.sendOkhttpGetRequest(url, new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//                Looper.prepare();
//                Toast.makeText(MyApplication.getContext(),"╮(╯▽╰)╭连接不上了",Toast.LENGTH_SHORT).show();
//                Looper.loop();
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                String ResponseData=response.body().string();
//                try {
//                    JSONObject jsonObject=new JSONObject(ResponseData);
//                    JSONArray jsonArray = jsonObject.getJSONArray("ShowPostingComment");
//                    JSONObject jsonObject2 = jsonArray.getJSONObject(0);
//                    //JSONObject jsonObject2=jsonObject.getJSONObject("ShowPostingComment");
//                    String warning=jsonObject2.getString("warning");
//                    if(warning.equals("1")){//没有评论
//                        Looper.prepare();
//                        Toast.makeText(MyApplication.getContext(),"暂无评论",Toast.LENGTH_SHORT).show();
//                        Looper.loop();
//                    }else {
//                       // JSONArray jsonArray = jsonObject.getJSONArray("GetFollowUserList");
//                        for (int i = 0; i < jsonArray.length(); i++) {
//                            commentDetailBean=new CommentDetailBean();
//                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);
//                            String postingCommentContent=jsonObject1.getString("postingCommentContent");
//                            String userPhoto=jsonObject1.getString("userPhoto");
//                            String postingCommentTime=jsonObject1.getString("postingCommentTime");
//                            String userNickName=jsonObject1.getString("userNickName");
//                            String postingcommnetId=jsonObject1.getString("postingCommentId");
//                            commentDetailBean.setId(postingcommnetId);
//                            commentDetailBean.setContent(postingCommentContent);
//                            commentDetailBean.setNickName(userNickName);
//                            commentDetailBean.setUserLogo(userPhoto);
//                            commentDetailBean.setCreateDate(postingCommentTime);
//                            commentDetailBean.setReplyList(replyDetailBeanList);
//                            commentDetailBeanList.add(commentDetailBean);
//                        }
//                        invitationInfoActivity.initExpandableListView(commentDetailBeanList);
//                    }
//
//
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//        });
//    }
    @Override
    public void getAllCommnetList(String postingId) {
        String url=Net.GetAllCommentList+"?postingId="+postingId;
        commentDetailBeanList=new ArrayList<>();

        Log.v("zjc",url);
        OKHttp.sendOkhttpGetRequest(url, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Looper.prepare();
                Toast.makeText(MyApplication.getContext(),"╮(╯▽╰)╭连接不上了",Toast.LENGTH_SHORT).show();
                Looper.loop();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String ResponseData=response.body().string();
                try {
                    JSONObject jsonObject=new JSONObject(ResponseData);
                    //JSONObject jsonObject1=jsonObject.getJSONObject("ShowPostingComment");
                    JSONArray jsonArray3=jsonObject.getJSONArray("ShowPostingComment");
                    JSONObject jsonObject4 = jsonArray3.getJSONObject(0);
//                    String warning=jsonObject4.getString("warning");
                    JSONObject jsonObject5=jsonObject4.getJSONObject("comment");
                    String warning=jsonObject5.getString("warning");
                    if(warning.equals("1")){//没有评论
                        Looper.prepare();
                        Toast.makeText(MyApplication.getContext(),"暂无评论",Toast.LENGTH_SHORT).show();
                        invitationInfoActivity.initExpandableListView(commentDetailBeanList);
                        Looper.loop();
                    }else {
                        for (int i=0;i<jsonArray3.length();i++){
                            replyDetailBean=new ReplyDetailBean();
                            commentDetailBean=new CommentDetailBean();
                            replyDetailBeanList=new ArrayList<>();
                        JSONObject jsonObject1 = jsonArray3.getJSONObject(i);
                        JSONObject jsonObject2=jsonObject1.getJSONObject("comment");
                            if(jsonObject1.isNull("replyComment")){

                            }else {
                                JSONObject jsonObject3=jsonObject1.getJSONObject("replyComment");
                                String replyCommentContent=jsonObject3.getString("replyCommentContent");
                                String userPhoto=jsonObject3.getString("userPhoto");
                                String replyCommentTime=jsonObject3.getString("replyCommentTime");
                                String userNickName=jsonObject3.getString("userNickName");
                                String userid=jsonObject3.getString("userId");
                                String replyCommentId=jsonObject3.getString("replyCommentId");
                                replyDetailBean.setReplyCommentId(replyCommentId);
                                replyDetailBean.setAuthorName(userNickName);
                                replyDetailBean.setId(userid);
                                replyDetailBean.setContent(replyCommentContent);
                                replyDetailBean.setNickName(userNickName);
                                replyDetailBean.setUserLogo(userPhoto);
                                replyDetailBean.setCreateDate(replyCommentTime);
                                replyDetailBeanList.add(replyDetailBean);
                            }


                        String replypostingCommentContent=jsonObject2.getString("postingCommentContent");
                        String replyuserPhoto=jsonObject2.getString("userPhoto");
                        String replypostingCommentTime=jsonObject2.getString("postingCommentTime");
                        String replyuserNickName=jsonObject2.getString("userNickName");
                        String postingcommnetId=jsonObject2.getString("postingCommentId");
                        String replyuserid=jsonObject2.getString("userId");
                        commentDetailBean.setPostingCommentId(postingcommnetId);
                        commentDetailBean.setId(replyuserid);
                        commentDetailBean.setContent(replypostingCommentContent);
                        commentDetailBean.setNickName(replyuserNickName);
                        commentDetailBean.setUserLogo(replyuserPhoto);
                        commentDetailBean.setCreateDate(replypostingCommentTime);
                        commentDetailBean.setReplyList(replyDetailBeanList);
                        commentDetailBeanList.add(commentDetailBean);
                    }
                        invitationInfoActivity.initExpandableListView(commentDetailBeanList);
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void Comment(String uesrphone, String postingId, String postingComment) {
        String url=Net.Comment+"?postingId="+postingId+"&postingComment="+postingComment+"&userPhone="+uesrphone;
       Log.v("zjc",url);
        OKHttp.sendOkhttpGetRequest(url, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Looper.prepare();
                Toast.makeText(MyApplication.getContext(),"╮(╯▽╰)╭连接不上了",Toast.LENGTH_SHORT).show();
                Looper.loop();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String ResponseData=response.body().string();
                try {
                    JSONObject jsonObject=new JSONObject(ResponseData);
                    JSONObject jsonObject1=jsonObject.getJSONObject("CommentPosting");
                    String warning=jsonObject1.getString("warning");
                    if (warning.equals("评价成功")){
                        Looper.prepare();
                        Toast.makeText(MyApplication.getContext(),"评论成功",Toast.LENGTH_SHORT).show();
                        Looper.loop();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void Reply(String userId, String CommentId, String replyContent) {
        String url=Net.Reply+"?userId="+userId+"&commentId="+CommentId+"&replyContent="+replyContent;
        Log.v("zjc",url);
        OKHttp.sendOkhttpGetRequest(url, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Looper.prepare();
                Toast.makeText(MyApplication.getContext(),"╮(╯▽╰)╭连接不上了",Toast.LENGTH_SHORT).show();
                Looper.loop();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String ResponseData=response.body().string();
                try {
                    JSONObject jsonObject=new JSONObject(ResponseData);
                    JSONObject jsonObject1=jsonObject.getJSONObject("ReplyComment");
                    String warning=jsonObject1.getString("warning");
                    if (warning.equals("0")){
                        Looper.prepare();
                        Toast.makeText(MyApplication.getContext(),"回复成功",Toast.LENGTH_SHORT).show();
                        Looper.loop();
                    }else {
                        Looper.prepare();
                        Toast.makeText(MyApplication.getContext(),"回复失败",Toast.LENGTH_SHORT).show();
                        Looper.loop();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void getAttentionList(String userId) {
        String url=Net.GetAttentionList+"?userId="+userId;
        attentionInfoList=new ArrayList<>();
        Log.v("zjc",url);
        OKHttp.sendOkhttpGetRequest(url, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Looper.prepare();
                Toast.makeText(MyApplication.getContext(),"╮(╯▽╰)╭连接不上了",Toast.LENGTH_SHORT).show();
                Looper.loop();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String ResponseData=response.body().string();
                try {
                    JSONObject jsonObject=new JSONObject(ResponseData);
                    JSONArray jsonArray=jsonObject.getJSONArray("ShowPostingComment");
                    JSONObject jsonObject1=jsonArray.getJSONObject(0);
                    String warning=jsonObject1.getString("warning");
                    if (warning.equals("1")){
                        Looper.prepare();
                        Toast.makeText(MyApplication.getContext(),"暂无动态",Toast.LENGTH_SHORT).show();
                        Looper.loop();
                    }else {
                        for (int i=0;i<jsonArray.length();i++){
                            attentionInfo=new AttentionInfo();
                            jsonObject1=jsonArray.getJSONObject(i);
                            String postingId=jsonObject1.getString("postingId");
                            String nickname = jsonObject1.getString("userNickName");
                            String image = jsonObject1.getString("userPhoto");
                            String supportNum=jsonObject1.getString("postingLike");
                            String content=jsonObject1.getString("postingContent");
                            String picture=jsonObject1.getString("postingImg");
                            String time=jsonObject1.getString("postingTime");
                            String userId=jsonObject1.getString("userId");
                            String commentNum=jsonObject1.getString("commentNum");
                            attentionInfo.setUserId(userId);
                            attentionInfo.setTime(time);
                            attentionInfo.setPostingId(postingId);
                            attentionInfo.setImage(image);
                            attentionInfo.setNickName(nickname);
                            attentionInfo.setSupportNum(supportNum);
                            attentionInfo.setCommentNum(commentNum);
                            attentionInfo.setPciture(picture);
                            attentionInfo.setContent(content);
                            attentionInfoList.add(attentionInfo);
                        }
                        attentionFragment.InitAttentionList(attentionInfoList);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void CancelLikePosting(String postingId) {
        String url=Net.CancelLikePosting+"?postingId="+postingId;
        Log.v("zjc",url);
        OKHttp.sendOkhttpGetRequest(url, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Looper.prepare();
                Toast.makeText(MyApplication.getContext(),"╮(╯▽╰)╭连接不上了",Toast.LENGTH_SHORT).show();
                Looper.loop();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String ResponseData=response.body().string();
                try {
                    JSONObject jsonObject=new JSONObject(ResponseData);
                    JSONObject jsonObject1=jsonObject.getJSONObject("CancelLikePosting");
                    String warning=jsonObject1.getString("warning");
                    if (warning.equals("0")){
                        Looper.prepare();
                        Toast.makeText(MyApplication.getContext(),"取消赞成功",Toast.LENGTH_SHORT).show();
                        Looper.loop();
                    }else {
                        Looper.prepare();
                        Toast.makeText(MyApplication.getContext(),"取消赞失败",Toast.LENGTH_SHORT).show();
                        Looper.loop();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void GetFollowUserList(String userId) {
        String url=Net.GetFollowUserList+"?userId="+userId;
        attentionList=new ArrayList<>();
        Log.v("zjc",url);
        OKHttp.sendOkhttpGetRequest(url, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Looper.prepare();
                Toast.makeText(MyApplication.getContext(),"╮(╯▽╰)╭连接不上了",Toast.LENGTH_SHORT).show();
                Looper.loop();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String ResponseData=response.body().string();
                try {
                    JSONObject jsonObject=new JSONObject(ResponseData);
                    JSONArray jsonArray=jsonObject.getJSONArray("GetFollowUserList");
                    JSONObject jsonObject2=jsonArray.getJSONObject(0);
                    String warning=jsonObject2.getString("warning");
                    if(warning.equals("1")){//你暂时还没有关注
                        Looper.prepare();
                        Toast.makeText(MyApplication.getContext(),"暂无数据",Toast.LENGTH_SHORT).show();
                        attentionActivity.noData(attentionList);
                        Looper.loop();
                    }else {
                        //JSONArray jsonArray=jsonObject.getJSONArray("GetFollowUserList");
                        for (int i=0;i<jsonArray.length();i++){
                            peopleInfo=new PeopleInfo();
                            JSONObject jsonObject1=jsonArray.getJSONObject(i);
                            String nickname = jsonObject1.getString("friendNickName");
                            String image = jsonObject1.getString("friendPhoto");
                            String userid=jsonObject1.getString("friendId");
                            peopleInfo.setPeopleImage(image);
                            peopleInfo.setUserid(userid);
                            peopleInfo.setPeopleNickName(nickname);
                            attentionList.add(peopleInfo);
                        }
                        attentionActivity.InitList(attentionList);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void GetFollowedUserList(String userId) {
        String url=Net.GetFollowedUserList+"?userId="+userId;
        fanList=new ArrayList<>();
        OKHttp.sendOkhttpGetRequest(url, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Looper.prepare();
                Toast.makeText(MyApplication.getContext(),"╮(╯▽╰)╭连接不上了",Toast.LENGTH_SHORT).show();
                Looper.loop();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String ResponseData=response.body().string();
                try {
                    JSONObject jsonObject=new JSONObject(ResponseData);
                    JSONArray jsonArray=jsonObject.getJSONArray("GetFollowedUserList");
                    JSONObject jsonObject2=jsonArray.getJSONObject(0);
                    String warning=jsonObject2.getString("warning");
                    if(warning.equals("1")){//你暂时还没有关注
                        Looper.prepare();
                        Toast.makeText(MyApplication.getContext(),"暂无数据",Toast.LENGTH_SHORT).show();
                        fanListActivity.noData(fanList);
                        Looper.loop();
                    }else {
                        //JSONArray jsonArray=jsonObject.getJSONArray("GetFollowUserList");
                        for (int i=0;i<jsonArray.length();i++){
                            peopleInfo=new PeopleInfo();
                            JSONObject jsonObject1=jsonArray.getJSONObject(i);
                            String nickname = jsonObject1.getString("fansNickName");
                            String image = jsonObject1.getString("fansPhoto");
                            String userid=jsonObject1.getString("fansId");
                            peopleInfo.setPeopleImage(image);
                            peopleInfo.setUserid(userid);
                            peopleInfo.setPeopleNickName(nickname);
                            fanList.add(peopleInfo);
                        }
                        fanListActivity.InitFanList(fanList);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
