package com.example.zhan.heathmanage.Main.FindFragment.Activity;

import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bm.library.Info;
import com.bm.library.PhotoView;
import com.bumptech.glide.Glide;
import com.example.zhan.heathmanage.BasicsTools.BaseActivity;
import com.example.zhan.heathmanage.Main.FindFragment.Adapter.CommentExpandAdapter;
import com.example.zhan.heathmanage.Main.FindFragment.Bean.CommentDetailBean;
import com.example.zhan.heathmanage.Main.FindFragment.Bean.ReplyDetailBean;
import com.example.zhan.heathmanage.Main.FindFragment.Service.AttentionDao;
import com.example.zhan.heathmanage.Main.FindFragment.Service.HotDao;
import com.example.zhan.heathmanage.Main.FindFragment.Service.PeopleListDao;
import com.example.zhan.heathmanage.Main.FindFragment.Service.ServiceImp.AttentionDaoImp;
import com.example.zhan.heathmanage.Main.FindFragment.Service.ServiceImp.HotDaoImp;
import com.example.zhan.heathmanage.Main.FindFragment.Service.ServiceImp.PeopleListDaoImp;
import com.example.zhan.heathmanage.Main.FindFragment.View.CommentExpandableListView;
import com.example.zhan.heathmanage.MyApplication;
import com.example.zhan.heathmanage.R;
import com.makeramen.roundedimageview.RoundedImageView;
import com.sackcentury.shinebuttonlib.ShineButton;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static com.example.zhan.heathmanage.MyApplication.getContext;

public class InvitationInfoActivity extends BaseActivity {
    @BindView(R.id.InvitationInfo_nickname_tv)
    TextView InvitationInfo_nickname_tv;
    @BindView(R.id.InvitationInfo_content_tv)
    TextView InvitationInfo_content_tv;
    @BindView(R.id.InvitationInfo_image_riv)
    RoundedImageView InvitationInfo_image_riv;
    @BindView(R.id.InvitationInfosmall_picture_pv)
    PhotoView InvitationInfosmall_picture_pv;
    @BindView(R.id.InvitationInfo_supportnum_tv)
    TextView InvitationInfo_supportnum_tv;
    @BindView(R.id.InvitationInfo_postcontent_tv)
    TextView InvitationInfo_postcontent_tv;
    @BindView(R.id.InvitationInfo_CommentExpandableListView)
    CommentExpandableListView InvitationInfo_CommentExpandableListView;
    @BindView(R.id.InvitationInfo_time_tv)
    TextView InvitationInfo_time_tv;
    @BindView(R.id.Invitation_attention_bt)
    Button Invitation_attention_bt;
    @BindView(R.id.Invitation_haveattention_bt)
    Button Invitation_haveattention_bt;
    @BindView(R.id.InvitationInfo_support_sb)
    ShineButton InvitationInfo_support_sb;
    @BindView(R.id.InvitationInfobig_picture_pv)
    PhotoView InvitationInfobig_picture_pv;
    private CommentExpandAdapter commentExpandAdapter;
    private List<CommentDetailBean> commentsList = new ArrayList<>();
    private BottomSheetDialog dialog;
    private String nickname;
    @BindView(R.id.InvitationInfo_content_ll)LinearLayout InvitationInfo_content_ll;
    @BindView(R.id.nocommet_ll)LinearLayout nocommet_ll;
    AttentionDao attentionDao;
    PeopleListDao peopleListDao;
    HotDao hotDao;
    String image;
    String picture1;
    String content;
    String supportnum;
    String time;
    String postingId;
    Info mRectF;
    public static String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invitation_info);
        attentionDao = new AttentionDaoImp(this);
    //    commentExpandAdapter=new CommentExpandAdapter(this);
        // getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING);        setContentView(R.layout.activity_invitation_info);
        nickname = getIntent().getStringExtra("nickname");
        image = getIntent().getStringExtra("userimage");
        picture1 = getIntent().getStringExtra("picture1");
        content = getIntent().getStringExtra("content");
        supportnum = getIntent().getStringExtra("supportnum");
        time = getIntent().getStringExtra("time");
        postingId = getIntent().getStringExtra("postingId");
        userId = getIntent().getStringExtra("userId");
        peopleListDao = new PeopleListDaoImp();
        hotDao = new HotDaoImp(this);
        // attentionDao.getReplyList();
        attentionDao.getAllCommnetList(postingId);
        hotDao.GetFollowStatus(MyApplication.getUserId(), userId);
        InitView();
        InvitationInfo_support_sb.setOnCheckStateChangeListener(new ShineButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(View view, boolean checked) {
                if(checked==true) {// 已经点赞
                    attentionDao.Support(postingId);
                    supportnum= String.valueOf((Integer.valueOf(supportnum)+1));
                    InvitationInfo_supportnum_tv.setText(supportnum);
                }else {
                    attentionDao.CancelLikePosting(postingId);
                    supportnum= String.valueOf((Integer.valueOf(supportnum)-1));
                    InvitationInfo_supportnum_tv.setText(supportnum);
                }
            }
        });
        //设置不可以双指缩放移动放大等操作，跟普通的image一模一样,默认情况下就是disenable()状态
        InvitationInfosmall_picture_pv.disenable();
        InvitationInfosmall_picture_pv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InvitationInfosmall_picture_pv.setVisibility(View.GONE);
                InvitationInfobig_picture_pv.setVisibility(View.VISIBLE);
                InvitationInfo_content_ll.setVisibility(View.GONE);

                //获取img1的信息
                mRectF = InvitationInfosmall_picture_pv.getInfo();
                //让img2从img1的位置变换到他本身的位置
                InvitationInfobig_picture_pv.animaFrom(mRectF);
            }
        });
        InvitationInfobig_picture_pv.enable();
        InvitationInfobig_picture_pv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InvitationInfobig_picture_pv.animaTo(mRectF, new Runnable() {
                    @Override
                    public void run() {
                        InvitationInfobig_picture_pv.setVisibility(View.GONE);
                        InvitationInfosmall_picture_pv.setVisibility(View.VISIBLE);
                        InvitationInfo_content_ll.setVisibility(View.VISIBLE);
                    }
                });
            }
        });


    }
    @Override
    public void onBackPressed() {
        if (InvitationInfobig_picture_pv.getVisibility() == View.VISIBLE) {
            InvitationInfobig_picture_pv.animaTo(mRectF, new Runnable() {
                @Override
                public void run() {
                    InvitationInfobig_picture_pv.setVisibility(View.GONE);
                    InvitationInfosmall_picture_pv.setVisibility(View.VISIBLE);
                    InvitationInfo_content_ll.setVisibility(View.VISIBLE);
                }
            });
        } else {
            super.onBackPressed();
        }
    }

//    @OnClick(R.id.InvitationInfo_support_iv)
//    public void InvitationInfo_support_iv_Onclick() {
////        InvitationInfo_support_iv.setImageResource(R.drawable.support);
////        //   InvitationInfo_support_iv.setBackground();
////        attentionDao.Support(postingId);
////        if(InvitationInfo_support_iv.getDrawable().getConstantState().equals(getResources().getDrawable(R.drawable.support).getConstantState())){
////            attentionDao.CancelLikePosting(postingId);
////        }
////        if (InvitationInfo_support_iv.getDrawable().getConstantState().equals(getResources().getDrawable(R.drawable.support_nopress).getConstantState())){
////            attentionDao.Support(postingId);
////        }
////        if(InvitationInfo_support_iv.getTag().equals("nosupport")){
////            attentionDao.Support(postingId);
////            InvitationInfo_support_iv.setTag("support");
////            InvitationInfo_support_iv.setImageResource(R.drawable.support);
////
////        }else {
////            attentionDao.CancelLikePosting(postingId);
////            InvitationInfo_support_iv.setTag("nosupport");
////            InvitationInfo_support_iv.setImageResource(R.drawable.support_nopress);
////        }
//    }

//    public void Judge() {
//        if(InvitationInfo_support_iv.getDrawable().getConstantState().equals(getResources().getDrawable(R.drawable.support).getConstantState())){
//            attentionDao.CancelLikePosting(postingId);
//        }
//        if (InvitationInfo_support_iv.getDrawable().getConstantState().equals(getResources().getDrawable(R.drawable.support_nopress).getConstantState())){
//            attentionDao.Support(postingId);
//        }
//    }

    public void InitView() {
        InvitationInfo_nickname_tv.setText(nickname);
        InvitationInfo_supportnum_tv.setText(supportnum);
        InvitationInfo_content_tv.setText(content);
        InvitationInfo_time_tv.setText(time);
        Glide.with(getContext())
                .load(image)
                .asBitmap()
                .error(R.drawable.welcome)
                .into(InvitationInfo_image_riv);
        Glide.with(getContext())
                .load(picture1)
                .asBitmap()
                .error(R.drawable.welcome)
                .into(InvitationInfosmall_picture_pv);
        Glide.with(getContext())
                .load(picture1)
                .asBitmap()
                .error(R.drawable.welcome)
                .into(InvitationInfobig_picture_pv);

        //获取评论的列表
        //commentsList = generateTestData();
//        initExpandableListView(commentsList);

    }


    @OnClick(R.id.InvitationInfo_image_riv)
    public void InvitationInfo_image_riv_Onclick() {
        Intent intent = new Intent(InvitationInfoActivity.this, PersonalActivity.class);
        intent.putExtra("userId", userId);
        startActivity(intent);
    }


    @OnClick(R.id.invitationinfo_back_ib)
    public void invitationinfo_back_ib_Onclick() {
        finish();
    }

    @OnClick(R.id.InvitationInfo_postcontent_tv)
    public void InvitationInfo_postcontent_tv_Onclick() {
        showCommentDialog();
    }

    public void initExpandableListView(final List<CommentDetailBean> commentList, final int i) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(i==1){
                    nocommet_ll.setVisibility(View.VISIBLE);
                }else {
                    nocommet_ll.setVisibility(View.GONE);
                }
            }
        });

        commentsList = commentList;
        InvitationInfo_CommentExpandableListView.setGroupIndicator(null);
        //默认展开所有回复
        commentExpandAdapter = new CommentExpandAdapter(this, commentList);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                InvitationInfo_CommentExpandableListView.setAdapter(commentExpandAdapter);
                for (int i = 0; i < commentList.size(); i++) {
                    InvitationInfo_CommentExpandableListView.expandGroup(i);
                }
            }
        });

        InvitationInfo_CommentExpandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView expandableListView, View view, int groupPosition, long l) {
                boolean isExpanded = expandableListView.isGroupExpanded(groupPosition);
                Log.e("aa", "onGroupClick: 当前的评论id>>>" + commentList.get(groupPosition).getId());
//                if(isExpanded){
//                    expandableListView.collapseGroup(groupPosition);
//                }else {
//                    expandableListView.expandGroup(groupPosition, true);
//                }
                showReplyDialog(groupPosition);
                return true;
            }
        });

        InvitationInfo_CommentExpandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView expandableListView, View view, int groupPosition, int childPosition, long l) {
                Toast.makeText(InvitationInfoActivity.this, "点击了回复", Toast.LENGTH_SHORT).show();
                showChildReplyDialog(groupPosition, childPosition);
                return false;
            }
        });

        InvitationInfo_CommentExpandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
                //toast("展开第"+groupPosition+"个分组");

            }
        });

    }

    /**
     * by moos on 2018/04/20
     * func:弹出评论框
     */
    private void showCommentDialog() {

        dialog = new BottomSheetDialog(this, R.style.BottomSheetEdit);
        View commentView = LayoutInflater.from(this).inflate(R.layout.comment_dialog_layout, null);
        final EditText commentText = (EditText) commentView.findViewById(R.id.dialog_comment_et);
        final Button bt_comment = (Button) commentView.findViewById(R.id.dialog_comment_bt);
        dialog.setContentView(commentView);
        /**
         * 解决bsd显示不全的情况
         */
        //commentExpandAdapter = new CommentExpandAdapter(this);
        View parent = (View) commentView.getParent();
        BottomSheetBehavior behavior = BottomSheetBehavior.from(parent);
        commentView.measure(0, 0);
        behavior.setPeekHeight(commentView.getMeasuredHeight());

        bt_comment.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                String commentContent = commentText.getText().toString().trim();
                if (!TextUtils.isEmpty(commentContent)) {

                    //commentOnWork(commentContent);
                    dialog.dismiss();
                    CommentDetailBean detailBean = new CommentDetailBean(MyApplication.getUserNickName(), commentContent, "刚刚", MyApplication.getPhoto());
                    commentExpandAdapter.addTheCommentData(detailBean);
                    Toast.makeText(InvitationInfoActivity.this, "评论成功", Toast.LENGTH_SHORT).show();
                    //上传评论
                    nocommet_ll.setVisibility(View.GONE);
                    attentionDao.Comment(MyApplication.getUserPhone(), postingId, commentContent);
                } else {
                    Toast.makeText(InvitationInfoActivity.this, "评论内容不能为空", Toast.LENGTH_SHORT).show();
                }
            }
        });
        commentText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!TextUtils.isEmpty(charSequence) && charSequence.length() >= 1) {
                    bt_comment.setBackgroundColor(Color.parseColor("#FFB568"));
                    bt_comment.setEnabled(true);
                } else {
                    bt_comment.setBackgroundColor(Color.parseColor("#D8D8D8"));
                    bt_comment.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        dialog.show();
    }

    /**
     * by moos on 2018/04/20
     * func:弹出回复框
     */
    private void showReplyDialog(final int position) {
        dialog = new BottomSheetDialog(this, R.style.BottomSheetEdit);
        View commentView = LayoutInflater.from(this).inflate(R.layout.comment_dialog_layout, null);
        final EditText commentText = (EditText) commentView.findViewById(R.id.dialog_comment_et);
        final Button bt_comment = (Button) commentView.findViewById(R.id.dialog_comment_bt);
        commentText.setHint("回复 " + commentsList.get(position).getNickName() + " 的评论:");
        dialog.setContentView(commentView);
        bt_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String replyContent = commentText.getText().toString().trim();
                if (!TextUtils.isEmpty(replyContent)) {
//                    String anuthorname=commentsList.get(position).getReplyList().get(position).getAuthorName();
//                    String name=commentsList.get(position).getReplyList().get(position).getNickName();
//                    String user=commentsList.get(position).getNickName();
                    dialog.dismiss();
                    ReplyDetailBean detailBean = new ReplyDetailBean(MyApplication.getUserNickName(), commentsList.get(position).getNickName(), replyContent);
                    commentExpandAdapter.addTheReplyData(detailBean, position);
                    InvitationInfo_CommentExpandableListView.expandGroup(position);
                    attentionDao.Reply(MyApplication.getUserId(), commentsList.get(position).getPostingCommentId(), replyContent);
                    Toast.makeText(InvitationInfoActivity.this, "回复成功", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(InvitationInfoActivity.this, "回复内容不能为空", Toast.LENGTH_SHORT).show();
                }
            }
        });
        commentText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!TextUtils.isEmpty(charSequence) && charSequence.length() >= 1) {
                    bt_comment.setBackgroundColor(Color.parseColor("#FFB568"));
                    bt_comment.setEnabled(true);
                } else {
                    bt_comment.setBackgroundColor(Color.parseColor("#D8D8D8"));
                    bt_comment.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        dialog.show();
    }

    private void showChildReplyDialog(final int grounpostion, final int childposition) {
        dialog = new BottomSheetDialog(this, R.style.BottomSheetEdit);
        View commentView = LayoutInflater.from(this).inflate(R.layout.comment_dialog_layout, null);
        final EditText commentText = (EditText) commentView.findViewById(R.id.dialog_comment_et);
        final Button bt_comment = (Button) commentView.findViewById(R.id.dialog_comment_bt);
        commentText.setHint("回复 " + commentsList.get(grounpostion).getReplyList().get(childposition).getNickName() + " 的评论:");
        final String name = commentsList.get(grounpostion).getReplyList().get(childposition).getNickName();
//        String anuthorname = commentsList.get(grounpostion).getReplyList().get(childposition).getAuthorName();
//        //String name=commentsList.get(position).getReplyList().get(position).getNickName();
//        String user = commentsList.get(grounpostion).getNickName();

        dialog.setContentView(commentView);
        bt_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String replyContent = commentText.getText().toString().trim();
                if (!TextUtils.isEmpty(replyContent)) {

                    dialog.dismiss();
                    ReplyDetailBean detailBean = new ReplyDetailBean(MyApplication.getUserNickName(), name, replyContent);
                    commentExpandAdapter.addTheReplyData(detailBean, grounpostion);
                    InvitationInfo_CommentExpandableListView.expandGroup(grounpostion);
                    //attentionDao.Reply(commentsList.get(grounpostion).getReplyList().get(childposition).getId(),commentsList.get(grounpostion).getReplyList().get(childposition).getReplyCommentId(),replyContent);

                    //  Toast.makeText(InvitationInfoActivity.this, "回复成功", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(InvitationInfoActivity.this, "回复内容不能为空", Toast.LENGTH_SHORT).show();
                }
            }
        });
        commentText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!TextUtils.isEmpty(charSequence) && charSequence.length() >= 1) {
                    bt_comment.setBackgroundColor(Color.parseColor("#FFB568"));
                    bt_comment.setEnabled(true);
                } else {
                    bt_comment.setBackgroundColor(Color.parseColor("#D8D8D8"));
                    bt_comment.setEnabled(false);

                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        dialog.show();
    }

    @OnClick(R.id.Invitation_attention_bt)
    public void Invitation_attention_bt_Onclick() {
        if (MyApplication.getUserId().equals(userId)) {
            Invitation_attention_bt.setVisibility(View.GONE);
        } else {
            peopleListDao.attention(MyApplication.getUserId(), userId);
            Invitation_attention_bt.setVisibility(View.GONE);
            Invitation_haveattention_bt.setVisibility(View.VISIBLE);
        }

    }

    @OnClick(R.id.Invitation_haveattention_bt)
    public void Invitation_haveattention_bt_Onclick() {
        if (MyApplication.getUserId().equals(userId)) {
            Invitation_attention_bt.setVisibility(View.GONE);
        } else {
            peopleListDao.RemoveConcern(MyApplication.getUserId(), userId);
            Invitation_attention_bt.setVisibility(View.VISIBLE);
            Invitation_haveattention_bt.setVisibility(View.GONE);
        }
    }

    //相互关注
    public void Concern() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Invitation_haveattention_bt.setVisibility(View.VISIBLE);
                Invitation_attention_bt.setVisibility(View.GONE);
            }
        });

    }

    //没有关注
    public void NoConcern() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Invitation_attention_bt.setVisibility(View.VISIBLE);
                Invitation_haveattention_bt.setVisibility(View.GONE);
            }
        });

    }

    public void Self() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Invitation_attention_bt.setVisibility(View.GONE);
                Invitation_haveattention_bt.setVisibility(View.GONE);
            }
        });
    }
}
