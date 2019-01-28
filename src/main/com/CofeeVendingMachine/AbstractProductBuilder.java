package com.CofeeVendingMachine;

import java.math.BigDecimal;

public abstract class AbstractProductBuilder<B, T extends Product>
{
    protected String name;
    protected BigDecimal price;

    public AbstractProductBuilder()
    {
        this.name = "";
        this.price = BigDecimal.ZERO;
    }

    public B setName( String name )
    {
        this.name = name;
        return self();
    }

    public B setPrice( BigDecimal price )
    {
        this.price = price;
        return self();
    }

    public B setPrice( String price )
    {
        this.price = new BigDecimal( price );
        return self();
    }

    public String getName()
    {
        return name;
    }

    public BigDecimal getPrice()
    {
        return price;
    }

    /**
     * Helper method for casting.
     */
    protected abstract B self();

    protected abstract T buildProduct();

    public final T build()
    {
        return buildProduct();
    }
}
