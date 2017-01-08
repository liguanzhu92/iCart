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
import com.example.guanzhuli.icart.utils.AppController;

import java.util.List;

/**
 * Created by Guanzhu Li on 1/1/2017.
 */
public class CategoryAdapter extends ArrayAdapter<Category> {

    Context mContext;
    ImageLoader mImageLoader;

    public CategoryAdapter(Context context, int resource, List<Category> objects) {
        super(context, resource, objects);
        mContext = context;
        mImageLoader = AppController.getInstance().getImageLoader();
/*        mRequestQueue = Volley.newRequestQueue(getContext());
        mImageLoader = new ImageLoader(mRequestQueue, new ImageLoader.ImageCache() {
            private final LruCache<String, Bitmap> mCache = new LruCache<>(10);
            public void putBitmap(String url, Bitmap bitmap) {
                mCache.put(url, bitmap);
            }
            public Bitmap getBitmap(String url) {
                return mCache.get(url);
            }
        });*/
    }

    private class ViewHolder {
        NetworkImageView mImageView;
        TextView mCategoryNameTextView;
        TextView mCategoryDescTextView;

        public void setImageView(NetworkImageView mImageView) {
            this.mImageView = mImageView;
        }

        public void setCategoryNameTextView(TextView mTextView) {
            this.mCategoryNameTextView = mTextView;
        }

        public NetworkImageView getImageView() {
            return mImageView;
        }

        public TextView getCategoryNameTextView() {
            return mCategoryNameTextView;
        }

        public TextView getCategoryDescTextView() {
            return mCategoryDescTextView;
        }

        public void setCategoryDescTextView(TextView categoryDescTextView) {
            mCategoryDescTextView = categoryDescTextView;
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
            holder.setCategoryNameTextView((TextView) convertView.findViewById(R.id.list_view_category_text));
            holder.setCategoryDescTextView((TextView) convertView.findViewById(R.id.list_view_category_desc));
            holder.setImageView((NetworkImageView) convertView.findViewById(R.id.list_view_category_img));
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.getCategoryNameTextView().setText(rowItem.getName());
        holder.getCategoryDescTextView().setText(rowItem.getDescription());
        NetworkImageView networkImageView =  holder.getImageView();
        //networkImageView.setDefaultImageResId(R.drawable.reload); // image for loading...
        networkImageView.setImageUrl(rowItem.getImageUrl(), mImageLoader);

        return convertView;
    }
}
