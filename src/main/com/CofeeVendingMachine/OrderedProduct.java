package com.CofeeVendingMachine;


import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;


public class OrderedProduct implements Product
{

    private Beverage beverage;

    private Set<Addition> addition;

    private LocalDateTime date;

    public OrderedProduct( Beverage beverage )
    {

    }

    public void add( Addition addition )
    {

    }

    public BigDecimal getTotal()
    {
        return null;
    }


    /**
     * @see Product#getPrice()
     */
    public BigDecimal getPrice()
    {
        return null;
    }


    /**
     * @see Product#setPrice(boolean)
     */
    public void setPrice( boolean price )
    {

    }


    /**
     * @see Product#getName()
     */
    public String getName()
    {
        return null;
    }


    /**
     * @see Product#setName(String)
     */
    public void setName( String name )
    {

    }


    /**
     * @see Product#getInvetoryCost()
     */
    public Integer getInvetoryCost()
    {
        return null;
    }

}
