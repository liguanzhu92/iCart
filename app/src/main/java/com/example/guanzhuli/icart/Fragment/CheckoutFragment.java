package com.example.guanzhuli.icart.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.guanzhuli.icart.CartActivity;
import com.example.guanzhuli.icart.R;
import com.example.guanzhuli.icart.data.Adapters.CheckoutItemAdapter;
import com.example.guanzhuli.icart.data.DBManipulation;
import com.example.guanzhuli.icart.data.Item;
import com.example.guanzhuli.icart.data.SPManipulation;
import com.example.guanzhuli.icart.data.ShoppingCartList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

/**
 * Created by Guanzhu Li on 1/4/2017.
 */
public class CheckoutFragment extends Fragment {
    private static final String CHECKOUT_URL =
            "http://rjtmobile.com/ansari/shopingcart/androidapp/orders.php?&item_id=";
    Button mButtonConfirm, mButtonCancel;
    RequestQueue mRequestQueue;
    RecyclerView mRecyclerView;
    private String mobile;
    private ShoppingCartList mItemList;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_checkout, container, false);
        mItemList = ShoppingCartList.getInstance();
        //initialItemList();
        mRecyclerView = (RecyclerView) view.findViewById(R.id.checkout_items_container);
        CheckoutItemAdapter adapter = new CheckoutItemAdapter(mItemList, getContext());
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRequestQueue = Volley.newRequestQueue(getContext());
        mButtonCancel = (Button) view.findViewById(R.id.checkout_cancel);
        mButtonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
                startActivity(new Intent(getContext(), CartActivity.class));
            }
        });
        mobile = new SPManipulation().getMobile(getContext());
        // mobile="5555555";
        mButtonConfirm = (Button) view.findViewById(R.id.checkout_confirm);
        mButtonConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerOrder();
            }
        });
        return view;
    }

    private void registerOrder(){
        for (int i = 0; i < mItemList.size(); i++) {
//  http://rjtmobile.com/ansari/shopingcart/androidapp/orders.php?&item_id=701&item_names=laptop&item_quantity=1&final_price=100000&mobile=654987
            double finalPrice = mItemList.get(i).getQuantity() * mItemList.get(i).getPrice();
            String name = "";
            try {
                name = URLEncoder.encode(mItemList.get(i).getName(), "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                return;
            }
            String url = CHECKOUT_URL + mItemList.get(i).getId()
                    + "&item_names=" + name
                    + "&item_quantity=" + mItemList.get(i).getQuantity()
                    + "&final_price=" + finalPrice + "&mobile=" + mobile;
            StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String s) {
                            try {
                                JSONArray jsonArray = new JSONObject(s).getJSONArray("Order Confirmed");
                                JSONObject jsonObject = jsonArray.getJSONObject(0);
                                String msg = jsonObject.getString("OrderId");
                                Log.d("order ", msg);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {
                    Toast.makeText(getContext(), "network error!", Toast.LENGTH_LONG).show();
                }
            });
            mRequestQueue.add(stringRequest);
        }

        mItemList.clear();
        SPManipulation sp = new SPManipulation();
        new DBManipulation(getContext(),sp.getName(getContext()) + sp.getMobile(getContext())).deleteAll();
        OrderSuccessFragment orderSuccessFragment = new OrderSuccessFragment();
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.checkout_fragment_container, orderSuccessFragment).commit();
    }
}
