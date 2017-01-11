package com.example.guanzhuli.icart.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.guanzhuli.icart.R;
import com.example.guanzhuli.icart.adapters.OrderListAdapter;
import com.example.guanzhuli.icart.data.Order;
import com.example.guanzhuli.icart.data.SPManipulation;
import com.example.guanzhuli.icart.utils.AppController;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by Guanzhu Li on 1/4/2017.
 */
public class OrderHistoryFragment extends Fragment {
    private static final String ORDER_URL =
            "http://rjtmobile.com/ansari/shopingcart/androidapp/order_history.php?&mobile=";
    private static final String ORDERID = "OrderID";
    private static final String ORDER_NAME = "ItemName";
    private static final String ORDER_QUANT = "ItemQuantity";
    private static final String ORDER_PRICE = "FinalPrice";
    private static final String ORDER_STATUS = "OrderStatus";

    private RecyclerView mRecyclerView;
    private List<Order> mOrderList = new ArrayList<>();
    private SPManipulation mSPManipulation;

    @Override
    public void onResume() {
        super.onResume();
        getActivity().setTitle("Order History");
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_order_history, container, false);
        mSPManipulation = SPManipulation.getInstance(getContext());
        getOrderList();
        mRecyclerView = (RecyclerView) view.findViewById(R.id.order_history_container);
        return view;
    }

    private void getOrderList() {
        // mRequestQueue = Volley.newRequestQueue(getContext());
        //String url = ORDER_URL + "5555555";
        mOrderList.clear();
        String url = ORDER_URL + mSPManipulation.getMobile();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        try {
                            JSONArray jsonArray = new JSONObject(s).getJSONArray("Order History");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject item = jsonArray.getJSONObject(i);
                                Order order = new Order();
                                order.setOrderId(item.getString(ORDERID));
                                order.setName(item.getString(ORDER_NAME));
                                order.setQuant(item.getInt(ORDER_QUANT));
                                order.setPrice(item.getDouble(ORDER_PRICE));
                                order.setStatus(item.getInt(ORDER_STATUS));
                                mOrderList.add(order);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        } finally {
/*                            Collections.sort(mOrderList, new Comparator<Order>() {
                                @Override
                                public int compare(Order order, Order t1) {
                                    int id1 = Integer.parseInt(order.getOrderId());
                                    int id2 = Integer.parseInt(t1.getOrderId());
                                    if (id1 < id2) return 1;
                                    else if (id1 == id2) return 0;
                                    else return -1;
                                }
                            });*/
                            final OrderListAdapter adapter = new OrderListAdapter(mOrderList, getContext());
                            mRecyclerView.setAdapter(adapter);
                            mRecyclerView.setHasFixedSize(true);
                            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
                            linearLayoutManager.setStackFromEnd(true);
                            linearLayoutManager.setReverseLayout(true);
                            mRecyclerView.setLayoutManager(linearLayoutManager);
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(getContext(), "network error!", Toast.LENGTH_LONG).show();
            }
        });
        AppController.getInstance().addToRequestQueue(stringRequest);
    }
}
