package com.example.guanzhuli.icart;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.guanzhuli.icart.data.SPManipulation;
import org.json.JSONException;
import org.json.JSONObject;

// http://rjtmobile.com/ansari/shopingcart/androidapp/shop_login.php?mobile=123456&password=ansari
public class SignInActivity extends AppCompatActivity {

    private static final String LOGIN_URL = "http://rjtmobile.com/ansari/shopingcart/androidapp/shop_login.php?";
    Button mButtonSignIn;
    TextView mTextUsername, mTextPassword;
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
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, Url, null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject jsonObject) {
                                try {
                                    String msg = jsonObject.getString("msg");
                                    if (msg.contains("failure")) {
                                        mTextPassword.setText("");
                                    } else {
                                        new SPManipulation().save(SignInActivity.this, mobile);
                                        Intent i = new Intent(SignInActivity.this, MainActivity.class);
                                        startActivity(i);
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Toast.makeText(getApplicationContext(), "Volley Error", Toast.LENGTH_SHORT).show();
                    }
                });
                mRequestQueue.add(jsonObjectRequest);
            }
        });
    }


}
