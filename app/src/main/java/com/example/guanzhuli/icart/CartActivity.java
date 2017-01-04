package com.example.guanzhuli.icart;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.guanzhuli.icart.data.Adapters.CartListAdapter;
import com.example.guanzhuli.icart.data.DBManipulation;
import com.example.guanzhuli.icart.data.Item;
import com.example.guanzhuli.icart.data.SPManipulation;

import java.util.List;

public class CartActivity extends AppCompatActivity {

    private TextView mTextTotal;
    private Button mButtonContinue, mButtonCheckout;
    private RecyclerView recyclerView;
    private DBManipulation mDBManipulation;
    private List<Item> itemList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        recyclerView = (RecyclerView) findViewById(R.id.cart_container);
        String temp = new SPManipulation().getValue(CartActivity.this);
        String[] test = temp.split(" ");
        mDBManipulation = new DBManipulation(CartActivity.this, test[0]+test[2]);
        itemList = mDBManipulation.selectAll();
        CartListAdapter cartListAdapter = new CartListAdapter(CartActivity.this, itemList);
        recyclerView.setAdapter(cartListAdapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(CartActivity.this));

        // Button
        mButtonContinue = (Button) findViewById(R.id.cart_continue);
        mButtonContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        mButtonCheckout = (Button) findViewById(R.id.cart_checkout);
        mButtonCheckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        //Set Total
        mTextTotal = (TextView) findViewById(R.id.cart_total);
        mTextTotal.setText(String.valueOf(calculateTotal(itemList)));
    }

    private double calculateTotal(List<Item> itemList) {
        double result = 0;
        for (int i = 0; i < itemList.size(); i++) {
            result += itemList.get(i).getPrice() * itemList.get(i).getQuantity();
        }
        return result;
    }

    @Override
    protected void onResume() {
        super.onResume();
        mTextTotal = (TextView) findViewById(R.id.cart_total);
        mTextTotal.setText(String.valueOf(calculateTotal(itemList)));
    }
}
