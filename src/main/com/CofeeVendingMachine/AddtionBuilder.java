package com.CofeeVendingMachine;

public class AddtionBuilder extends AbstractProductBuilder<AddtionBuilder, Addition>
{
    private int additionQuantity = 0;

    public AddtionBuilder buildFrom( Addition addition )
    {
        this.setName( addition.getName() );
        this.setPrice( addition.getPrice() );
        this.setQuantity( addition.getQuantity() );
        return this;
    }

    public AddtionBuilder setQuantity(int quantity)
    {
        this.additionQuantity = 0;
        return this;
    }

    /**
     * Helper method for casting.
     */
    @Override
    protected AddtionBuilder self()
    {
        return this;
    }

    /**
     * Build the actual addition.
     * @return
     */
    @Override
    protected Addition buildProduct()
    {
        return new Addition( this.getName(), this.getPrice() );
    }
}
