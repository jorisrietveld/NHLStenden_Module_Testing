package com.CofeeVendingMachine;

import java.math.BigDecimal;

public class CashPayment implements Payable
{
    private static boolean disabled = false;

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
		return !this.disabled;
	}


    public void toggleMethod()
    {
        disabled = !disabled;
    }

    public boolean isDisabled()
    {
        return disabled;
    }

    @Override
    public String toString()
    {
        return "Cash";
    }

}
