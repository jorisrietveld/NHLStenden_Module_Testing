package com.CofeeVendingMachine;


import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.gui2.BasicWindow;
import com.googlecode.lanterna.gui2.MultiWindowTextGUI;
import com.googlecode.lanterna.gui2.Window;
import com.googlecode.lanterna.gui2.WindowBasedTextGUI;
import com.googlecode.lanterna.gui2.dialogs.ActionListDialog;
import com.googlecode.lanterna.gui2.dialogs.ActionListDialogBuilder;
import com.googlecode.lanterna.gui2.dialogs.MessageDialogBuilder;
import com.googlecode.lanterna.gui2.dialogs.MessageDialogButton;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;
import com.sun.org.apache.xpath.internal.operations.Or;

import java.io.*;
import java.math.BigDecimal;
import java.net.SocketException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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

    private BasicWindow window;

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

        // Set virtual effects of the terminal
        terminal.setForegroundColor( TextColor.ANSI.GREEN );
        terminal.setBackgroundColor( TextColor.ANSI.BLACK );

        terminal.enterPrivateMode();

        // Create a appropriate screen (UnixTerminal, SwingTerminal or even
        // Telnet terminal) for displaying output to user.
        this.screen = new TerminalScreen( terminal );

        // Get the graphical user settings for the GUI.
        this.textGraphics = screen.newTextGraphics();

        // Everything is setup so activate the screen.
        this.screen.startScreen();

        // Add a gui that supports multiple windows to the screen.
        this.textGUI = new MultiWindowTextGUI( screen );

        // Create window to hold the panel
        this.window = new BasicWindow();

        this.window.setHints(Arrays.asList(Window.Hint.FULL_SCREEN));
    }

    /**
     * Reboots the system to its initial state, the inventory and product list
     * are in memory so get wiped on a reboot.
     */
    public void reboot()
    {
        try
        {
            this.terminal.exitPrivateMode();
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
    public void run() throws IOException
    {
        this.terminal.flush();
        this.screen.refresh();
        this.textGUI.waitForWindowToClose( this.window );

        // Print the main menu
        this.initialStartupMode();
    }

    /**
     * This mode enables the user to select between maintainers mode and the
     * beverage ordering mode.
     */
    public void initialStartupMode()
    {
        new ActionListDialogBuilder().setTitle( "==[ Coffee vending machine ]==" )
                                     .setDescription( "Please choose one of the following actions:" )
                                     .setCloseAutomaticallyOnAction( true )
                                     .setCanCancel( false )
                                     .addAction( "Order a beverage.", this::orderBeverageMode )
                                     .addAction( "Maintainers mode.", this::maintainersMode )
                                     .build()
                                     .showDialog( this.textGUI );
    }

    /**
     * This mode enables the vending machine maintainers to add new products,
     * resupply, reconfigure, reboot or shutdown the machine.
     */
    public void maintainersMode()
    {
        new ActionListDialogBuilder().setTitle( "==[ Maintainers Mode ]==" )
                                     .setDescription( "Please choose one of the following actions:" )
                                     .setCloseAutomaticallyOnAction( true )
                                  /*   .addAction( "Add new inventory.", this::selectInventoryToAddMode )*/
                                     .addAction( "Resupply the inventory.", this::maintainersMode )
                             /*        .addAction( "Enable/Disable payment methods.", this::maintainersMode )*/
                                     .addAction( "Reboot", this::reboot )
                                     .addAction( "Shutdown (exit the simulator)", this::shutdown )
                                     .build()
                                     .showDialog( this.textGUI );
    }

    /**
     * Creates an action menu for selecting a available beverage.
     */
    public void orderBeverageMode()
    {
        ActionListDialogBuilder menuBuilder = new ActionListDialogBuilder();

        for ( Beverage beverage : this.inventory.getBeverages() )
        {
            menuBuilder.addAction( beverage.getName(), () -> this.beverageAdditionsMode( beverage ) );
        }
        menuBuilder.setTitle( "==[ Order A Beverage ]==" )
                   .setDescription( "Please select the beverage you want to order:" )
                   .setCloseAutomaticallyOnAction( true )
                   .build()
                   .showDialog( this.textGUI );
    }

    /**
     * Creates an action menu for available beverage additions, for the previously
     * chosen beverage.
     *
     * @param forProduct The product to add the ingredients to.
     */
    public void beverageAdditionsMode( Beverage forProduct )
    {
        ActionListDialogBuilder menuBuilder = new ActionListDialogBuilder();

        for ( Addition addition : this.inventory.getAdditions( forProduct ) )
        {
            menuBuilder.addAction( addition.getName(), () -> this.orderProduct( addition ) );
        }
        menuBuilder.setTitle( "==[ Beverage Customization ]==" )
                   .setDescription( "Add to your beverage:" )
                   .setCloseAutomaticallyOnAction( true )
                   .addAction( "Go back to the previous menu", this::initialStartupMode )
                   .build()
                   .showDialog( this.textGUI );
    }

    /**
     * @param product
     */
    public void orderProduct( Orderable product )
    {
        // todo store order
        //todo subtract from inventory
    }

    /**
     * Prints a product list of all products so the maintainer can choose what
     * products he wants to refill.
     */
    public void selectInventoryToAddMode()
    {
        ActionListDialogBuilder menuBuilder = new ActionListDialogBuilder();

        for ( Orderable product : this.inventory.getAll() )
        {
            menuBuilder.addAction( product.getName(), () -> this.addInventoryMode( product ) );
        }
        menuBuilder.setTitle( "==[ Resupply to inventory ]==" )
                   .setDescription( "Select the product you want to resupply:" )
                   .setCloseAutomaticallyOnAction( true )
                   .addAction( "Go back to the previous menu", this::maintainersMode )
                   .build()
                   .showDialog( this.textGUI );
    }

    /**
     * Enables the maintenance staff to resupply "Units" of a product to the
     * inventory. It creates number-input dialog asking for the amount.
     */
    public void addInventoryMode(Orderable product)
    {

    }

    /**
     * Enables the maintenance staff to add new products to the machines inventory.
     */
    public void addNewProduct()
    {

    }

    /**
     * Enables the user to select the payment method he or se prefers for
     * completing his order.
     */
    public void paymentSelectionMode()
    {

    }

    /**
     * Enables the user to complete him or his order by starting a transaction
     * between the payment method and vending machine.
     */
    public void completeOrderMode(PaymentMethod method)
    {
        // Todo switch between cash and networked payments.

        //todo implement a cash payment menu? that counts down and lets the user
        //      select what coin to use.

        // Todo implement a networked payment menu.

        // Todo Call check availability to check if the payment API is available.
        //  - print error dialog
        //  - Do payment and notify the user about the success.
    }

    /**
     * Notifies a user about a event.
     * @param message Info, debug, warnings that are not errors.
     */
    public void notifyMessage( String message )
    {
        new MessageDialogBuilder()
                .setTitle("==[ Notice ]==")
                .setText(message)
                .addButton( MessageDialogButton.OK )
                .build()
                .showDialog(textGUI);
    }

    /**
     * Notifies a user about a error occurrence.
     * @param message Warnings, errors and if unlucky fatal's.
     */
    public void errorMessage( String message )
    {
        new MessageDialogBuilder()
                .setTitle("==[ An error occurred ]==")
                .setText(message)
                .addButton(MessageDialogButton.Retry)
                .addButton(MessageDialogButton.Continue)
                .addButton(MessageDialogButton.Abort )
                .build()
                .showDialog(textGUI);
    }



    public void addInventory( Orderable product, Integer amount )
    {
        inventory.addProduct( product, amount );
    }

}
