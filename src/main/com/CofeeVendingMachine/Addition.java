package com.CofeeVendingMachine;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * This class represents a addition that can be added to a beverage.
 */
public class Addition extends Product implements Orderable
{
    private Integer quantity;

    Addition(String name, BigDecimal price)
    {
        super(name, price);
        this.quantity = 1;
    }

    Addition(String name, BigDecimal price, Integer quantity)
    {
        super(name, price);
        this.quantity = quantity;
    }

    public Integer getQuantity()
    {
        return this.quantity;
    }

    public Addition updateQuantity(Integer quantity)
    {
        return new Addition( this.getName(), this.getPrice(), quantity );
    }

    @Override
    public List<Addition> getAvailableAdditions()
    {
        return new ArrayList<>(  );
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
}
