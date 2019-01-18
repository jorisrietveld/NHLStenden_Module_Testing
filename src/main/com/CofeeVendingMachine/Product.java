package com.CofeeVendingMachine;

import java.math.BigDecimal;

/**
 * The contract for all items soled in the coffee vending machine.
 * todo remove setters so it will become a immutable object.
 */
public interface Product
{
    /**
     * Sets the products price.
     * @return The products price as a big integer for floating point precession.
     */
     BigDecimal getPrice();

    /**
     * Gets the name of the product.
     * @return String The name of the product.
     */
     String getName();


     Integer getInventoryCost();

}
