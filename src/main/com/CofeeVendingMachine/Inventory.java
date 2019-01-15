package com.CofeeVendingMachine;

import java.util.LinkedHashMap;
import java.util.Map;

public class Inventory
{

    private Map<Product, Integer> currentInventory;

    public Inventory()
    {
        currentInventory = new LinkedHashMap<>();
    }

    public void addProduct( Product product )
    {

    }

    public int getAvailableProduct( String name )
    {
        return 0;
    }

}
