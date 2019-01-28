package com.CofeeVendingMachine;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class BeverageBuilder extends AbstractProductBuilder<BeverageBuilder, Beverage>
{
    private List<Addition> availableAdditions = new ArrayList<>();
    private List<Addition> additions = new ArrayList<>();
    private AddtionBuilder addtionBuilder = new AddtionBuilder();

    public BeverageBuilder addAvailableAddition(Addition addition)
    {
        this.availableAdditions.add( addition );
        return this;
    }

    public BeverageBuilder addAvailableAdditions(Addition ... addition)
    {
        this.availableAdditions.addAll( Arrays.asList( addition ) );
        return this;
    }

    public BeverageBuilder addAvailableAdditions(List<Addition> additions )
    {
        this.availableAdditions.addAll( additions );
        return this;
    }

    public BeverageBuilder addAddition(Addition addition)
    {
        this.additions.add( addition );
        return this;
    }

    public BeverageBuilder addAdditions(List<Addition> addition)
    {
        this.additions.addAll( addition );
        return this;
    }

    public BeverageBuilder buildFrom( Beverage beverage )
    {
        this.setName( beverage.getName() );
        this.setPrice( beverage.getPrice() );
        this.addAdditions( beverage.getAdditions() );
        this.addAvailableAdditions( beverage.getAvailableAdditions() );
        return this;
    }

    public BeverageBuilder updateAddition( Beverage beverage, Addition addition )
    {
        this.buildFrom( beverage );
        this.additions.remove( beverage.getAdditions().indexOf( addition ) );
        this.additions.add( addition );

        return this;
    }

    /**
     * Helper method for casting.
     */
    @Override
    protected BeverageBuilder self()
    {
        return this;
    }

    @Override
    protected Beverage buildProduct()
    {
        Beverage finishedProduct =  new Beverage( this.name, this.price, this.availableAdditions, this.additions );
        this.setName( "" );
        this.setPrice( BigDecimal.ZERO );
        this.additions = new ArrayList<>(  );
        this.availableAdditions = new ArrayList<>(  );
        return finishedProduct;
    }
}
