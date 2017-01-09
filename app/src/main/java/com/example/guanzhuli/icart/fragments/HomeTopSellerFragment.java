package com.example.guanzhuli.icart.fragments;

import android.support.v4.app.Fragment;
import android.os.Bundle;
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
import com.example.guanzhuli.icart.adapters.TopSellerAdapter;
import com.example.guanzhuli.icart.data.TopSeller;
import com.example.guanzhuli.icart.utils.AppController;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Guanzhu Li on 12/31/2016.
 */
public class HomeTopSellerFragment extends Fragment {
    private static final String TOPSELLER_URL
            = "http://rjtmobile.com/ansari/shopingcart/androidapp/shop_top_sellers.php";
    private static final String TOPSELLER_ID = "SellerId";
    private static final String TOPSELLER_NAME = "SellerName";
    private static final String TOPSELLER_DEAL = "SellerDeal";
    private static final String TOPSELLER_RATING = "SellerRating";
    private static final String TOPSELLER_IMAGE = "SellerLogo";
    private List<TopSeller> mTopSellers = new ArrayList<>();
    private RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.tablayout_home, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_container);
        //mRequestQueue = mController.getRequestQueue();
        //mRequestQueue = Volley.newRequestQueue(getContext());

        // request the value
        StringRequest stringRequest = new StringRequest(Request.Method.GET, TOPSELLER_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        try{
                            JSONArray products = new JSONObject(s).getJSONArray("Top Sellers");
                            for (int i = 0; i < products.length(); i++) {
                                JSONObject product = products.getJSONObject(i);
                                TopSeller item = new TopSeller();
                                item.setId(product.getString(TOPSELLER_ID));
                                item.setName(product.getString(TOPSELLER_NAME));
                                item.setDeal(product.getString(TOPSELLER_DEAL));
                                item.setRating(product.getString(TOPSELLER_RATING));
                                item.setImageUrl(product.getString(TOPSELLER_IMAGE));
                                mTopSellers.add(item);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        } finally {
                            final TopSellerAdapter adapter = new TopSellerAdapter(getContext(), mTopSellers);
                            recyclerView.setAdapter(adapter);
                            recyclerView.setHasFixedSize(true);
                            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(getContext(), "network error!", Toast.LENGTH_LONG).show();
            }
        });
        AppController.getInstance().addToRequestQueue(stringRequest);
        return view;
    }
}
