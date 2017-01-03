package com.example.guanzhuli.icart;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.guanzhuli.icart.data.SPManipulation;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

// http://rjtmobile.com/ansari/shopingcart/androidapp/shop_login.php?mobile=123456&password=ansari
public class SignInActivity extends AppCompatActivity {

    private static final String LOGIN_URL = "http://rjtmobile.com/ansari/shopingcart/androidapp/shop_login.php?";
    Button mButtonSignIn;
    TextView mTextUsername, mTextPassword, mTextSignUp;
    private RequestQueue mRequestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        mRequestQueue = Volley.newRequestQueue(this);
        mTextUsername = (TextView) findViewById(R.id.Sign_in_username);
        mTextPassword = (TextView) findViewById(R.id.sign_in_password);
        mButtonSignIn = (Button) findViewById(R.id.button_sign_in);
        mButtonSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String mobile = mTextUsername.getText().toString();
                String pwd = mTextPassword.getText().toString();
                String Url = LOGIN_URL + "mobile=" + mobile + "&password=" + pwd;
                StringRequest stringRequest = new StringRequest(Request.Method.GET, Url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String s) {
                                try {
                                    JSONObject jsonObject = new JSONObject(s);
                                    String msg = jsonObject.getString("msg");
                                    if (msg.contains("failure")) {
                                        mTextPassword.setText("");
                                    }
                                    return;
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                try {
                                    JSONArray array = new JSONArray(s);
                                    JSONObject obj = array.getJSONObject(0);
                                    String msg = obj.getString("msg");
                                    if (msg.contains("success")) {
                                        // new SPManipulation().save(SignInActivity.this, s);
                                        String username = obj.getString("UserName");
                                        String email = obj.getString("UserEmail");
                                        String mobile = obj.getString("UserMobile");
                                        StringBuilder stringBuilder = new StringBuilder();
                                        stringBuilder.append(username);
                                        stringBuilder.append(" ");
                                        stringBuilder.append(email);
                                        stringBuilder.append(" ");
                                        stringBuilder.append(mobile);
                                        String temp = stringBuilder.toString();
                                        new SPManipulation().save(SignInActivity.this, temp);
                                        Intent i = new Intent(SignInActivity.this, MainActivity.class);
                                        startActivity(i);
                                    }
                                    return;
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Toast.makeText(SignInActivity.this, "network error!", Toast.LENGTH_LONG).show();
                    }
                });
                mRequestQueue.add(stringRequest);
            }
        });
        mTextSignUp = (TextView) findViewById(R.id.to_sign_up);
        mTextSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(SignInActivity.this, SignUpActivity.class);
                startActivity(i);
            }
        });
    }


}
