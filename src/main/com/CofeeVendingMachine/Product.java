package com.CofeeVendingMachine;

import java.math.BigDecimal;

/**
 * The contract for all items soled in the coffee vending machine.
 */
public interface Product
{

    /**
     * Sets the products price.
     * @return The products price as a big integer for floating point precession.
     */
     BigDecimal getPrice();

    /**
     * Sets the products price by passing a BigDecimal for floating point precession.
     * @param price The price of the product.
     */
     void setPrice( BigDecimal price );

    /**
     * Sets the products price by passing a integer.
     * @param price The price of the product.
     */
    void setPrice( Integer price );

    /**
     * Sets the products price by passing a string that represents a real number.
     * @param price The price of the product.
     */
    void setPrice( String price );

    /**
     * Gets the name of the product.
     * @return String The name of the product.
     */
     String getName();

    /**
     * Sets or updates the name of the product.
     * @param name String The name of the product.
     */
     void setName( String name );

     Integer getInventoryCost();

}
