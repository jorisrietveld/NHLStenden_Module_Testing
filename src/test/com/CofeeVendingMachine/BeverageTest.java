package com.CofeeVendingMachine;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class BeverageTest
{
    private Beverage coffee;
    private Beverage coldWater;
    private Beverage chocolateMilk;

    @BeforeEach
    void setUp()
    {
        this.chocolateMilk = new Beverage( "ChocolateMilk", new BigDecimal( "0.50" ) );
        this.coffee = new Beverage( "Coffee", BigDecimal.ZERO );
        this.coldWater = new Beverage( "ColdWater", BigDecimal.TEN );
    }

    @Test
    @DisplayName("Test get beverage price")
    void getPrice()
    {
        assertEquals( new BigDecimal( "0.50" ), this.chocolateMilk.getPrice() );
        assertEquals( 0.50, this.chocolateMilk.getPrice().doubleValue() );
        assertEquals( 0, this.coffee.getPrice().doubleValue() );
        assertEquals( 0, this.coffee.getPrice().intValue() );
    }

    @Test
    @DisplayName("Test get beverage name")
    void getName()
    {
        assertEquals( "Coffee", coffee.getName() );
    }

    @Test
    @DisplayName("Test comparing 2 beverage objects")
    void equals()
    {
        String val;
        Beverage newChocolateMilk = new Beverage( "ChocolateMilk", new BigDecimal("0.50") );
        Beverage updatedChocolateMilk = new Beverage( "ChocolateMilk", new BigDecimal("0.60") );

        assertEquals( this.chocolateMilk, newChocolateMilk );
        assertNotEquals( this.chocolateMilk, updatedChocolateMilk );
    }
}