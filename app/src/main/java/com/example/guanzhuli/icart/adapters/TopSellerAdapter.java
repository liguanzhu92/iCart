package com.example.guanzhuli.icart.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.example.guanzhuli.icart.R;
import com.example.guanzhuli.icart.data.TopSeller;
import com.example.guanzhuli.icart.utils.AppController;

import java.util.List;

/**
 * Created by Guanzhu Li on 1/4/2017.
 */
public class TopSellerAdapter extends RecyclerView.Adapter<TopSellerHolder>{
    private Context mContext;
    private LayoutInflater inflater;
    private ImageLoader mImageLoader;
    private List<TopSeller> mSellerList;

    public TopSellerAdapter(Context context, List<TopSeller> objects) {
        this.mContext = context;
        mImageLoader = AppController.getInstance().getImageLoader();
        inflater = LayoutInflater.from(mContext);
        mSellerList = objects;
    }

    @Override
    public TopSellerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.cardview_topseller, parent, false);
        TopSellerHolder topSellerHolder = new TopSellerHolder(view);
        return topSellerHolder;
    }

    @Override
    public void onBindViewHolder(TopSellerHolder holder, int position) {
        holder.mTextSellerId.setText(mSellerList.get(position).getId());
        holder.mTextSellerName.setText(mSellerList.get(position).getName());
        holder.mTextSellerDeal.setText(mSellerList.get(position).getDeal());
        holder.mTextRating.setText("Rating: " + mSellerList.get(position).getRating());
        holder.mImageView.setImageUrl(mSellerList.get(position).getImageUrl(), mImageLoader);
    }

    @Override
    public int getItemCount() {
        return mSellerList.size();
    }
}

class TopSellerHolder extends RecyclerView.ViewHolder {
    NetworkImageView mImageView;
    TextView mTextSellerId, mTextSellerName, mTextSellerDeal, mTextRating;

    public TopSellerHolder(View itemView) {
        super(itemView);
        mImageView = (NetworkImageView) itemView.findViewById(R.id.seller_pic);
        mTextSellerId = (TextView) itemView.findViewById(R.id.seller_id);
        mTextSellerName = (TextView) itemView.findViewById(R.id.seller_name);
        mTextSellerDeal = (TextView) itemView.findViewById(R.id.seller_deal);
        mTextRating = (TextView) itemView.findViewById(R.id.seller_rating);
    }
}
