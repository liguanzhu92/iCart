package com.example.guanzhuli.icart.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.guanzhuli.icart.R;
import com.example.guanzhuli.icart.data.SPManipulation;
import org.json.JSONException;
import org.json.JSONObject;


public class ProfileFragment extends Fragment {
    private RequestQueue mRequestQueue;
    private Button mButtonConfirm, mButtonCancel;
    private EditText mEditPwd, mEditNewPwd, mEditReNewPwd;
    private static final String RESET_URL =
            "http://rjtmobile.com/ansari/shopingcart/androidapp/shop_reset_pass.php?&mobile=";
    // http://rjtmobile.com/ansari/shopingcart/androidapp/shop_reset_pass.php?&mobile=1&password=2&newpassword=456
    private SPManipulation mSPManipulation;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //mobile = new SPManipulation().getMobile(getContext());
        mRequestQueue = Volley.newRequestQueue(getContext());
        mSPManipulation = SPManipulation.getInstance(getContext());
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().setTitle("Profile");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        mEditPwd = (EditText) view.findViewById(R.id.profile_current_pwd);
        mEditNewPwd = (EditText) view.findViewById(R.id.profile_newpwd);
        mEditReNewPwd = (EditText) view.findViewById(R.id.profile_newpwd2);

        // Inflate the layout for this fragment
        mButtonConfirm = (Button) view.findViewById(R.id.profile_confirm);
        mButtonConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String pwd = mEditPwd.getText().toString();
                String newPwd = mEditNewPwd.getText().toString();
                String newPwd2 = mEditReNewPwd.getText().toString();
                if (!checkOldPwd(pwd)|| haveSpace(newPwd) || !checkNewPwd(newPwd, newPwd2)) {
                    mEditPwd.setText("");
                    mEditNewPwd.setText("");
                    mEditReNewPwd.setText("");
                    return;
                }
                resetPwd(pwd, newPwd, mSPManipulation.getMobile());
            }
        });
        mButtonCancel = (Button) view.findViewById(R.id.profile_cancel);
        mButtonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HomeFragment homeFragment = new HomeFragment();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.main_fragment_container, homeFragment).commit();
            }
        });
        return view;
    }

    private boolean checkOldPwd(String pwd) {
        String savedPwd = mSPManipulation.getPwd();
        if ( !pwd.equals(savedPwd)) {
            Toast.makeText(getContext(), "Old Password is not correct!", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    private boolean haveSpace(String pwd) {
        if (pwd.contains(" ")) {
            Toast.makeText(getContext(), "No space in password", Toast.LENGTH_LONG).show();
            return true;
        }
        return false;
    }

    private boolean checkNewPwd(String newPwd, String newPwd2) {
        if (! newPwd.equals(newPwd2)) {
            Toast.makeText(getContext(), "Password does not match", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    private void resetPwd(String pwd, String newPwd, String mobile) {
        // http://rjtmobile.com/ansari/shopingcart/androidapp/shop_reset_pass.php?&mobile=1&password=2&newpassword=456
        String url = RESET_URL + mobile + "&password=" + pwd + "&newpassword=" + newPwd;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        try {
                            String msg = new JSONObject(s).getString("msg");
                            if (msg.contains("successfully")) {
                                Toast.makeText(getContext(), "password reset successfully", Toast.LENGTH_LONG).show();
                                mEditPwd.setText("");
                                mEditNewPwd.setText("");
                                mEditReNewPwd.setText("");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(getContext(), "Volley Error", Toast.LENGTH_LONG).show();
            }
        });
        mRequestQueue.add(stringRequest);
    }

}
