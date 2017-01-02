package com.example.guanzhuli.icart.data.Adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;
import com.example.guanzhuli.icart.R;
import com.example.guanzhuli.icart.data.Category;

import java.util.List;

/**
 * Created by Guanzhu Li on 1/1/2017.
 */
public class CategoryAdapter extends ArrayAdapter<Category> {
    private RequestQueue mRequestQueue;
    Context mContext;
    ImageLoader mImageLoader;

    public CategoryAdapter(Context context, int resource, List<Category> objects) {
        super(context, resource, objects);
        mRequestQueue = Volley.newRequestQueue(getContext());
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
        //networkImageView.setDefaultImageResId(R.drawable.reload); // image for loading...
        networkImageView.setImageUrl(rowItem.getImageUrl(), mImageLoader);

        return convertView;
    }
}
