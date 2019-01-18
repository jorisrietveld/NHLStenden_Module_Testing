package com.CofeeVendingMachine;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

public class Inventory
{

    private Map<Product, Integer> currentInventory;

    /**
     * Initiate a empty inventory.
     */
    public Inventory()
    {
        currentInventory = new LinkedHashMap<>();
    }

    /**
     * Initiate the inventory that already has some items in stock.
     *
     * @param initialInventory Initial stock.
     */
    public Inventory( Map<Product, Integer> initialInventory )
    {
        currentInventory = new LinkedHashMap<>(initialInventory);
    }

    /**
     * Add new stock or update existing stock in the inventory.
     *
     * @param product The new or existing product that gets new stock.
     * @param amount  The amount of stock to add.
     */
    public void addProduct( Product product, Integer amount )
    {
        currentInventory.put( product, currentInventory.getOrDefault( product, 0 ) + amount );
    }

    /**
     * @param product The product to check.
     *
     * @return The current stock in inventory.
     */
    public int getStockOfProduct( Product product )
    {
        return currentInventory.getOrDefault( product, 0  );
    }

    /**
     * Checks if a product exists in this inventory.
     * @param product The product to check.
     * @return The answer of the check.
     */
    public boolean hasProduct( Product product )
    {
        return currentInventory.containsKey( product );
    }

}
