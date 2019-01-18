package com.CofeeVendingMachine;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;

@DisplayName( "Addition class tests" )
class AdditionTest
{
    private Addition sugar;
    private Addition milk;
    private Addition whiskey;

    @BeforeEach
    void setUp()
    {
        this.sugar = new Addition( "Sugar", new BigDecimal( "0.50" ) );
        this.milk = new Addition( "Milk", BigDecimal.ZERO );
        this.whiskey = new Addition( "Whiskey", BigDecimal.TEN );
    }

    @Test
    @DisplayName("Test get addition price")
    void getPrice()
    {
        assertEquals( new BigDecimal( "10" ), this.whiskey.getPrice() );
        assertEquals( 0.50, this.sugar.getPrice().doubleValue() );
        assertEquals( 0, this.milk.getPrice().doubleValue() );
        assertEquals( 0, this.milk.getPrice().intValue() );
    }

    @Test
    @DisplayName("Test get addition name")
    void getName()
    {
        assertEquals( "Sugar", sugar.getName() );
    }

    @Test
    @DisplayName("Test comparing 2 addition objects")
    void equals()
    {
        String val;
        Addition newSugar = new Addition( "Sugar", new BigDecimal("0.50") );
        Addition updatedSugar = new Addition( "Sugar", new BigDecimal("0.60") );

        assertEquals( sugar, newSugar );
        assertNotEquals( sugar, updatedSugar );
    }

    @Test
    void getInventoryCost()
    {

    }
}