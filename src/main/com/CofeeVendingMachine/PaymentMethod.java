package com.CofeeVendingMachine;

import java.math.BigDecimal;

public interface PaymentMethod
{

    public abstract void pay( BigDecimal amount );

    public abstract boolean isAvailable();

}
