package com.CofeeVendingMachine;

import java.math.BigDecimal;

public class Beverage implements Product
{

    private BigDecimal price;

    private String name;

    private Integer inventoryCost;

    public Beverage()
    {

    }


    /**
     * @see Product#getPrice()
     */
    public BigDecimal getPrice()
    {
        return price;
    }


    /**
     * @see Product#setPrice(boolean)
     */
    public void setPrice( BigDecimal price )
    {
        this.price = price;
    }


    /**
     * @see Product#getName()
     */
    public String getName()
    {

        return name;
    }


    /**
     * @see Product#setName(String)
     */
    public void setName( String name )
    {
        this.name = name;
    }


    /**
     * @see Product#getInvetoryCost()
     */
    public Integer getInvetoryCost()
    {
        return inventoryCost;
    }

}
