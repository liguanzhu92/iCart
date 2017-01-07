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


// url
// http://rjtmobile.com/ansari/shopingcart/androidapp/shop_reg.php?%20name=aamir&email=aa@gmail.com&mobile=555454545465&password=7011
public class SignUpActivity extends AppCompatActivity {
    private static final String REGISTER_URL = "http://rjtmobile.com/ansari/shopingcart/androidapp/shop_reg.php?%20";
    private TextView mTextUsername, mTextMobile, mTextEmail, mTextPwd, mTextRePwd, mTextSignIn;
    private Button mButtonSignUp;
    private RequestQueue mRequestQueue;
    private SPManipulation mSPManipulation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        mSPManipulation = SPManipulation.getInstance(this);
        mRequestQueue =  Volley.newRequestQueue(SignUpActivity.this);
        mTextUsername = (TextView) findViewById(R.id.sign_up_username);
        mTextMobile = (TextView) findViewById(R.id.sign_up_mobile);
        mTextEmail = (TextView) findViewById(R.id.sign_up_email);
        mTextPwd = (TextView) findViewById(R.id.sign_up_password);
        mTextRePwd = (TextView) findViewById(R.id.sign_up_password2);
        mButtonSignUp = (Button) findViewById(R.id.button_sign_up);
        mButtonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String username = mTextUsername.getText().toString();
                // add method to check illegal character
                final String mobile = mTextMobile.getText().toString();
                // add method to check all are numbers
                final String email = mTextEmail.getText().toString();
                // add method to check email format
                final String pwd = mTextPwd.getText().toString();
                String pwd2 = mTextRePwd.getText().toString();
                if (!pwdMatch(pwd, pwd2)) {
                    Toast.makeText(SignUpActivity.this, "Password does not match!", Toast.LENGTH_LONG).show();
                    return;
                }
                // add method to check pwd and pwd2 match
                // http://rjtmobile.com/ansari/shopingcart/androidapp/shop_reg.php?%20name=aamir&email=aa@gmail.com&mobile=555454545465&password=7011
                String url = REGISTER_URL + "name=" + username
                        + "&email=" + email + "&mobile=" + mobile
                        + "&password=" + pwd;
                StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String s) {
                                if (s.contains("successfully")) {
                                    mSPManipulation.clearSharedPreference(SignUpActivity.this);
                                    mSPManipulation.saveName(username);
                                    mSPManipulation.saveMobile(mobile);
                                    mSPManipulation.saveEmail(email);
                                    mSPManipulation.savePwd(pwd);
                                    Intent i = new Intent(SignUpActivity.this, MainActivity.class);
                                    startActivity(i);
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Toast.makeText(SignUpActivity.this, "network error!", Toast.LENGTH_LONG).show();
                    }
                });
                mRequestQueue.add(stringRequest);
            }
        });
        mTextSignIn = (TextView) findViewById(R.id.to_sign_in);
        mTextSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(SignUpActivity.this, SignInActivity.class);
                startActivity(i);
            }
        });
    }
    private boolean pwdMatch(String pwd, String pwd2) {
        return pwd.equals(pwd2);
    }

    private boolean checkUsername(String username) {
        return true;
    }

    private boolean checkMobile(String mobile) {
        return true;
    }

    private boolean checkEmail(String email) {
        return true;
    }
}
