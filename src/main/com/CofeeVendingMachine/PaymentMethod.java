package com.CofeeVendingMachine;

import java.math.BigDecimal;

public interface PaymentMethod
{
    /**
     * Complete a order in the coffee vending machine by transacting owed money
     * from the customer of the beverage to the machines owner.
     * @param amount The amount represented in a BigDecimal for floating point precession.
     */
    void pay( BigDecimal amount );

    /**
     * Checks if a payment method is available at the moment.
     * @return The result of the check as a boolean.
     */
    boolean isAvailable();

}
