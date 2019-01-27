package com.CofeeVendingMachine;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

/**
 * The contract for all items soled in the coffee vending machine.
 * todo remove setters so it will become a immutable object.
 */
public interface Orderable
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

    public List<Addition> getAvailableAdditions();
}
