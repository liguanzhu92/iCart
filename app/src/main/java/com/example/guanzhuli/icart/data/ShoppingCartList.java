package com.example.guanzhuli.icart.data;

import java.util.*;

/**
 * Created by Guanzhu Li on 1/4/2017.
 */
public class ShoppingCartList extends ArrayList<Item>{
    private Map<String, Integer> mMap = new HashMap<>();
    private static ShoppingCartList ourInstance = new ShoppingCartList();

    public static ShoppingCartList getInstance() {
        return ourInstance;
    }

    private ShoppingCartList() {
        super();
    }
}
