package com.CofeeVendingMachine;

import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * This class represents a beverage that can be ordered in the coffee vending
 * machine.
 */
public class Beverage implements Orderable
{
    /**
     * The base price of the beverage.
     */
    private BigDecimal price;

    /**
     * The base name of the beverage.
     */
    private String name;

    /**
     * A registry that keeps track of compatible additions to a beverage.
     */
    private List<Addition> availableAdditions = new ArrayList<>();

    /**
     * A list of added ingredients to an beverage.
     */
    private List<Addition> additions = new ArrayList<>();

    /**
     * Create a free beverage.
     */
    public Beverage( String name )
    {
        this( name, BigDecimal.ZERO );
    }

    /**
     * Create a beverage that the customer has to pay for.
     *
     * @param name  The name of the beverage.
     * @param price The price of the beverage.
     */
    public Beverage( String name, BigDecimal price )
    {
        this.name = name;
        this.price = price;

    }

    /**
     * Create a beverage that the customer has to pay for.
     *
     * @param name  The name of the beverage.
     * @param price The price of the beverage.
     * @param additions Possible additions to this beverage.
     */
    public Beverage( String name, BigDecimal price, Addition... additions )
    {
        this.name = name;
        this.price = price;
        this.availableAdditions.addAll( Arrays.asList( additions ) );
    }

    /**
     *
     * @return
     */
    public List<Addition> getAdditions()
    {
        return this.additions;
    }

    /**
     *
     * @param addition
     */
    public void addAddition(Addition addition)
    {
        this.additions.add( addition );
    }

    /**
     * Sets the products price.
     *
     * @return The products price as a big integer for floating point precession.
     */
    @Override
    public BigDecimal getPrice()
    {
        return this.price;
    }

    /**
     * Gets the name of the product.
     *
     * @return String The name of the product.
     */
    @Override
    public String getName()
    {
        return this.name;
    }

    @Override
    public List<Addition> getAvailableAdditions()
    {
        return this.availableAdditions;
    }

    public void registerAddition( Addition... addition )
    {
        this.availableAdditions.addAll( Arrays.asList( addition ) );
    }

    /**
     * Checks whether a addition is possible to add to this beverage.
     *
     * @param addition The ingredient to check.
     *
     * @return Answer if the addition was registered in this beverage.
     */
    public boolean isCompatible( Addition addition )
    {
        return this.availableAdditions.contains( addition );
    }

    /**
     * Custom equals method for comparing by the products name not hashcode.
     *
     * @param o The object to compare it to.
     *
     * @return True when the object is the same or has the same name.
     */
    @Override
    public boolean equals( Object o )
    {

        if ( this == o )
        {
            return true;
        }

        if ( o == null || getClass() != o.getClass() )
        {
            return false;
        }

        Beverage beverage = (Beverage) o;

        if ( !this.getPrice().equals( ((Beverage) o).getPrice() ) )
        {
            return false;
        }

        if ( this.getName().equals( beverage.getName() ) )
        {
            return true;
        }
        return false;
    }
}
