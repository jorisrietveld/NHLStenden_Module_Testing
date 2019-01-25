package com.CofeeVendingMachine;

import java.math.BigDecimal;

/**
 * This class represents a beverage that can be ordered in the coffee vending
 * machine.
 */
public class Beverage implements Product
{

    private BigDecimal price;

    private String name;

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
     * Create a free beverage.
     */
    public Beverage( String name )
    {
        this.name = name;
        this.price = BigDecimal.ZERO;
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

    /**
     * Custom equals method for comparing by the products name not hashcode.
     * @param o The object to compare it to.
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

    /**
     *
     */
    public Integer getInventoryCost()
    {
        return null;
    }

}
