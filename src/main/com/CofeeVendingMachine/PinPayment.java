package com.CofeeVendingMachine;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.SocketException;
import java.net.URL;

public class PinPayment extends NetworkPayment
{

    public PinPayment()
    {
        /*
        super( new URL( "https://rabobank.com" ) );
        */
    }


    /**
     * Complete a order in the coffee vending machine by transacting owed money
     * from the customer of the beverage to the machines owner.
     *
     * @param amount The amount represented in a BigDecimal for floating point precession.
     */
    @Override
    public void pay( BigDecimal amount )
    {
        // Do nothing with it it is always fine.
    }

    /**
     * Checks if a payment method is available at the moment.
     *
     * @return The result of the check as a boolean.
     */
    @Override
    public boolean isAvailable()
    {
        try{
            return this.checkNetworkAvailability();
        }
        catch ( IOException networkError )
        {
            // Todo check if this behaviour is correct.
            return false;
        }
    }

    @Override
    public String toString()
    {
        return "Pin";
    }
}
