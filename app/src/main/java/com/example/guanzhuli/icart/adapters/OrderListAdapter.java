package com.example.guanzhuli.icart.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.guanzhuli.icart.R;
import com.example.guanzhuli.icart.data.Order;

import java.util.List;

/**
 * Created by Guanzhu Li on 1/4/2017.
 */
public class OrderListAdapter extends RecyclerView.Adapter<OrderContentHolder>{
    private List<Order> mOrderList;
    private Context mContext;
    private LayoutInflater inflater;

    public OrderListAdapter(List<Order> orderList, Context context) {
        mOrderList = orderList;
        mContext = context;
        inflater = LayoutInflater.from(mContext);
    }


    @Override
    public OrderContentHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.cardview_order_history, parent, false);
        OrderContentHolder orderContentHolder = new OrderContentHolder(view);
        return orderContentHolder;
    }

    @Override
    public void onBindViewHolder(OrderContentHolder holder, int position) {
        holder.mTextOrderId.setText("Order ID: " + mOrderList.get(position).getOrderId());
        holder.mTextName.setText(mOrderList.get(position).getName());
        holder.mTextQuant.setText(Integer.toString(mOrderList.get(position).getQuant()));
        holder.mTextPrice.setText("Total:" + Double.toString(mOrderList.get(position).getPrice()));
        holder.mTextStatus.setText("Status: " + parseOrderStatus(mOrderList.get(position).getStatus()));
        // holder.mTextStatus.setText("Status: " + Integer.toString(mOrderList.get(position).getStatus()));
        // set image listener -  view order details;
    }

    private String parseOrderStatus(int status) {
        if (status == 1) {
            return "Confirm";
        } else if (status == 2) {
            return "Dispatch";
        } else if (status == 3) {
            return "On the way";
        }else if (status == 4) {
            return "Delivered";
        } else {
            return "invalid code" ;
        }
    }

    @Override
    public int getItemCount() {
        return mOrderList.size();
    }
}

class OrderContentHolder extends RecyclerView.ViewHolder {
    TextView mTextName, mTextOrderId, mTextQuant, mTextPrice, mTextStatus;
    ImageView mImage;
    public OrderContentHolder(View itemView) {
        super(itemView);
        mTextName = (TextView) itemView.findViewById(R.id.order_list_name);
        mTextOrderId = (TextView) itemView.findViewById(R.id.order_list_id);
        mTextQuant = (TextView) itemView.findViewById(R.id.order_list_quant);
        mTextPrice = (TextView) itemView.findViewById(R.id.order_list_price);
        mTextStatus = (TextView) itemView.findViewById(R.id.order_list_status);
        mImage = (ImageView) itemView.findViewById(R.id.order_list_icon);
    }
}
