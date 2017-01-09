package com.example.guanzhuli.icart.fragments;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.example.guanzhuli.icart.CheckoutActivity;
import com.example.guanzhuli.icart.R;
import com.example.guanzhuli.icart.data.DBManipulation;
import com.example.guanzhuli.icart.data.Item;
import com.example.guanzhuli.icart.data.SPManipulation;
import com.example.guanzhuli.icart.data.ShoppingCartList;
import com.example.guanzhuli.icart.utils.AppController;

import static com.example.guanzhuli.icart.adapters.ItemGridAdapter.*;

/**
 * Created by Guanzhu Li on 1/2/2017.
 */
public class ItemDetailFragment extends Fragment {
    private NetworkImageView mImageView;
    private TextView mTextName, mTextId, mTextPrice, mTextDescription, mTextQuant;
    private ImageButton mButtonQuantAdd, mButtonQuantMinus;
    private Button mButtonAddCart, mButtonChceckout;
    private ImageLoader mImageLoader;
    private AppController mController;
    private DBManipulation mDBManipulation;
    private SPManipulation mSPManipulation;
    private Item mItem;
    private ShoppingCartList mCartList;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mController = AppController.getInstance();
        mImageLoader = mController.getImageLoader();
        mSPManipulation = SPManipulation.getInstance(context);
        mCartList = ShoppingCartList.getInstance();
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().setTitle("Product Detail");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item_detail, container, false);
        mTextId = (TextView) view.findViewById(R.id.item_details_id);
        mTextName = (TextView) view.findViewById(R.id.item_details_name);
        mTextPrice = (TextView) view.findViewById(R.id.item_details_price);
        mTextDescription = (TextView) view.findViewById(R.id.item_details_description);
        mImageView = (NetworkImageView)view.findViewById(R.id.item_details_image);
        getBundleData();
        setTextViewData();
        return view;
    }

    private void setTextViewData() {
        mTextId.setText("SKU: " + mItem.getId());
        mTextName.setText(mItem.getName());
        mTextDescription.setText("Description: " + mItem.getDescription());
        mTextPrice.setText("Price: " + Double.toString(mItem.getPrice()));
        mImageView.setImageUrl(mItem.getImageurl(), mImageLoader);
    }

    private void getBundleData() {
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            mItem = new Item();
            mItem.setMaxQuant(bundle.getInt(ITEM_QUANTITY));
            mItem.setName(bundle.getString(ITEM_NAME));
            mItem.setId(bundle.getString(ITEM_ID));
            mItem.setDescription(bundle.getString(ITEM_DES));
            mItem.setPrice(bundle.getDouble(ITEM_PRICE));
            mItem.setImageurl(bundle.getString(ITEM_IMAGEURL));
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mButtonQuantAdd = (ImageButton) getView().findViewById(R.id.button_quantity_add);
        mButtonQuantMinus = (ImageButton) getView().findViewById(R.id.button_quantity_minus);
        mTextQuant = (TextView) getView().findViewById(R.id.item_details_quant);
        // final int quant  = Integer.valueOf(mTextQuant.getText().toString());
        mButtonQuantAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int quant  = Integer.valueOf(mTextQuant.getText().toString());
                if (quant + 1 > mItem.getMaxQuant()) {
                    Toast.makeText(getContext(), "Exceeds the stock limit", Toast.LENGTH_SHORT).show();
                } else {
                    mTextQuant.setText(String.valueOf(quant + 1));
                }
            }
        });
        mButtonQuantMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int quant  = Integer.valueOf(mTextQuant.getText().toString());
                if (quant > 0) {
                    mTextQuant.setText(String.valueOf(quant - 1));
                } else {
                    return;
                }
            }
        });
        mButtonAddCart = (Button) getView().findViewById(R.id.add_cart);

        mButtonAddCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = mSPManipulation.getName();
                String mobile = mSPManipulation.getMobile();
                mDBManipulation = DBManipulation.getInstance(getContext(), name + mobile);
                // add current quant
                int curQuant = Integer.valueOf(mTextQuant.getText().toString());
                int prevQuant = mDBManipulation.select(mItem.getId());
                if (prevQuant != 0) {
                    if (prevQuant + curQuant > mItem.getMaxQuant()) {
                        Toast.makeText(getContext(), "Exceeds the stock limit", Toast.LENGTH_LONG).show();
                        return;
                    }
                    mDBManipulation.update(mItem.getId(), prevQuant + curQuant);
                } else {
                    mItem.setQuantity(curQuant);
                    mDBManipulation.insert(mItem);
                }
            }
        });
        mButtonChceckout = (Button) getView().findViewById(R.id.checkout);
        mButtonChceckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mItem.setQuantity(Integer.valueOf(mTextQuant.getText().toString()));
                // mCartList.add(mItem);
                Intent i = new Intent(getContext(), CheckoutActivity.class);
                i.putExtra("SingleItem", true);
                i.putExtra("CheckoutItem", mItem);
                startActivity(i);
            }
        });
    }


}
