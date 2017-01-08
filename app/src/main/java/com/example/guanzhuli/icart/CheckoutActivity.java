package com.example.guanzhuli.icart;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.example.guanzhuli.icart.fragments.CheckoutFragment;

public class CheckoutActivity extends AppCompatActivity {
 /*   private static final String CHECKOUT_URL =
            "http://rjtmobile.com/ansari/shopingcart/androidapp/orders.php?&item_id=";*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);
        if (findViewById(R.id.checkout_fragment_container) != null) {
            CheckoutFragment checkoutFragment = new CheckoutFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.checkout_fragment_container, checkoutFragment).commit();
        }
    }
}
