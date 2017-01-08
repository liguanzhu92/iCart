package com.example.guanzhuli.icart.data.Adapters;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.example.guanzhuli.icart.fragments.ItemDetailFragment;
import com.example.guanzhuli.icart.fragments.ItemListFragment;
import com.example.guanzhuli.icart.R;
import com.example.guanzhuli.icart.data.Item;
import com.example.guanzhuli.icart.utils.AppController;

import java.util.List;

import static com.example.guanzhuli.icart.data.Adapters.ItemGridAdapter.*;

/**
 * Created by Guanzhu Li on 1/1/2017.
 */
public class ItemListAdapter extends RecyclerView.Adapter<ListViewHolder>{
    Context mContext;
    LayoutInflater inflater;
    ImageLoader mImageLoader;
    List<Item> mItemArrayList;
    public ItemListAdapter(Context context, List<Item> objects) {
        this.mContext = context;
        mImageLoader = AppController.getInstance().getImageLoader();
/*        mRequestQueue = Volley.newRequestQueue(context);
        mImageLoader = new ImageLoader(mRequestQueue, new ImageLoader.ImageCache() {
            private final LruCache<String, Bitmap> mCache = new LruCache<>(10);
            public void putBitmap(String url, Bitmap bitmap) {
                mCache.put(url, bitmap);
            }
            public Bitmap getBitmap(String url) {
                return mCache.get(url);
            }
        });*/
        inflater = LayoutInflater.from(context);
        mItemArrayList = objects;
    }

    @Override
    public ListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = inflater.inflate(R.layout.cardview_product_list, parent, false);
        ListViewHolder viewHolder = new ListViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ListViewHolder holder, final int position) {
        holder.mTextID.setText(mItemArrayList.get(position).getId());
        holder.mTextName.setText(mItemArrayList.get(position).getName());
        holder.mTextPrice.setText(Double.toString(mItemArrayList.get(position).getPrice()));
        holder.mNetworkImageView.setImageUrl(mItemArrayList.get(position).getImageUrl(), mImageLoader);
        holder.mNetworkImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ItemDetailFragment itemDetailFragment = new ItemDetailFragment();
                Bundle bundle = new Bundle();
                bundle.putString(ITEM_ID, mItemArrayList.get(position).getId());
                bundle.putString(ITEM_NAME, mItemArrayList.get(position).getName());
                bundle.putString(ITEM_DES, mItemArrayList.get(position).getDescription());
                bundle.putString(ITEM_IMAGEURL, mItemArrayList.get(position).getImageUrl());
                bundle.putInt(ITEM_QUANTITY, mItemArrayList.get(position).getQuantity());
                bundle.putDouble(ITEM_PRICE, mItemArrayList.get(position).getPrice());
                itemDetailFragment.setArguments(bundle);
                AppCompatActivity activity = (AppCompatActivity) view.getContext();
                activity.getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_fragment_container, itemDetailFragment)
                        .addToBackStack(ItemListFragment.class.getName())
                        .commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mItemArrayList.size();
    }
}
class ListViewHolder extends RecyclerView.ViewHolder {
    NetworkImageView mNetworkImageView;
    TextView mTextID, mTextName, mTextPrice;
    public ListViewHolder(View itemView) {
        super(itemView);
        mNetworkImageView = (NetworkImageView) itemView.findViewById(R.id.item_list_image);
        mTextID = (TextView) itemView.findViewById(R.id.item_list_id);
        mTextName = (TextView) itemView.findViewById(R.id.item_list_name);
        mTextPrice = (TextView) itemView.findViewById(R.id.item_list_price);
    }
}


