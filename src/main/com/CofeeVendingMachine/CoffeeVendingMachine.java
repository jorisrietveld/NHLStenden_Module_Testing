package com.CofeeVendingMachine;


import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.gui2.MultiWindowTextGUI;
import com.googlecode.lanterna.gui2.WindowBasedTextGUI;
import com.googlecode.lanterna.gui2.dialogs.ActionListDialogBuilder;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

import java.io.*;
import java.math.BigDecimal;
import java.net.SocketException;
import java.util.*;

import static java.lang.System.*;

/**
 * This is the main class of the coffee vending machine (simulator) it contains
 * the main access point and everything needed to simulate a coffee vending
 * machine like the boot and shutdown methods and methods for user interaction.
 */
public class CoffeeVendingMachine
{
    /**
     * Constants that define the terminal output formats.
     */
    final static private String MENU_HEAD_FORMAT = "-----------[%1$22s]----------";
    final static private String MENU_OPT_FORMAT = "[%d] - %s";
    final static private String MENU_FOOT_FORMAT = "---------------------------------------------";

    private Terminal terminal;
    public Screen screen;
    private TextGraphics textGraphics;

    private WindowBasedTextGUI textGUI;

    /**
     * The buffer for reading users input.
     */
    private BufferedReader bufferedReader = new BufferedReader( new InputStreamReader( in ) );

    /**
     * All products that are ordered by customers.
     */
    private Set<OrderedProduct> orderedProducts;

    /**
     * The inventory that keeps track of the stock of all ingredients present.
     */
    private Inventory inventory;

    /**
     * If set false it will initiate a shutdown.
     */
    private boolean exitSignal = false;
    private boolean powerOffSignal = false;

    private static int simulatorExitSignal = 0;
    private static Exception fatalSimulatorStateException;

    /**
     * An initiation method that configures the coffee vending with the default
     * stock and price configuration.
     *
     * @return The initial stock.
     */
    private static Map<Orderable, Integer> getInitialInventory()
    {
        Map<Orderable, Integer> initialInventory = new LinkedHashMap<>();
        initialInventory.put( new Beverage( "Coffee", BigDecimal.ZERO ), 100 );
        initialInventory.put( new Beverage( "ColdWater", new BigDecimal( "12.00" ) ), 100 );
        initialInventory.put( new Beverage( "HotWater", new BigDecimal( "6.00" ) ), 100 );
        initialInventory.put( new Beverage( "ChocolateMilk", BigDecimal.ONE ), 100 );

        return initialInventory;
    }

    /**
     * The access point to this program.
     *
     * @param args THe commandline arguments passed to this program.
     */
    public static void main( String... args )
    {
        CoffeeVendingMachine coffeeVendingMachine = new CoffeeVendingMachine( getInitialInventory() );
        try
        {
            while ( !coffeeVendingMachine.powerOffSignal )
            {
                out.println( "The system is booting up..." );
                coffeeVendingMachine.boot();
                out.println( "Initiating the coffee vending machine..." );
                coffeeVendingMachine.init();
                out.println( "Passing control to user-mode..." );
                coffeeVendingMachine.run();
            }
        }

        // There are errors with the network of the system.
        catch ( SocketException networkFailure )
        {
            simulatorExitSignal = 100;
            fatalSimulatorStateException = networkFailure;
        }

        // There are unrecoverable input and output errors.
        catch ( IOException inputOutputError )
        {
            simulatorExitSignal = 5;
            fatalSimulatorStateException = inputOutputError;
        }

        // We don't know what the hell went wrong...
        catch ( Exception unhandledException )
        {
            simulatorExitSignal = 131;
            fatalSimulatorStateException = unhandledException;
        }
        finally
        {
            // If the simulators screen process still is running terminate it
            if ( coffeeVendingMachine.screen != null )
            {
                try
                {
                    coffeeVendingMachine.screen.stopScreen();
                }
                catch ( IOException e )
                {
                    e.printStackTrace();
                }
            }

            // The nothing left to do, so initiate the shutdown.
            coffeeVendingMachine.shutdown();
        }
    }

    /**
     * Create a new coffee vending machine instance.
     */
    public CoffeeVendingMachine()
    {
        this.inventory = new Inventory();
        this.orderedProducts = new HashSet<>();
    }

    /**
     * Create a new coffee vending machine instance with some stock in inventory.
     *
     * @param initialInventory The initial stock in inventory.
     */
    public CoffeeVendingMachine( Map<Orderable, Integer> initialInventory )
    {
        this.inventory = new Inventory( initialInventory );
        this.orderedProducts = new HashSet<>();
    }

    /**
     * This function boots the coffee vending machine to its default state.
     */
    public void boot()
    {
        this.inventory = new Inventory( getInitialInventory() );
        this.orderedProducts = new HashSet<>();
    }

    /**
     * This function initiates the system that interacts with the user like
     * creating a terminal for I/O and a appropriate screen for displaying output.
     */
    public void init() throws IOException
    {
        // Create a terminal for user IO.
        this.terminal = new DefaultTerminalFactory().createTerminal();

        // Create a appropriate screen (UnixTerminal, SwingTerminal or even
        // Telnet terminal) for displaying output to user.
        this.screen = new TerminalScreen( terminal );

        // Get the graphical user settings for the GUI.
        this.textGraphics = screen.newTextGraphics();

        // Everything is setup so activate the screen.
        this.screen.startScreen();

        // Add a gui that supports multiple windows to the screen.
        this.textGUI = new MultiWindowTextGUI( screen );
    }

    /**
     * Reboots the system to its initial state, the inventory and product list
     * are in memory so get wiped on a reboot.
     */
    public void reboot()
    {
        try
        {
            screen.stopScreen();
            this.inventory = new Inventory( getInitialInventory() );
            this.orderedProducts = new HashSet<>();
            this.boot();
        }
        catch ( IOException exception )
        {
            simulatorExitSignal = 5;
            fatalSimulatorStateException = exception;
        }
    }

    /**
     * This will do a complete shutdown the coffee vending machine, it terminate
     * its self and returnes to the executing terminal.
     */
    public void shutdown()
    {
        if ( fatalSimulatorStateException != null )
        {
            // Notify the user with debug information.
            StringWriter fatalStackTrace = new StringWriter();
            PrintWriter printWriter = new PrintWriter( fatalStackTrace );
            fatalSimulatorStateException.printStackTrace( printWriter );

            out.format( "$1%s %n%n$2%s %n%n--[ Kernel Panic: Fatal Exception ] --",
                    fatalSimulatorStateException.getLocalizedMessage(),
                    fatalStackTrace
                      );
        }

        // An shutdown signal was triggered, Terminate the coffee vending
        // machine simulator with the appropriate exit signal.
        exit( simulatorExitSignal );
    }

    /**
     * Runs the main program loop of the coffee vending machine. After the
     * system is initiated by boot
     */
    public void run()
    {
        textGraphics.fillRectangle( new TerminalPosition( 1, 1 ), new TerminalSize( 80, 40 ), '#' );
        textGraphics.putString( new TerminalPosition( 30, 10 ), "Welcome to our coffee vending machine" );
        // Print the main menu
        this.initialStartupMode();
    }

    public void initialStartupMode()
    {
        new ActionListDialogBuilder()
                .setTitle( "==[ Coffee vending machine ]==" )
                .setDescription( "Please choose one of the following actions:" )
                .addAction( "Order a beverage.", this::orderBeverageMode )
                .addAction( "Maintainers mode.", this::maintainersMode )
                .addAction( "Reboot (Data will be wiped)", this::reboot )
                .addAction( "Shutdown (exit the simulator)", this::shutdown );/*
        // Print the header of the main menu
        out.format( MENU_HEAD_FORMAT, "Coffee Vending Menu" );

        // Look in the inventory for available beverages and print a menu for the user.
        List<Beverage> beverges = this.inventory.getBeverges();
        for ( int i = 0, beverageSize = beverges.size(); i < beverageSize; i++ )
        {
            out.format( MENU_OPT_FORMAT, i, beverges.get( i ).getName() );
        }

        // Add an method to exit or enter maintenance mode.
        out.format( "%1$s%nType m for maintainable mode or q to exit.%n%1$s", MENU_FOOT_FORMAT );

        String answer = bufferedReader.readLine();

        // The user choose to quit the program so set the exit signal.
        if ( answer.contains( "q" ) )
        {
            this.exitSignal = true;
            return;
        }

        // The user wants to access maintainer mode.
        if ( answer.contains( "m" ) )
        {
            // todo write maintainer mode.
        }

        try
        {
            Integer chosenMenuIndex = Integer.parseInt( answer );
            // todo Check if the index exists.
            // todo call make beverage with the name of the beverage to make
        }
        catch ( NumberFormatException ex )
        {

        }*/
    }

    public void maintainersMode()
    {

    }

    public void orderBeverageMode()
    {

    }

    public void selectAdditionsMode()
    {

    }

    /**
     * @param product
     */
    public void orderProduct( Orderable product )
    {
        //todo check availability

        //todo decide payment method

        //todo check if the payment was successfully completed

        //todo subtract from inventory
    }

    public void addInventory( Orderable product, Integer amount )
    {
        inventory.addProduct( product, amount );
    }

}
