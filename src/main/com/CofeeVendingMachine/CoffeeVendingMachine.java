package com.CofeeVendingMachine;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.*;

import static java.lang.System.*;

public class CoffeeVendingMachine
{
    /**
     * Constants that define the terminal output formats.
     */
    final static private String MENU_OPT_FORMAT =  "-----------[%1$22s]----------";
    final static private String MENU_HEAD_FORMAT = "[%d] - %s";
    final static private String MENU_FOOT_FORMAT = "---------------------------------------------";

    /**
     * The buffer for reading users input.
     */
    private BufferedReader bufferedReader = new BufferedReader(new InputStreamReader( in));

    /**
     * All products that are ordered by customers.
     */
    private Set<OrderedProduct> orderedProducts;

    /**
     * The inventory that keeps track of the stock of all ingredients present.
     */
    private Inventory inventory;

    /**
     * An initiation method that configures the coffee vending with the default
     * stock and price configuration.
     * @return The initial stock.
     */
    private static Map<Orderable, Integer> getInitialInventory()
    {
        Map<Orderable, Integer> initialInventory = new LinkedHashMap<>();
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


        out.println("-----------[Coffee vending machine]----------");


        for ( Beverage beverage : coffeeVendingMachine.inventory.getBeverges() )
        {
            out.format( "[] - make a %s", beverage );
        }

        String string = bufferedReader.readLine();

        out.print("Enter Integer:");

        try{
            int i = Integer.parseInt(bufferedReader.readLine());
        }catch(NumberFormatException nfe){
            err.println("Invalid Format!");
        }
    }

    public void printMainMenu()
    {
        // Todo write out.format( this.MENU_FORMAT) items with the available beverages.

        // Todo write reader that checks for the menu options and calls the order beverage method
        // Todo     or for the m character for maintenance.

        // todo catch exceptions and print them.

        // todo write exit function
    }

    // todo write method that can order a beverage.

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
    public CoffeeVendingMachine( Map<Orderable, Integer> initialInventory )
    {
        this.inventory = new Inventory( initialInventory );
        this.orderedProducts = new HashSet<>( );
    }

    /**
     *
     * @param product
     */
    public void orderProduct( Orderable product )
    {
        //todo check availability

        //todo decide payment method

        //todo check if the payment was successfully completed

        //todo subtract from inventory
    }

    public void addInventory( Orderable product, Integer amount )
    {
        inventory.addProduct( product, amount );
    }

}
