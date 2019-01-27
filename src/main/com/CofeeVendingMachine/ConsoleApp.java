package com.CofeeVendingMachine;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import static java.lang.System.in;

public class ConsoleApp
{
    /**
     * Constants that define the terminal output formats.
     */
    final static private String MENU_HEAD_FORMAT = "-----------[%1$22s]----------";
    final static private String MENU_OPT_FORMAT = "[%d] - %s";
    final static private String MENU_FOOT_FORMAT = "---------------------------------------------";

    /**
     * The buffer for reading users input.
     */
    private BufferedReader bufferedReader = new BufferedReader( new InputStreamReader( in ) );
}
