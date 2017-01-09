package com.example.guanzhuli.icart.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.guanzhuli.icart.CartActivity;
import com.example.guanzhuli.icart.R;
import com.example.guanzhuli.icart.adapters.CheckoutItemAdapter;
import com.example.guanzhuli.icart.data.DBManipulation;
import com.example.guanzhuli.icart.data.Item;
import com.example.guanzhuli.icart.data.SPManipulation;
import com.example.guanzhuli.icart.data.ShoppingCartList;
import com.paypal.android.sdk.payments.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Guanzhu Li on 1/4/2017.
 */
public class CheckoutFragment extends Fragment {
    private static final String TAG = "iCartPayment";
    //private static final String CONFIG_ENVIRONMENT = PayPalConfiguration.ENVIRONMENT_NO_NETWORK;
    private static final String CONFIG_ENVIRONMENT = PayPalConfiguration.ENVIRONMENT_SANDBOX;

    // note that these credentials will differ between live & sandbox environments.
    private static final String CONFIG_CLIENT_ID = "ATmK2McmbeWIC6UGRZw7gioRz2xZbBiCuUWN_1d8WK38OIeX6vXFWBTqjmwJaYXkRnZsVbQCe7VAgnO3";
    private static final int REQUEST_CODE_PAYMENT = 1;
    private static PayPalConfiguration config = new PayPalConfiguration()
            .environment(CONFIG_ENVIRONMENT)
            .clientId(CONFIG_CLIENT_ID)
            // The following are only used in PayPalFuturePaymentActivity.
            .merchantName("Example Merchant")
            .merchantPrivacyPolicyUri(Uri.parse("https://www.example.com/privacy"))
            .merchantUserAgreementUri(Uri.parse("https://www.example.com/legal"));

    private static final String CHECKOUT_URL =
            "http://rjtmobile.com/ansari/shopingcart/androidapp/orders.php?&item_id=";
    private Button mButtonConfirm, mButtonCancel;
    private RequestQueue mRequestQueue;
    private RecyclerView mRecyclerView;
    private DBManipulation mDBManipulation;
    private SPManipulation mSPManipulation;
    private String mobile;
    private ShoppingCartList mCartList;
    private CheckoutItemAdapter adapter;
    private List<Item> mItemList = new ArrayList<>();

    @Override
    public void onResume() {
        super.onResume();
        getActivity().setTitle("Checkout");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_checkout, container, false);
        // start paypal service
        Intent intent = new Intent(getContext(), PayPalService.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);
        getActivity().startService(intent);
        //initialItemList();
        mCartList = ShoppingCartList.getInstance();
        mRecyclerView = (RecyclerView) view.findViewById(R.id.checkout_items_container);
        if (getActivity().getIntent().getBooleanExtra("SingleItem", false)) {
            Item item = (Item) getActivity().getIntent().getSerializableExtra("CheckoutItem");
            mItemList.add(item);
        } else {
            mItemList = mCartList;
        }
        adapter = new CheckoutItemAdapter(mItemList, getContext());
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRequestQueue = Volley.newRequestQueue(getContext());
        mButtonCancel = (Button) view.findViewById(R.id.checkout_cancel);
        mButtonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
                startActivity(new Intent(getContext(), CartActivity.class));
            }
        });
        mSPManipulation = SPManipulation.getInstance(getContext());
        mobile = mSPManipulation.getMobile();
        mButtonConfirm = (Button) view.findViewById(R.id.checkout_paypal);
        mButtonConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                payOrder();
            }
        });
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getActivity().stopService(new Intent(getContext(), PayPalService.class));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_PAYMENT) {
            if (resultCode == getActivity().RESULT_OK) {
                PaymentConfirmation confirm =
                        data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);
                if (confirm != null) {
                    try {
                        Log.i(TAG, confirm.toJSONObject().toString(4));
                        Log.i(TAG, confirm.getPayment().toJSONObject().toString(4));
                        /**
                         *  TODO: send 'confirm' (and possibly confirm.getPayment() to your server for verification
                         * or consent completion.
                         * See https://developer.paypal.com/webapps/developer/docs/integration/mobile/verify-mobile-payment/
                         * for more details.
                         *
                         * For sample mobile backend interactions, see
                         * https://github.com/paypal/rest-api-sdk-python/tree/master/samples/mobile_backend
                         */
                        registerOrder();
                    } catch (JSONException e) {
                        Log.e(TAG, "an extremely unlikely failure occurred: ", e);
                    }
                }
            } else if (resultCode == getActivity().RESULT_CANCELED) {
                Log.i(TAG, "The user canceled.");
            } else if (resultCode == PaymentActivity.RESULT_EXTRAS_INVALID) {
                Log.i(
                        TAG,
                        "An invalid Payment or PayPalConfiguration was submitted. Please see the docs.");
            }
        }
    }

    private void payOrder() {
        /*
         * PAYMENT_INTENT_SALE will cause the payment to complete immediately.
         * Change PAYMENT_INTENT_SALE to
         *   - PAYMENT_INTENT_AUTHORIZE to only authorize payment and capture funds later.
         *   - PAYMENT_INTENT_ORDER to create a payment for authorization and capture
         *     later via calls from your server.
         *
         * Also, to include additional payment details and an item list, see getStuffToBuy() below.
         */
        PayPalPayment thingToBuy = getStuffToBuy(PayPalPayment.PAYMENT_INTENT_SALE);

        /*
         * See getStuffToBuy(..) for examples of some available payment options.
         */

        Intent intent = new Intent(getContext(), PaymentActivity.class);

        // send the same configuration for restart resiliency
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);

        intent.putExtra(PaymentActivity.EXTRA_PAYMENT, thingToBuy);

        startActivityForResult(intent, REQUEST_CODE_PAYMENT);
    }

    private PayPalPayment getStuffToBuy(String paymentIntent) {

        //--- include an item list, payment amount details
        PayPalItem[] items = new PayPalItem[mItemList.size()];
        for (int i = 0; i <  mItemList.size(); i++) {
            items[i] = new PayPalItem(mItemList.get(i).getName(),
                    mItemList.get(i).getQuantity(),
                    new BigDecimal(mItemList.get(i).getPrice()),
                    "HKD",
                    mItemList.get(i).getId());
        }
        BigDecimal subtotal = PayPalItem.getItemTotal(items);
        BigDecimal shipping = new BigDecimal("0.00");
        BigDecimal tax = new BigDecimal("0.00");
        PayPalPaymentDetails paymentDetails = new PayPalPaymentDetails(shipping, subtotal, tax);
        BigDecimal amount = subtotal.add(shipping).add(tax);
        PayPalPayment payment = new PayPalPayment(amount, "HKD", "iCart", paymentIntent);
        payment.items(items).paymentDetails(paymentDetails);

        //--- set other optional fields like invoice_number, custom field, and soft_descriptor
        payment.custom("This is text that will be associated with the payment that the app can use.");

        return payment;
    }

    private void registerOrder(){
        for (int i = 0; i < mItemList.size(); i++) {
//  http://rjtmobile.com/ansari/shopingcart/androidapp/orders.php?&item_id=701&item_names=laptop&item_quantity=1&final_price=100000&mobile=654987
            double finalPrice = mItemList.get(i).getQuantity() * mItemList.get(i).getPrice();
            String name = "";
            try {
                name = URLEncoder.encode(mItemList.get(i).getName(), "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                return;
            }
            String url = CHECKOUT_URL + mItemList.get(i).getId()
                    + "&item_names=" + name
                    + "&item_quantity=" + mItemList.get(i).getQuantity()
                    + "&final_price=" + finalPrice + "&mobile=" + mobile;
            StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String s) {
                            try {
                                JSONArray jsonArray = new JSONObject(s).getJSONArray("Order Confirmed");
                                JSONObject jsonObject = jsonArray.getJSONObject(0);
                                String msg = jsonObject.getString("OrderId");
                                Log.d("order ", msg);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {
                    Toast.makeText(getContext(), "network error!", Toast.LENGTH_LONG).show();
                }
            });
            mRequestQueue.add(stringRequest);
        }

        mItemList.clear();

        mDBManipulation = DBManipulation.getInstance(getContext(),mSPManipulation.getName()+ mSPManipulation.getMobile());
        mDBManipulation.deleteAll();
        OrderSuccessFragment orderSuccessFragment = new OrderSuccessFragment();
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.checkout_fragment_container, orderSuccessFragment).commit();
    }
}
