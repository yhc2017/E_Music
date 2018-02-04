package com.anddle.music.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.anddle.music.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    public EditText logidET,logpwdET;
    public Button logbtn, touristbtn;
    public String logidInfo, logpwdInfo;

//    public SharedPreferences pref;
//    public static SharedPreferences.Editor editor;
//    public static String returnedData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);
        logidET = (EditText) findViewById(R.id.login_id);
        logpwdET = (EditText) findViewById(R.id.login_pwd);
        logbtn = (Button) findViewById(R.id.logbtn);
        touristbtn = (Button) findViewById(R.id.tourist_btn);

        logbtn.setOnClickListener(this);
        touristbtn.setOnClickListener(this);


    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {

        switch (v.getId())  {
            case R.id.logbtn:
                logidInfo = logidET.getText().toString();
                logpwdInfo = logpwdET.getText().toString();
//                Toast.makeText(LoginActivity.this, logidInfo, Toast.LENGTH_SHORT).show();
//                Toast.makeText(LoginActivity.this, logpwdInfo, Toast.LENGTH_SHORT).show();

                sendRequestWithOkHttp("http://193.112.12.207/chkuser.php", new okhttp3.Callback(){
                    /**
                     * Called when the request could not be executed due to cancellation, a connectivity problem or
                     * timeout. Because networks can fail during an exchange, it is possible that the remote server
                     * accepted the request before the failure.
                     *
                     * @param call
                     * @param e
                     */
                    @Override
                    public void onFailure(Call call, IOException e) {
                        Log.d("LoginActivity","出现异常");
                        e.printStackTrace();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(LoginActivity.this, "获取信息失败", Toast.LENGTH_SHORT).show();

                            }
                        });


                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        final String responseData = response.body().string(); //得到返回具体内容

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    parseJSONWithGson(responseData);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });


                    }
                });

                break;
            case R.id.tourist_btn:
                SharedPreferences.Editor editor = getSharedPreferences("data", MODE_PRIVATE).edit();
                editor.putBoolean("registerState", false);
                editor.apply();
                Intent intent = new Intent(LoginActivity.this, MusicListActivity.class);
                startActivity(intent);
                Toast.makeText(LoginActivity.this, "游客模式", Toast.LENGTH_SHORT).show();
                finish();
            default:
                break;
        }

    }

    protected void sendRequestWithOkHttp(String address, okhttp3.Callback callback) {

        //*******
        OkHttpClient client = new OkHttpClient(); //创建 OkHttpClient实例
        RequestBody requestBody = new FormBody.Builder().add("userid", logidInfo).add("pwd", logpwdInfo).build();
        Request request = new Request.Builder().url("http://193.112.12.207/chkuser.php").post(requestBody).build();
        client.newCall(request).enqueue(callback);
        //*******


    }
    protected void parseJSONWithGson(String jsonData) throws JSONException {

        Gson gson = new Gson();
        List<App> appList = gson.fromJson(jsonData, new TypeToken<List<App>>()
        {}.getType());
        for (App app : appList) {
            String useridrs = app.getuserid();
            String pwdrs = app.getPwd();
//            logidInfo = logidET.getText().toString();

            Log.d("LoginActivity", "userideis" + useridrs);
            Log.d("LoginActivity", "pwddis" + pwdrs);
//            reuserid1.setText(app.getuserid());
//            rePwd1.setText(app.getPwd());

            if(logidInfo.equals("")) {
                Toast.makeText(LoginActivity.this, "请先输入手机号或账号ID", Toast.LENGTH_SHORT).show();
                logpwdET.setText("");
            } else if(logpwdInfo.equals("")) {
                Toast.makeText(LoginActivity.this, "请输入密码", Toast.LENGTH_SHORT).show();
            } else {
                if(useridrs.equals("0") && pwdrs.equals("0")) {
                    Toast.makeText(LoginActivity.this, "该用户不存在，请先注册~", Toast.LENGTH_SHORT).show();
                    logidET.setText("");
                    logpwdET.setText("");
                } else if(useridrs.equals("1") && pwdrs.equals("0")) {
                    Toast.makeText(LoginActivity.this, "密码错误~", Toast.LENGTH_SHORT).show();
                    logpwdET.setText("");
                } else {
                    SharedPreferences.Editor editor = getSharedPreferences("data", MODE_PRIVATE).edit();
                    editor.putBoolean("registerState", true);
                    editor.putString("userid", logidInfo);
                    editor.apply();
//                    SharedPreferences pref = getSharedPreferences("data", MODE_PRIVATE);
//                    boolean state = pref.getBoolean("registerState", false);
//                    String id = pref.getString("userid", "");
//                    Log.d("aa", "状态："+state);
//                    Log.d("aa", "登录id："+id);

                    Intent intent = new Intent(LoginActivity.this, MusicListActivity.class);
                    startActivity(intent);


//                    pref = PreferenceManager.getDefaultSharedPreferences(this);
//                    editor = pref.edit();
//                    editor.putBoolean("registerState", true);
//                    editor.putString("userid", "111");
//                    editor.apply();
                    Toast.makeText(LoginActivity.this, "用户："+logidInfo+" 登录成功，密码为："+pwdrs, Toast.LENGTH_SHORT).show();

                    finish();
//                    a(logidInfo);
                }
            }




        }

//********
    }

//    public void a(String logidInfo) {
//        Intent intent = new Intent();
//        intent.putExtra("registerState", logidInfo);
//        setResult(RESULT_OK, intent);
//        finish();
//    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.putExtra("registerState", logidInfo);
        setResult(RESULT_OK, intent);
        Toast.makeText(LoginActivity.this, "用户："+logidInfo+" 登录成功，密码为：", Toast.LENGTH_SHORT).show();
        finish();
    }
}
