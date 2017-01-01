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
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.guanzhuli.icart.R;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.util.ArrayList;
import java.util.List;

public class CategoryFragment extends Fragment {

    public static final String CATEGORY_REQUEST_URL
            = "http://rjtmobile.com/ansari/shopingcart/androidapp/cust_category.php";
    private RequestQueue mRequestQueue;

    class Category {
        String mImageUrl;
        String mName;

        Category(String imageUrl, String name) {
            mImageUrl = imageUrl;
            mName = name;
        }

        public String getImageUrl() {
            return mImageUrl;
        }

        public String getName() {
            return mName;
        }
    }

    class CategoryAdapter extends ArrayAdapter<Category> {

        Context mContext;
        ImageLoader mImageLoader;

        public CategoryAdapter(Context context, int resource, List<Category> objects) {
            super(context, resource, objects);
            mContext = context;
            mImageLoader = new ImageLoader(mRequestQueue, new ImageLoader.ImageCache() {
                private final LruCache<String, Bitmap> mCache = new LruCache<>(10);
                public void putBitmap(String url, Bitmap bitmap) {
                    mCache.put(url, bitmap);
                }
                public Bitmap getBitmap(String url) {
                    return mCache.get(url);
                }
            });
        }

        private class ViewHolder {
            NetworkImageView mImageView;
            TextView mTextView;

            public void setImageView(NetworkImageView mImageView) {
                this.mImageView = mImageView;
            }

            public void setTextView(TextView mTextView) {
                this.mTextView = mTextView;
            }

            public NetworkImageView getImageView() {
                return mImageView;
            }

            public TextView getTextView() {
                return mTextView;
            }
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            Category rowItem = getItem(position);

            LayoutInflater mInflater = (LayoutInflater) mContext
                    .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.category_listview_item, null);
                holder = new ViewHolder();
                holder.setTextView((TextView) convertView.findViewById(R.id.list_view_category_text));
                holder.setImageView((NetworkImageView) convertView.findViewById(R.id.list_view_category_img));
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            holder.getTextView().setText(rowItem.getName());
            NetworkImageView networkImageView =  holder.getImageView();
            //networkImageView.setDefaultImageResId(R.drawable.lo); // image for loading...
            networkImageView.setImageUrl(rowItem.getImageUrl(), mImageLoader);

            return convertView;
        }
    }

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
                                categoryList.add(new Category(imageUrl, name));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        CategoryAdapter adapter = new CategoryAdapter(getContext(), R.layout.category_listview_item, categoryList);
                        ListView listView = (ListView) getView().findViewById(R.id.category_list);
                        listView.setAdapter(adapter);
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
