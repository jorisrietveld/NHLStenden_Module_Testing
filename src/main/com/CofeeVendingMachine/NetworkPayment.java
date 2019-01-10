package com.CofeeVendingMachine;

import java.math.BigDecimal;

public abstract class NetworkPayment implements PaymentMethod
{

    public NetworkPayment()
    {

    }


    /**
     * @see PaymentMethod#pay(BigDecimal)
     */
    public void pay( BigDecimal amount )
    {

    }


    /**
     * @see PaymentMethod#isAvailable()
     */
    public boolean isAvailable()
    {
        return false;
    }

}
