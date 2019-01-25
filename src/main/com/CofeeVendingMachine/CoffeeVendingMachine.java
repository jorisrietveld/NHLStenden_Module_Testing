package com.CofeeVendingMachine;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class CoffeeVendingMachine
{
    private Set<OrderedProduct> orderedProducts;

    private Inventory inventory;

    private static Map<Product, Integer> getInitialInventory()
    {
        Map<Product, Integer> initialInventory = new LinkedHashMap<>();
        initialInventory.put( new Beverage("Coffee", BigDecimal.ZERO ), 100 );
        initialInventory.put( new Beverage("ColdWater", new BigDecimal("12.00") ), 100 );
        initialInventory.put( new Beverage("HotWater", new BigDecimal("6.00") ), 100 );
        initialInventory.put( new Beverage("ChocolateMilk", BigDecimal.ONE ), 100 );

        return initialInventory;
    }

    /**
     * The access point to this program.
     * @param args THe commandline arguments passed to this program.
     */
    public static void main( String[] args ) throws Exception
    {

        CoffeeVendingMachine coffeeVendingMachine = new CoffeeVendingMachine( getInitialInventory() );

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("Enter String");

        String string = bufferedReader.readLine();

        System.out.print("Enter Integer:");

        try{
            int i = Integer.parseInt(bufferedReader.readLine());
        }catch(NumberFormatException nfe){
            System.err.println("Invalid Format!");
        }
    }



    /**
     * Create a new coffee vending machine instance.
     */
    public CoffeeVendingMachine()
    {
        this.inventory = new Inventory();
        this.orderedProducts = new HashSet<>();
    }

    /**
     * Create a new coffee vending machine instance with some stock in inventory.
     * @param initialInventory The initial stock in inventory.
     */
    public CoffeeVendingMachine( Map<Product, > initialInventory)
    {
        this.inventory = new Inventory( initialInventory );
        this.orderedProducts = new HashSet<>( );
    }

    /**
     *
     * @param product
     */
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
