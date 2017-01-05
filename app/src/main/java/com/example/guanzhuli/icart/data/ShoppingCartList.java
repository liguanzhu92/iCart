package com.example.guanzhuli.icart.data;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Guanzhu Li on 1/4/2017.
 */
public class ShoppingCartList extends ArrayList<Item>{
    private static ShoppingCartList ourInstance = new ShoppingCartList();

    public static ShoppingCartList getInstance() {
        return ourInstance;
    }

    private ShoppingCartList() {
        super();
    }
}
