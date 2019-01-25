package com.CofeeVendingMachine;

import java.math.BigDecimal;

/**
 * This class represents a addition that can be added to a beverage.
 */
public class Addition implements Product
{

    private BigDecimal price;

    private String name;

    private Integer inventoryCost;

    /**
     * Create a addition to a beverage that the customer has to pay for.
     * @param name The name of the addition.
     * @param price The price of the addition.
     */
    public Addition(String name, BigDecimal price)
    {
        this.name = name;
        this.price = price;
    }

    /**
     * Create a free addition of a beverage.
     * @param name the name of the addition.
     */
    public Addition(String name )
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

        Addition addition = (Addition) o;

        if ( !this.getPrice().equals( ((Addition) o).getPrice() ) )
        {
            return false;
        }

        if ( this.getName().equals( addition.getName() ) )
        {
            return true;
        }
        return false;
    }


    @Override
    public Integer getInventoryCost()
    {
        return null;
    }
}
