package com.example.guanzhuli.icart.Fragment;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.*;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.guanzhuli.icart.R;
import com.example.guanzhuli.icart.data.Adapters.CategoryAdapter;
import com.example.guanzhuli.icart.data.Category;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class CategoryFragment extends Fragment {
    public static final String CATEGORY_ID_KEY = "categoryID";

    public static final String CATEGORY_REQUEST_URL
            = "http://rjtmobile.com/ansari/shopingcart/androidapp/cust_category.php";
    private RequestQueue mRequestQueue;



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mRequestQueue = Volley.newRequestQueue(getContext());
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_category, container, false);

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, CATEGORY_REQUEST_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        List<Category> categoryList = new ArrayList<>();
                        try {
                            JSONArray categories = new JSONObject(response).getJSONArray("Category");
                            for(int i = 0; i < categories.length(); i++) {
                                JSONObject item = categories.getJSONObject(i);
                                String name = item.getString("CatagoryName");
                                String imageUrl = item.getString("CatagoryImage");
                                String id = item.getString("Id");
                                categoryList.add(new Category(imageUrl, name, id));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        final CategoryAdapter adapter = new CategoryAdapter(getContext(), R.layout.category_listview_item, categoryList);
                        ListView listView = (ListView) getView().findViewById(R.id.category_list);
                        listView.setAdapter(adapter);
                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                Category category = (Category) adapterView.getItemAtPosition(i);
                                SubCategoryFragment subCategoryFragment = new SubCategoryFragment();
                                Bundle bundle = new Bundle();
                                bundle.putString(CATEGORY_ID_KEY, category.getId());
                                subCategoryFragment.setArguments(bundle);
                                getActivity().getSupportFragmentManager()
                                        .beginTransaction()
                                        .replace(R.id.main_fragment_container, subCategoryFragment)
                                        .addToBackStack(SubCategoryFragment.class.getName())
                                        .commit();

                            }
                        });
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "network error!", Toast.LENGTH_LONG).show();
            }
        });
        // Add the request to the RequestQueue.
        mRequestQueue.add(stringRequest);
        return v;
    }
}
