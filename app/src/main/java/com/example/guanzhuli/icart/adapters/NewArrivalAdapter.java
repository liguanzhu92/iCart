package com.example.guanzhuli.icart.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.guanzhuli.icart.R;
import com.example.guanzhuli.icart.data.NewArrivalItem;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Guanzhu Li on 1/11/2017.
 */
public class NewArrivalAdapter extends RecyclerView.Adapter<NewArrivalHolder>{

    private Context mContext;
    private LayoutInflater inflater;
    private List<NewArrivalItem> mList = new ArrayList<>();

    public NewArrivalAdapter(Context context) {
        this.mContext = context;
        inflater = LayoutInflater.from(mContext);
        // initialize list;
        initialList();
    }


    @Override
    public NewArrivalHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.cardview_newarrival, parent, false);
        NewArrivalHolder newArrivalHolder = new NewArrivalHolder(view);
        return newArrivalHolder;
    }

    @Override
    public void onBindViewHolder(NewArrivalHolder holder, int position) {
        holder.mTextArrivalId.setText(Integer.toString(mList.get(position).getId()));
        holder.mTextArrivalName.setText(mList.get(position).getName());
        holder.mTextArrivalPrice.setText(Double.toString(mList.get(position).getPrice()));
        holder.mImageView.setImageResource(mList.get(position).getResource());
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    private void initialList() {
        int[] itemId = {123,321,333,232,452};
        String[] name = {"Heel Shoes", "Tablet", "SamSung", "Laptop", "Handbag"};
        Double[] price = {90.99, 200.00, 400.00, 900.99, 70.39};
        int[] res = {R.drawable.item1, R.drawable.item2, R.drawable.item3, R.drawable.item4, R.drawable.item5};
        for (int i = 0; i < itemId.length; i++) {
            mList.add(new NewArrivalItem(itemId[i], name[i], price[i], res[i]));
        }
    }
}

class NewArrivalHolder extends RecyclerView.ViewHolder {
    ImageView mImageView;
    TextView mTextArrivalId, mTextArrivalName, mTextArrivalPrice;

    public NewArrivalHolder(View itemView) {
        super(itemView);
        mImageView = (ImageView) itemView.findViewById(R.id.arrival_pic);
        mTextArrivalId = (TextView) itemView.findViewById(R.id.arrival_id);
        mTextArrivalName = (TextView) itemView.findViewById(R.id.arrival_name);
        mTextArrivalPrice = (TextView) itemView.findViewById(R.id.arrival_price);
    }
}