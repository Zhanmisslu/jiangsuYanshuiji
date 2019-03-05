package com.example.zhan.heathmanage.Main.Menu;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zhan.heathmanage.BasicsTools.BaseActivity;
import com.example.zhan.heathmanage.Main.EvaluteFragment.Server.server.EmergencyContactDao;
import com.example.zhan.heathmanage.Main.EvaluteFragment.Server.serverImp.EmergencyContactDaoImp;
import com.example.zhan.heathmanage.MyApplication;
import com.example.zhan.heathmanage.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.OnClick;

public class EmergencyContactActivity extends BaseActivity {
    @BindView(R.id.goto_addresslist_ll)
    LinearLayout goto_addresslist_ll;
    @BindView(R.id.emergency_phone_et)
    EditText emergency_phone_et;
    @BindView(R.id.emergency_user_et)
    EditText emergency_user_et;
    @BindView(R.id.affirm__bt)
    Button affirm__bt;
    @BindView(R.id.emergency_contactaffirm_ll)
    LinearLayout emergency_contactaffirm_ll;
    @BindView(R.id.emergency_contact_ll)
    LinearLayout emergency_contact_ll;
    @BindView(R.id.emergency_user_tv)
    TextView emergency_user_tv;
    @BindView(R.id.emergency_phone_tv)
    TextView emergency_phone_tv;
    private static final int PICK_CONTACT = 1;
    private static final int PERMISSIONS_REQUEST_READ_CONTACTS = 2;
    private Intent mIntent;
    String name = "";
    String phoneNumber = "";
    EmergencyContactDao emergencyContactDao;
    private SharedPreferences pref;
    SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emergency_contact);
        emergencyContactDao = new EmergencyContactDaoImp(this);
        pref=getSharedPreferences("UserList", MODE_PRIVATE);
        editor = pref.edit();
        EditTextLinsten();
        if (!MyApplication.getEmergencyPhone().equals("null")) {
            emergency_contact_ll.setVisibility(View.GONE);
            emergency_contactaffirm_ll.setVisibility(View.VISIBLE);
            emergency_phone_tv.setText(MyApplication.getEmergencyPhone());
            emergency_user_tv.setText(MyApplication.getEmergencyName());
        }
    }

    @OnClick(R.id.goto_addresslist_bt)
    public void goto_addresslist_bt_OnClick() {
        //跳转到系统的通讯录列表
        Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
        startActivityForResult(intent, PICK_CONTACT);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case PICK_CONTACT:
                mIntent = data;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
                    //申请授权，第一个参数为要申请用户授权的权限；第二个参数为requestCode 必须大于等于0，主要用于回调的时候检测，匹配特定的onRequestPermissionsResult。
                    //可以从方法名requestPermissions以及第二个参数看出，是支持一次性申请多个权限的，系统会通过对话框逐一询问用户是否授权。
                    requestPermissions(new String[]{Manifest.permission.READ_CONTACTS}, PERMISSIONS_REQUEST_READ_CONTACTS);

                } else {
                    //如果该版本低于6.0，或者该权限已被授予，它则可以继续读取联系人。
                    getContacts(data);
                }
                break;
            default:
                super.onActivityResult(requestCode, resultCode, data);
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PERMISSIONS_REQUEST_READ_CONTACTS) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // 用户成功授予权限
                getContacts(mIntent);
            } else {
                Toast.makeText(this, "你拒绝了此应用对读取联系人权限的申请！", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void getContacts(Intent data) {
        if (data == null) {
            return;
        }

        Uri contactData = data.getData();
        if (contactData == null) {
            return;
        }

    //目标联系人的uri
        Uri contactUri = data.getData();
        //解读内容
        Cursor cursor = getContentResolver().query(contactUri, null, null, null, null);
       //判断查询结果是否为空
        if (cursor.moveToFirst()) {
            //返回指定列的数据 姓名
            name = cursor
                    .getString(cursor
                            .getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
            //返回指定列的数据 有没有电话号码
            String hasPhone = cursor
                    .getString(cursor
                            .getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));
            String id = cursor.getString(cursor
                    .getColumnIndex(ContactsContract.Contacts._ID));
            if (hasPhone.equalsIgnoreCase("1")) {
                hasPhone = "true";
            } else {
                hasPhone = "false";
            }
            if (Boolean.parseBoolean(hasPhone)) {
                Cursor phones = getContentResolver()
                        .query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                                null,
                                ContactsContract.CommonDataKinds.Phone.CONTACT_ID
                                        + " = " + id, null, null);
                while (phones.moveToNext()) {
                    phoneNumber = phones
                            .getString(phones
                                    .getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                }
                phones.close();
            }
            cursor.close();
            //删除联系人号码中的空格
            if (phoneNumber.length()>11){
                phoneNumber = phoneNumber.replace(" ","") ;
                emergency_phone_et.setText(phoneNumber);
            }else {
                emergency_phone_et.setText(phoneNumber);
            }
            emergency_user_et.setText(name);
        }
    }

    @OnClick(R.id.affirm__bt)
    public void affirm__bt_OnClick() {
        String limitEx="[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
        Pattern pattern = Pattern.compile(limitEx);
        Matcher m = pattern.matcher(emergency_phone_et.getText());
        Matcher ma = pattern.matcher(emergency_user_et.getText());
        if (m.find()&&ma.find()){
            Toast.makeText(getApplicationContext(),"不可以用特殊符号哦",Toast.LENGTH_LONG).show();
        }else {
            emergencyContactDao.ChangeUserEmergency(MyApplication.getUserPhone(), emergency_phone_et.getText().toString(), emergency_user_et.getText().toString());
        }
    }

    public void callback(final String EmergencyPhone, final String EmergencyName) {
        Handler handler = new Handler(getMainLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {
                editor.putString("EmergencyPhone",EmergencyPhone);
                editor.putString("EmergencyName",EmergencyName);
                MyApplication.setEmergencyPhone(EmergencyPhone);
                MyApplication.setEmergencyName(EmergencyName);
                editor.commit();
                emergency_contact_ll.setVisibility(View.GONE);
                emergency_contactaffirm_ll.setVisibility(View.VISIBLE);
                emergency_user_tv.setText(name);
                emergency_phone_tv.setText(phoneNumber);
                Toast.makeText(MyApplication.getContext(), "修改成功", Toast.LENGTH_LONG).show();

            }
        });
    }

    ;

    public void EditTextLinsten() {
        emergency_phone_et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //charSequence  输入框中改变后的字符串信息
                //i 输入框中改变后的字符串的起始位置
                //i1 输入框中改变前的字符串的位置 默认为0
                //i2 输入框中改变后的一共输入字符串的数量
                if (i2 + i == 11) {
                    affirm__bt.setEnabled(true);
                    affirm__bt.setBackgroundColor(Color.parseColor("#15bdff"));
                    affirm__bt.setTextColor(Color.parseColor("#f5f5f5"));
                } else {
                    affirm__bt.setEnabled(false);
                    affirm__bt.setBackgroundColor(Color.parseColor("#dcdcdc"));
                    affirm__bt.setTextColor(Color.parseColor("#bebebe"));
                }
                if (i2 == 0) {
                    emergency_user_et.setText("");
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    @OnClick(R.id.contact_finsh)
    public void contact_finsh_OnClick(){
        finish();
    }
    @OnClick(R.id.emergency_update)
    public void emergency_update_OnClick(){
        emergency_contact_ll.setVisibility(View.VISIBLE);
        emergency_contactaffirm_ll.setVisibility(View.GONE);
    }
}
