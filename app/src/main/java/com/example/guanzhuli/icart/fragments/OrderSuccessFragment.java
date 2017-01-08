package com.example.guanzhuli.icart.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.guanzhuli.icart.CartActivity;
import com.example.guanzhuli.icart.MainActivity;
import com.example.guanzhuli.icart.R;

/**
 * Created by Guanzhu Li on 1/4/2017.
 */
public class OrderSuccessFragment extends Fragment {
    TextView mTextBack, mTextHome, mTextViewOrder;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order_success, container, false);
        mTextBack = (TextView) view.findViewById(R.id.success_back_cart);
        mTextBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), CartActivity.class));
            }
        });
        mTextHome = (TextView) view.findViewById(R.id.success_home);
        mTextHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), MainActivity.class));
            }
        });
        mTextViewOrder = (TextView) view.findViewById(R.id.success_view_order);
        mTextViewOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        return view;
    }
}
