package com.sise.login_demo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    public EditText logidET,logpwdET;
    public Button logbtn;
    public String logidInfo, logpwdInfo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        logidET = (EditText) findViewById(R.id.login_id);
        logpwdET = (EditText) findViewById(R.id.login_pwd);
        logbtn = (Button) findViewById(R.id.logbtn);

        logbtn.setOnClickListener(this);


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
//                Toast.makeText(MainActivity.this, logidInfo, Toast.LENGTH_SHORT).show();
//                Toast.makeText(MainActivity.this, logpwdInfo, Toast.LENGTH_SHORT).show();

                sendRequestWithOkHttp("http://117.48.203.145/chkuser.php", new okhttp3.Callback(){
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
                        Log.d("MainActivity","出现异常");
                        e.printStackTrace();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(MainActivity.this, "获取信息失败", Toast.LENGTH_SHORT).show();

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
            default:
                break;
        }

    }

    private void sendRequestWithOkHttp(String address, okhttp3.Callback callback) {

        //*******
        OkHttpClient client = new OkHttpClient(); //创建 OkHttpClient实例
        RequestBody requestBody = new FormBody.Builder().add("userid", logidInfo).add("pwd", logpwdInfo).build();
        Request request = new Request.Builder().url("http://117.48.203.145/chkuser.php").post(requestBody).build();
        client.newCall(request).enqueue(callback);
        //*******


    }
    private void parseJSONWithGson(String jsonData) throws JSONException {

        Gson gson = new Gson();
        List<App> appList = gson.fromJson(jsonData, new TypeToken<List<App>>()
        {}.getType());
        for (App app : appList) {
            String useridrs = app.getuserid();
            String pwdrs = app.getPwd();
//            logidInfo = logidET.getText().toString();

            Log.d("MainActivity", "userideis" + useridrs);
            Log.d("MainActivity", "pwddis" + pwdrs);
//            reuserid1.setText(app.getuserid());
//            rePwd1.setText(app.getPwd());

            if(logidInfo.equals("")) {
                Toast.makeText(MainActivity.this, "请先输入手机号或账号ID", Toast.LENGTH_SHORT).show();
                logpwdET.setText("");
            } else if(logpwdInfo.equals("")) {
                Toast.makeText(MainActivity.this, "请输入密码", Toast.LENGTH_SHORT).show();
            } else {
                if(useridrs.equals("0") && pwdrs.equals("0")) {
                    Toast.makeText(MainActivity.this, "该用户不存在，请先注册~", Toast.LENGTH_SHORT).show();
                    logidET.setText("");
                    logpwdET.setText("");
                } else if(useridrs.equals("1") && pwdrs.equals("0")) {
                    Toast.makeText(MainActivity.this, "密码错误~", Toast.LENGTH_SHORT).show();
                    logpwdET.setText("");
                } else {
                    Toast.makeText(MainActivity.this, "用户："+logidInfo+" 登录成功，密码为："+pwdrs, Toast.LENGTH_SHORT).show();
                }
            }




        }

//********
    }


}
