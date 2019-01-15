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
     * @param name The name of the beverage.
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
     * Sets the products price by passing a BigDecimal for floating point precession.
     *
     * @param price The price of the product.
     */
    @Override
    public void setPrice( BigDecimal price )
    {
        this.price = price;
    }

    /**
     * Sets the products price by passing a integer.
     *
     * @param price The price of the product.
     */
    @Override
    public void setPrice( Integer price )
    {
        this.price = new BigDecimal( price );
    }

    /**
     * Sets the products price by passing a string that represents a real number.
     *
     * @param price The price of the product.
     */
    @Override
    public void setPrice( String price )
    {
        this.price = new BigDecimal( price );
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
     * Sets or updates the name of the product.
     *
     * @param name String The name of the product.
     */
    @Override
    public void setName( String name )
    {
        this.name = name;
    }

    /**
     */
    public Integer getInventoryCost()
    {
        return null;
    }

}
