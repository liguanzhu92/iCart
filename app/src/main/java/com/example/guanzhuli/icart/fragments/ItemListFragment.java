package com.example.guanzhuli.icart.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.guanzhuli.icart.R;
import com.example.guanzhuli.icart.data.Adapters.ItemGridAdapter;
import com.example.guanzhuli.icart.data.Adapters.ItemListAdapter;
import com.example.guanzhuli.icart.data.Item;
import com.example.guanzhuli.icart.utils.AppController;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.example.guanzhuli.icart.fragments.SubCategoryFragment.SUBCATEGORY_ID_KEY;

public class ItemListFragment extends Fragment {
    public static final String ITEMLIST_URL =
            "http://rjtmobile.com/ansari/shopingcart/androidapp/cust_product.php?Id=";
    public static final String ITEM_ID_KEY = "itemID";
    private ImageButton mButtonListView, mButtonGridView;
    RecyclerView recyclerView;
    List<Item> itemList;

    @Override
    public void onResume() {
        super.onResume();
        getActivity().setTitle("Products");
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_product, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.product_container);
        Bundle bundle = this.getArguments();
        String url = null;
        if (bundle != null) {
            String subcategoryID = bundle.getString(SUBCATEGORY_ID_KEY, "-1");
            if (subcategoryID.equals("-1")) {
                Toast.makeText(getContext(),"SubCategoryID invalid", Toast.LENGTH_SHORT).show();
                return view;
            } else {
                url = ITEMLIST_URL + subcategoryID;
            }
        }
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        itemList = new ArrayList<>();
                        try {
                            JSONArray items = new JSONObject(s).getJSONArray("Product");
                            for (int i = 0; i < items.length(); i++) {
                                JSONObject item = items.getJSONObject(i);
                                String id = item.getString("Id");
                                String name = item.getString("ProductName");
                                int quantity = item.getInt("Quantity");
                                double price = item.getDouble("Prize");
                                String description = item.getString("Discription");
                                String imageUrl = item.getString("Image");
                                itemList.add(new Item(id, name, imageUrl, description, quantity, price));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        final ItemListAdapter adapter = new ItemListAdapter(getContext(), itemList);
                        recyclerView.setAdapter(adapter);
                        recyclerView.setHasFixedSize(true);
                        //Layout manager for Recycler view
                        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

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

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mButtonGridView = (ImageButton) getView().findViewById(R.id.product_gridview);
        mButtonGridView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ItemGridAdapter adapter = new ItemGridAdapter(getContext(), itemList);
                recyclerView.setAdapter(adapter);
                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));
            }
        });
        mButtonListView = (ImageButton) getView().findViewById(R.id.product_listview);
        mButtonListView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ItemListAdapter adapter = new ItemListAdapter(getContext(), itemList);
                recyclerView.setAdapter(adapter);
                recyclerView.setHasFixedSize(true);
                //Layout manager for Recycler view
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            }
        });
    }
}
