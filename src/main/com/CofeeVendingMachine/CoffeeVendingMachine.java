package com.CofeeVendingMachine;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class CoffeeVendingMachine
{
    private Set<OrderedProduct> orderedProducts;

    private Inventory inventory;

    /**
     * The access point to this program.
     * @param args THe commandline arguments passed to this program.
     */
    public static void main( String[] args ) throws Exception
    {
        CoffeeVendingMachine coffeeVendingMachine = new CoffeeVendingMachine();

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("Enter String");

        String string = bufferedReader.readLine();

        System.out.print("Enter Integer:");

        try{
            int i = Integer.parseInt(bufferedReader.readLine());
        }catch(NumberFormatException nfe){
            System.err.println("Invalid Format!");
        }
        gsdgsdfgfgh;
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
    public CoffeeVendingMachine( Map<Product, Integer> initialInventory)
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
