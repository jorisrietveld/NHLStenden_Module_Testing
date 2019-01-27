package com.CofeeVendingMachine;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName( "Inventory tests" )
class InventoryTest
{
    Inventory inventory;

    @BeforeEach
    void setUp()
    {
        Map<Orderable, Integer> stock = new LinkedHashMap<>();

        stock.put( new Beverage( "Coffee", BigDecimal.ZERO ), 100 );
        stock.put( new Beverage( "HotWater", new BigDecimal("0.50") ), 100 );
        stock.put( new Beverage( "ColdWater", BigDecimal.TEN ), 100 );
        stock.put( new Addition( "Sugar", BigDecimal.ZERO ), 100 );
        stock.put( new Addition( "Sugar", new BigDecimal( "0.70" ) ), 100 );
        stock.put( new Addition( "Milk", new BigDecimal( "0.50" ) ), 100 );
        stock.put( new Addition( "Whiskey", BigDecimal.ZERO ), 100 );

        this.inventory = new Inventory( stock );
    }

    @Test
    @DisplayName( "Test adding an existing product to the inventory" )
    void addTheSameProduct()
    {
        Beverage newCoffee = new Beverage( "Coffee", BigDecimal.ZERO );

        this.inventory.addProduct( newCoffee, 50 );

    }

    @Test
    @DisplayName( "Test getting the stock of a product form the inventory" )
    void getStockOfProduct()
    {
    }

    @Test
    @DisplayName( "Test validating if a product is present in the inventory." )
    void hasProduct()
    {

    }
}