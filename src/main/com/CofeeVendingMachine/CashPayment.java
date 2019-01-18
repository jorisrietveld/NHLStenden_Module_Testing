package com.CofeeVendingMachine;

import java.math.BigDecimal;

public class CashPayment implements PaymentMethod {

	public CashPayment()
    {


	}


	/**
	 * @see PaymentMethod#pay(BigDecimal)
	 * 
	 *  
	 */
	public void pay( BigDecimal amount )
    {
        // Do nothing with it, always works.
	}


	/**
	 * @see PaymentMethod#isAvailable()
	 */
	public boolean isAvailable() {
		return true;
	}

}
