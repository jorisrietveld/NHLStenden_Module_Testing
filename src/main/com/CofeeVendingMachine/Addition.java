package com.CofeeVendingMachine;

import java.math.BigDecimal;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;

/**
 * This class represents a addition that can be added to a beverage.
 */
public class Addition extends Product implements Orderable
{
    public static final int MIN_QUANTITY = 3;
    public static final int MAX_QUANTITY = 300;
    public static final int LOW_QUANTITY = 50;

    private int quantity;

    /**
     * Create a free addition to a product.
     * @param name The name of the addition and unique identifier.
     */
    Addition(String name)
    {
        this(name, BigDecimal.ZERO);
    }

    /**
     * Create a addition to a beverage that has a price.
     * @param name The name of the addition and unique identifier.
     * @param price The price per unit the customer has to pay.
     */
    Addition(String name, BigDecimal price)
    {
        this(name, price, 0);
    }

    /**
     * The default constructor, a addition to a beverage that has a price.
     * @param name The name of the addition and unique identifier.
     * @param price The price per unit the customer has to pay.
     */
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

    public static Addition updateQuantity( Addition addition, int quantity )
    {
        return new Addition( addition.getName(), addition.getPrice(), addition.getQuantity()+quantity );
    }
}
