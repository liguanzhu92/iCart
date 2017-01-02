package com.example.guanzhuli.icart.Fragment;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;
import com.example.guanzhuli.icart.R;

import static com.example.guanzhuli.icart.data.Adapters.ItemGridAdapter.*;

/**
 * Created by Guanzhu Li on 1/2/2017.
 */
public class ItemDetailFragment extends Fragment {
    private NetworkImageView mImageView;
    private TextView mTextName, mTextId, mTextPrice, mTextDescription;
    private int maxQuantity;
    private RequestQueue mRequestQueue;
    private ImageLoader mImageLoader;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mRequestQueue = Volley.newRequestQueue(getContext());
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item_detail, container, false);
        mTextId = (TextView) view.findViewById(R.id.item_details_id);
        mTextName = (TextView) view.findViewById(R.id.item_details_name);
        mTextPrice = (TextView) view.findViewById(R.id.item_details_price);
        mTextDescription = (TextView) view.findViewById(R.id.item_details_description);
        mImageView = (NetworkImageView)view.findViewById(R.id.item_details_image);
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            maxQuantity = bundle.getInt(ITEM_QUANTITY);
            mTextId.setText("Item ID: " + bundle.getString(ITEM_ID));
            mTextName.setText("Item Name: " + bundle.getString(ITEM_NAME));
            mTextDescription.setText("Description: " +bundle.getString(ITEM_DES));
            mTextPrice.setText("Price: " + Double.toString(bundle.getDouble(ITEM_PRICE)));
            mImageView.setImageUrl(bundle.getString(ITEM_IMAGEURL), mImageLoader);
        }
        return view;
    }
}
