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
        currentInventory = initialInventory;
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
     * @param name The name of the product
     *
     * @return
     */
    public int getStockOfProduct( String name )
    {
        return 0;
    }

    public boolean hasProduct( String string )
    {
        currentInventory.forEach( (k,v) -> { } );
    }

    public boolean hasProduct( Product product )
    {
        AtomicBoolean answer = new AtomicBoolean( false );
        currentInventory.forEach( (k,v) -> {
            if( k.getName().equals( product.getName() ))
            {
                answer.set( true );
            }
        } );
        return answer.get();
    }

}
