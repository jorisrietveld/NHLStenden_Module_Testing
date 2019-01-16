package com.CofeeVendingMachine;


import java.util.Map;
import java.util.Set;

public class CoffeeVendingMachine
{

    private Set<OrderedProduct> orderedProducts;

    private Inventory inventory;

    public static void main( String[] args )
    {

    }

    public CoffeeVendingMachine()
    {

    }

    public CoffeeVendingMachine( Map<Product, Integer> initialInventory)
    {

    }

    public void orderProduct( Product product )
    {
        //todo check availability

        //todo decide payment method

        //todo check if the payment was successfully completed

        //todo subtract from inventory
    }

    public void addInventory( Product product, Integer amount )
    {
        inventory.addProduct( product, amount );
    }

}
