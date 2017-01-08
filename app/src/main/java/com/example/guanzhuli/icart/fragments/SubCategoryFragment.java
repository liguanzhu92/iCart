package com.example.guanzhuli.icart.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.guanzhuli.icart.R;
import com.example.guanzhuli.icart.data.Adapters.CategoryAdapter;
import com.example.guanzhuli.icart.data.Category;
import com.example.guanzhuli.icart.utils.AppController;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.example.guanzhuli.icart.fragments.CategoryFragment.CATEGORY_ID_KEY;

public class SubCategoryFragment extends Fragment {
    private AppController mController;
    // private RequestQueue mRequestQueue;
    public static final String SUB_CATEGORY_URL =
            "http://rjtmobile.com/ansari/shopingcart/androidapp/cust_sub_category.php?Id=";
    public static final String SUBCATEGORY_ID_KEY = "subcategoryID";

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mController = AppController.getInstance();
        // mRequestQueue = Volley.newRequestQueue(getContext());
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().setTitle("Category");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_sub_category, container, false);
        String url = null;
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            String categoryID = bundle.getString(CATEGORY_ID_KEY, "-1");
            if (categoryID.equals("-1")) {
                Toast.makeText(getContext(), "category request fail", Toast.LENGTH_SHORT).show();
                return view;
            }
            url = SUB_CATEGORY_URL + categoryID;
        }
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        List<Category> categoryList = new ArrayList<>();
                        try {
                            JSONArray categories = new JSONObject(s).getJSONArray("SubCategory");
                            for (int i = 0; i < categories.length(); i++) {
                                JSONObject item = categories.getJSONObject(i);
                                String name = item.getString("SubCatagoryName");
                                String imageUrl = item.getString("CatagoryImage");
                                String id = item.getString("Id");
                                String description = item.getString("SubCatagoryDiscription");
                                categoryList.add(new Category(imageUrl, name, id, description));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        final CategoryAdapter adapter = new CategoryAdapter(getContext(), R.layout.category_listview_item, categoryList);
                        ListView listView = (ListView) getView().findViewById(R.id.sub_category_list);
                        listView.setAdapter(adapter);
                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                Category category = (Category) adapterView.getItemAtPosition(i);
                                ItemListFragment itemListFragment = new ItemListFragment();
                                Bundle bundle = new Bundle();
                                bundle.putString(SUBCATEGORY_ID_KEY, category.getId());
                                itemListFragment.setArguments(bundle);
                                getActivity().getSupportFragmentManager()
                                        .beginTransaction()
                                        .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right)
                                        .replace(R.id.main_fragment_container, itemListFragment)
                                        .addToBackStack(SubCategoryFragment.class.getName())
                                        .commit();

                            }
                        });
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(getContext(), "network error!", Toast.LENGTH_SHORT).show();
            }
        });
        mController.addToRequestQueue(stringRequest);
        return view;
    }

}
