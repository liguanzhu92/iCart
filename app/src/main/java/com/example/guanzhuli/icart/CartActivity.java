package com.example.guanzhuli.icart;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.example.guanzhuli.icart.adapters.CartListAdapter;
import com.example.guanzhuli.icart.data.DBManipulation;
import com.example.guanzhuli.icart.data.Item;
import com.example.guanzhuli.icart.data.SPManipulation;
import com.example.guanzhuli.icart.data.ShoppingCartList;

import java.util.List;

public class CartActivity extends AppCompatActivity {

    private TextView mTextTotal;
    private Button mButtonContinue, mButtonCheckout;
    private RecyclerView recyclerView;
    private DBManipulation mDBManipulation;
    private SPManipulation mSPManipulation;
    private ShoppingCartList itemList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        recyclerView = (RecyclerView) findViewById(R.id.cart_container);
        mSPManipulation = SPManipulation.getInstance(this);
        String name =  mSPManipulation.getName();
        String mobile =  mSPManipulation.getMobile();
        mDBManipulation = DBManipulation.getInstance(this, name + mobile);
        itemList = ShoppingCartList.getInstance();
        itemList.clear();
        itemList.addAll( mDBManipulation.selectAll());
        //itemList = mDBManipulation.selectAll();
        CartListAdapter cartListAdapter = new CartListAdapter(CartActivity.this, itemList, this);
        recyclerView.setAdapter(cartListAdapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(CartActivity.this));

        // Button
        mButtonContinue = (Button) findViewById(R.id.cart_continue);
        mButtonContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        mButtonCheckout = (Button) findViewById(R.id.cart_checkout);
        mButtonCheckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CartActivity.this, CheckoutActivity.class);
                startActivity(intent);

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
}
