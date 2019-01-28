package com.CofeeVendingMachine;

import java.math.BigDecimal;

public class CashPayment implements Payable
{

	public CashPayment()
    {


	}


	/**
	 * @see Payable#pay(BigDecimal)
	 * 
	 *  
	 */
	public void pay( BigDecimal amount )
    {
        // Do nothing with it, always works.
	}


	/**
	 * @see Payable#isAvailable()
	 */
	public boolean isAvailable() {
		return true;
	}

    @Override
    public String toString()
    {
        return "Cash";
    }

}
