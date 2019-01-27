package com.CofeeVendingMachine;


import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.gui2.MultiWindowTextGUI;
import com.googlecode.lanterna.gui2.WindowBasedTextGUI;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

import java.io.*;
import java.math.BigDecimal;
import java.net.SocketException;
import java.util.*;

import static java.lang.System.*;

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
        catch ( SocketException networkFailure )
        {
            // The network can't be reached.
            simulatorExitSignal = 100;
            fatalSimulatorStateException = networkFailure;
        }
        catch ( IOException inputOutputError )
        {
            // There is a general I/O error.
            simulatorExitSignal = 5;
            fatalSimulatorStateException = inputOutputError;
        }
        catch ( Exception unhandledException )
        {
            // State is not recoverable.
            simulatorExitSignal = 131;
            fatalSimulatorStateException = unhandledException;
        }
        finally
        {
            // Check for fatal exceptions that require the coffee vending
            // machine to terminate.
            if ( fatalSimulatorStateException != null )
            {
                // Notify the user about the fatal error and print detailed
                // debugging information.
                StringWriter fatalStackTrace = new StringWriter();
                PrintWriter printWriter = new PrintWriter( fatalStackTrace );
                fatalSimulatorStateException.printStackTrace( printWriter );

                out.format( "$1%s %n%n$2%s %n%n--[ Kernel Panic: Fatal Exception ] --",
                        fatalSimulatorStateException.getLocalizedMessage(),
                        fatalStackTrace
                          );
            }

            // If the screen process still is running terminate it so the we
            // return to the parent that executed this program.
            if(coffeeVendingMachine.screen != null) {
                try {
                    coffeeVendingMachine.screen.stopScreen();
                }
                catch(IOException e) {
                    e.printStackTrace();
                }
            }
        }

        // An shutdown signal or fatal exception has occurred, Terminate the
        // the coffee vending machine simulator with the appropriate exit signal.
        exit( simulatorExitSignal );
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
    public void reboot() throws IOException
    {
        screen.stopScreen();
        this.inventory = new Inventory( getInitialInventory() );
        this.orderedProducts = new HashSet<>();
        this.boot();
    }

    /**
     * This will do a complete shutdown the coffee vending machine, it terminate
     * its self and returnes to the executing terminal.
     */
    public void shutdown() throws IOException
    {
        if(this.screen != null )
        {
            this.screen.stopScreen();
        }
    }

    /**
     * Runs the main program loop of the coffee vending machine. After the
     * system is initiated by boot
     */
    public void run()
    {

    }

    public void printMainMenu() throws IOException
    {
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

        }
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
