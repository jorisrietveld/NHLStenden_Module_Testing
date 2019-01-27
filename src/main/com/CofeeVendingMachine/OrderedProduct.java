package com.CofeeVendingMachine;


import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;


public class OrderedProduct
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
     * @see Orderable#getPrice()
     */
    public BigDecimal getPrice()
    {
        return null;
    }


    /**
     * @see Orderable#setPrice(boolean)
     */
    public void setPrice( BigDecimal price )
    {

    }


    /**
     * @see Orderable#getName()
     */
    public String getName()
    {
        return null;
    }


    /**
     * @see Orderable#setName(String)
     */
    public void setName( String name )
    {

    }


    /**
     * @see Orderable#getInventoryCost()
     */
    public Integer getInventoryCost()
    {
        return null;
    }

}
