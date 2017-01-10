package com.example.guanzhuli.icart.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.example.guanzhuli.icart.R;
import com.example.guanzhuli.icart.data.Item;
import com.example.guanzhuli.icart.utils.AppController;

import java.util.List;

/**
 * Created by Guanzhu Li on 1/4/2017.
 */
public class CheckoutItemAdapter extends RecyclerView.Adapter<ItemHolder> {
    private List<Item> checkoutItem;
    private Context mContext;
    private LayoutInflater inflater;
    private ImageLoader mImageLoader;

    public CheckoutItemAdapter(List<Item> checkoutItem, Context context) {
        this.checkoutItem = checkoutItem;
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
        inflater = LayoutInflater.from(mContext);
    }

    @Override
    public ItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = inflater.inflate(R.layout.cardview_cart_item, parent, false);
        ItemHolder itemHolder = new ItemHolder(v);
        return itemHolder;
    }

    @Override
    public void onBindViewHolder(ItemHolder holder, int position) {
        holder.mTextId.setText(checkoutItem.get(position).getId());
        holder.mTextQuant.setText(Integer.toString(checkoutItem.get(position).getQuantity()));
        holder.mTextName.setText(checkoutItem.get(position).getName());
        holder.mTextPrice.setText(Double.toString(
                checkoutItem.get(position).getPrice() * checkoutItem.get(position).getQuantity()));
        holder.mImage.setImageUrl(checkoutItem.get(position).getImageurl(), mImageLoader);
        holder.mButtonDelete.setVisibility(View.INVISIBLE);
        holder.mButtonQuantMinus.setVisibility(View.INVISIBLE);
        holder.mButtonQuantAdd.setVisibility(View.INVISIBLE);

    }

    @Override
    public int getItemCount() {
        return checkoutItem.size();
    }
}

class ItemHolder extends RecyclerView.ViewHolder {
    ImageButton mButtonQuantAdd, mButtonQuantMinus;
    ImageView mButtonDelete;
    NetworkImageView mImage;
    TextView mTextName, mTextId, mTextQuant, mTextPrice;
    public ItemHolder(View itemView) {
        super(itemView);
        mImage = (NetworkImageView) itemView.findViewById(R.id.cart_item_image);
        mTextName = (TextView) itemView.findViewById(R.id.cart_item_name);
        mTextId = (TextView) itemView.findViewById(R.id.cart_item_id);
        mTextQuant = (TextView) itemView.findViewById(R.id.cart_item_number);
        mTextPrice = (TextView) itemView.findViewById(R.id.cart_item_price);
        mButtonQuantAdd = (ImageButton) itemView.findViewById(R.id.cart_quant_add);
        mButtonQuantMinus = (ImageButton) itemView.findViewById(R.id.cart_quant_minus);
        mButtonDelete = (ImageView) itemView.findViewById(R.id.cart_item_delete);
    }
}
