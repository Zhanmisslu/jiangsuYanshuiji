package com.example.zhan.heathmanage.Main.Menu;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zhan.heathmanage.BasicsTools.BaseActivity;
import com.example.zhan.heathmanage.Main.EvaluteFragment.Server.server.EmergencyContactDao;
import com.example.zhan.heathmanage.Main.EvaluteFragment.Server.serverImp.EmergencyContactDaoImp;
import com.example.zhan.heathmanage.MyApplication;
import com.example.zhan.heathmanage.R;

import butterknife.BindView;
import butterknife.OnClick;

public class EmergencyContactActivity extends BaseActivity {
    @BindView(R.id.goto_addresslist_ll)
    LinearLayout goto_addresslist_ll;
    @BindView(R.id.emergency_phone_et)
    EditText emergency_phone_et;
    @BindView(R.id.emergency_user_et)
    EditText emergency_user_et;
    @BindView(R.id.emergency_contactaffirm_ll)LinearLayout emergency_contactaffirm_ll;
    @BindView(R.id.emergency_contact_ll)LinearLayout emergency_contact_ll;
    @BindView(R.id.emergency_user_tv)TextView emergency_user_tv;
    @BindView(R.id.emergency_phone_tv)TextView emergency_phone_tv;
    private static final int PICK_CONTACT = 1;
    private static final int PERMISSIONS_REQUEST_READ_CONTACTS = 2;
    private Intent mIntent;
    String name = "";
    String phoneNumber = "";
    EmergencyContactDao emergencyContactDao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emergency_contact);
        emergencyContactDao=new EmergencyContactDaoImp(this);
    }

    @OnClick(R.id.goto_addresslist_bt)
    public void goto_addresslist_bt_OnClick() {
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


        Uri contactUri = data.getData();
        Cursor cursor = getContentResolver().query(contactUri, null, null, null, null);
        if (cursor.moveToFirst()) {
            name = cursor
                    .getString(cursor
                            .getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
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

            emergency_user_et.setText(name);
            emergency_phone_et.setText(phoneNumber);



        }
    }

    @OnClick(R.id.affirm__bt)
    public void affirm__bt_OnClick() {
        emergency_contact_ll.setVisibility(View.GONE);
        emergency_contactaffirm_ll.setVisibility(View.VISIBLE);
        emergency_user_tv.setText(name);
        emergency_phone_tv.setText(phoneNumber);
       // emergencyContactDao.ChangeUserEmergency(emergency_phone_et.getText().toString(),emergency_user_et.getText().toString());
    }
    public void callback(){
        Toast.makeText(MyApplication.getContext(),"修改成功",Toast.LENGTH_LONG).show();

    };


}
