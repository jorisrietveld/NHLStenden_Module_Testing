package com.CofeeVendingMachine;

import java.math.BigDecimal;

public interface Product
{

    public abstract BigDecimal getPrice();

    public abstract void setPrice( boolean price );

    public abstract String getName();

    public abstract void setName( String name );

    public abstract Integer getInvetoryCost();

}
