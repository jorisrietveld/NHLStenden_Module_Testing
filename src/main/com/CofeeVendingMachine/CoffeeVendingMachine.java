package com.CofeeVendingMachine;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.*;

import static java.lang.System.*;

public class CoffeeVendingMachine
{
    /**
     * Constants that define the terminal output formats.
     */
    final static private String MENU_HEAD_FORMAT = "-----------[%1$22s]----------";
    final static private String MENU_OPT_FORMAT = "[%d] - %s";
    final static private String MENU_FOOT_FORMAT = "---------------------------------------------";

    /**
     * The buffer for reading users input.
     */
    private BufferedReader bufferedReader = new BufferedReader( new InputStreamReader( in ) );

    /**
     * All products that are ordered by customers.
     */
    private Set<OrderedProduct> orderedProducts;

    /**
     * The inventory that keeps track of the stock of all ingredients present.
     */
    private Inventory inventory;

    /**
     * If set false it will initiate a shutdown.
     */
    private boolean exitSignal = false;

    /**
     * An initiation method that configures the coffee vending with the default
     * stock and price configuration.
     *
     * @return The initial stock.
     */
    private static Map<Orderable, Integer> getInitialInventory()
    {
        Map<Orderable, Integer> initialInventory = new LinkedHashMap<>();
        initialInventory.put( new Beverage( "Coffee", BigDecimal.ZERO ), 100 );
        initialInventory.put( new Beverage( "ColdWater", new BigDecimal( "12.00" ) ), 100 );
        initialInventory.put( new Beverage( "HotWater", new BigDecimal( "6.00" ) ), 100 );
        initialInventory.put( new Beverage( "ChocolateMilk", BigDecimal.ONE ), 100 );

        return initialInventory;
    }

    /**
     * The access point to this program.
     *
     * @param args THe commandline arguments passed to this program.
     */
    public static void main( String ... args )
    {
        CoffeeVendingMachine coffeeVendingMachine = new CoffeeVendingMachine( getInitialInventory() );
        coffeeVendingMachine.boot();
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
     *
     * @param initialInventory The initial stock in inventory.
     */
    public CoffeeVendingMachine( Map<Orderable, Integer> initialInventory )
    {
        this.inventory = new Inventory( initialInventory );
        this.orderedProducts = new HashSet<>();
    }

    /**
     * This function boots the system and handles the main program loop.
     */
    public void boot()
    {
        try
        {
            while ( !this.exitSignal )
            {
                this.printMainMenu();
            }
            // Todo call a terminate function.
        }
        catch ( Exception ex )
        {
            out.format( MENU_HEAD_FORMAT, "An exception was thrown" );
            ex.printStackTrace();
        }
    }

    public void reboot()
    {
        // todo write message to notify the user about the reboot.
        // todo re-initiate the ordered products and inventory to its defaults
        // todo      and call the boot function again.
    }

    /**
     * This will do a complete shutdown the coffee vending machine, it terminate
     * its self and returnes to the executing terminal.
     */
    public void shutdown()
    {
        //
    }

    public void printMainMenu() throws IOException
    {
        // Print the header of the main menu
        out.format( MENU_HEAD_FORMAT, "Coffee Vending Menu" );

        // Look in the inventory for available beverages and print a menu for the user.
        List<Beverage> beverges = this.inventory.getBeverges();
        for ( int i = 0, beverageSize = beverges.size(); i < beverageSize; i++ )
        {
            out.format( MENU_OPT_FORMAT, i, beverges.get( i ).getName() );
        }

        // Add an method to exit or enter maintenance mode.
        out.format( "%1$s%nType m for maintainable mode or q to exit.%n%1$s", MENU_FOOT_FORMAT );

        String answer = bufferedReader.readLine();

        // The user choose to quit the program so set the exit signal.
        if ( answer.contains( "q" ) )
        {
            this.exitSignal = true;
            return;
        }

        // The user wants to access maintainer mode.
        if ( answer.contains( "m" ) )
        {
            // todo write maintainer mode.
        }

        try
        {
            Integer chosenMenuIndex = Integer.parseInt( answer );
            // todo Check if the index exists.
            // todo call make beverage with the name of the beverage to make
        }
        catch ( NumberFormatException ex )
        {

        }
    }
    /**
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
