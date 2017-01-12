package com.example.guanzhuli.icart.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.guanzhuli.icart.R;
import com.example.guanzhuli.icart.adapters.NewArrivalAdapter;

/**
 * Created by Guanzhu Li on 12/31/2016.
 */
public class HomeNewArrivalFragment extends Fragment {
    RecyclerView mRecyclerView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tablayout_arrival, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_arrival_container);
        final NewArrivalAdapter adapter = new NewArrivalAdapter(getContext());
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        return view;
    }
}

