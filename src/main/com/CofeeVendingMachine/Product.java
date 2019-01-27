package com.CofeeVendingMachine;

import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringJoiner;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;

abstract class Product
{
    /**
     * This mapper function is used to get the total price of a products additions.
     */
    private Function<Addition, BigDecimal> totalMapper = a -> a.getPrice().multiply( a.getPrice() );

    /**
     * This collector is used to join the name of the product by appending all
     * additions to it.
     */
    private Collector<Addition, StringJoiner, String> productNameCollector =
            Collector.of(
                    () -> new StringJoiner( " " ),
                    ( joiner, addition ) -> joiner.add( addition.getName() ),
                    StringJoiner::merge,
                    StringJoiner::toString );

    /**
     * A list of additions to this product.
     */
    private List<Addition> additions;

    /**
     * The price of this single product.
     */
    private BigDecimal price;

    /**
     * The name of this single product or addition.
     */
    private String name;

    /**
     * Create a free addition of a beverage.
     *
     * @param name the name of the addition.
     */
    public Product( String name )
    {
        this.name = name;
        this.price = BigDecimal.ZERO;
    }

    /**
     * Create a addition to a beverage that the customer has to pay for.
     *
     * @param name  The name of the addition.
     * @param price The price of the addition.
     */
    Product( String name, BigDecimal price )
    {
        this.name = name;
        this.price = price;
    }

    /**
     * Create a addition to a beverage that the customer has to pay for.
     *
     * @param name  The name of the addition.
     * @param price The price of the addition.
     */
    Product( String name, BigDecimal price, Addition... additions )
    {
        this.name = name;
        this.price = price;
        this.additions = new ArrayList<>( Arrays.asList( additions ) );
    }

    /**
     * Add a addition to a product.
     *
     * @param addition
     */
    void add( Addition addition )
    {
        this.additions.add( addition );
    }

    /**
     * Sets the products price.
     *
     * @return The products price as a big integer for floating point precession.
     */
    public BigDecimal getPrice()
    {
        return additions.stream()
                        .map( totalMapper )
                        .reduce( BigDecimal.ZERO, BigDecimal::add )
                        .add( this.price );
    }

    /**
     * Gets the name of the product.
     *
     * @return String The name of the product.
     */
    public String getName()
    {
        if ( this.additions.isEmpty() )
        {
            return this.name;
        }

        // Return the products name and append it with its additions using csv.
        return this.name + " With: " + this.additions.stream().collect( this.productNameCollector );
    }

}
