package com.CofeeVendingMachine;

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * This class abstracts all payment methods that require network access to
 * complete the transaction.
 */
public abstract class NetworkPayment implements PaymentMethod
{
    private URL paymentAPI;
    private HttpURLConnection urlConnection;
    private Map<String, Object> requestParameters = new LinkedHashMap<>();
    private int contentLength;

    public NetworkPayment( URL paymentAPI )
    {
        this.paymentAPI = paymentAPI;
    }

    public NetworkPayment()
    {
    }

    private void openConnection() throws IOException
    {
        if ( this.urlConnection == null )
        {
            this.urlConnection = (HttpURLConnection) paymentAPI.openConnection();
        }
        this.urlConnection.setDoInput(true); // true indicates the server returns response*/
    }

    private byte[] preparePostData()
    {
        StringBuilder postData = new StringBuilder();

        requestParameters.forEach( ( key, value ) ->
                {
                    if ( postData.length() != 0 )
                    {
                        postData.append( '&' );
                    }
                    postData.append( key );
                    postData.append( '=' );
                    postData.append( String.valueOf( value ) );
                }
                                 );

        return postData.toString().getBytes( StandardCharsets.UTF_8 );
    }

    protected void addRequstParameter( String name, Object data )
    {
        this.requestParameters.put( name, data );
    }

    protected void addRequstParameters( Map<String, Object> requestParameters )
    {
        requestParameters.forEach( ( k, d ) -> this.requestParameters.put( k, d ) );
    }

    protected boolean callPaymentAPI()
    {
        try
        {
            this.openConnection();
            byte[] postData = this.preparePostData();
            urlConnection.setDoOutput( true );
            urlConnection.setRequestProperty( "charset", "utf-8" );
            urlConnection.setRequestProperty( "Content-Type:", "application/json" );
            urlConnection.setRequestProperty( "Accept:", "application/json" );
            urlConnection.setRequestProperty("Content-Length", String.valueOf(postData.length));
            urlConnection.setRequestMethod( "POST" );


            urlConnection.getOutputStream().write( postData );
            Reader responseReader = new BufferedReader( new InputStreamReader( urlConnection.getInputStream(), StandardCharsets.UTF_8 ) );
            StringBuilder sb = new StringBuilder();
            int c;
            while ( (c = responseReader.read()) >= 0 )
            {
                sb.append((char)c );
            }
            String response = sb.toString();
        }
        catch ( Exception exception )
        {
            // Todo: log the exception.
        }
        finally
        {
            // Todo: close all buffers.
        }
        return false;
    }

    /**
     * Checks if anny of the vending machines network interfaces has internet
     * access.
     *
     * @return The result of the check.
     */
    protected Boolean checkNetworkAvailability() throws IOException
    {
            // Get all the network interfaces that are available on the system.
            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
            while ( interfaces.hasMoreElements() )
            {
                NetworkInterface networkInterface = interfaces.nextElement();

                // If there is a available connection on any interface return.
                if ( networkInterface.isUp() )
                {
                    return true;
                }
            }

        return false;
    }

}
