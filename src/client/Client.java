/**
 * Name: Tianshan Su
 * Student ID: 875734
 */
package client;

import java.net.*;
import java.util.Locale;

import javax.swing.JOptionPane;

import java.io.*;
import constants.*;
import exceptions.*;

/**
 * Client Class
 * Validates command-line arguments, creates a socket connection to the server
 * Initializes the UI, and starts a thread for handling socket communication
 */
public class Client {

	/**
	 * The entry point of the client application
	 * checks the validity of command-line arguments (server address and port),
	 * establishes a socket connection to the server, initializes the UI, and starts
	 * a separate thread for handling socket communication.
	 * @param args args[0]:server address; args[1]:port number
	 */
	public static void main(String[] args) {
		
	    Socket s1;
	    int portNum=0;
	    String serverAddr="";
	    ClientUI clientUI;
	    //set environment to English(for display error msg)
	  	Locale.setDefault(Locale.US);
	    
	    // Check whether the argument is correct (both content and format)
	    if(args.length != 2) {
	    	JOptionPane.showMessageDialog(null, ClientConstant.ARGUMENT_INCORRECT_LENGTH,"Error",JOptionPane.ERROR_MESSAGE);
	    	System.exit(1);
	    }
	    try {
	    	serverAddr=args[0];
		    InetAddress.getByName(serverAddr);
		    
	    	portNum=Integer.parseInt(args[1]); 
	    	if(portNum<1024 || portNum>65535) {
	    		throw new PortOutOfRangeException();
	    	}
	    }catch(NumberFormatException numberFormatException) {
	    	JOptionPane.showMessageDialog(null, ClientConstant.PORT_NUMBER_INCORRECT_FORMAT,"Error",JOptionPane.ERROR_MESSAGE);
	    	System.exit(1);
	    }catch(PortOutOfRangeException portOutOfRangeException) {
	    	JOptionPane.showMessageDialog(null, ClientConstant.PORT_NUMBER_INCORRECT_RANGE,"Error",JOptionPane.ERROR_MESSAGE);
	    	System.exit(1);
	    }catch(UnknownHostException unknownHostException) {
	    	JOptionPane.showMessageDialog(null, ClientConstant.SERVER_ADDRESS_INCORRECT,"Error",JOptionPane.ERROR_MESSAGE);
	    	System.exit(1);
	    }
	    	
	    //create client socket and new client socket handler thread and UI
	    try {
	    	//create client socket
	    	s1 = new Socket(serverAddr,portNum);
	    	
	    	//create clientSocketHandler, to send and receive data from server
	    	ClientSocketHandler clientSocketHandler=new ClientSocketHandler(s1);
	    	
	    	//generate the client UI
	    	clientUI=new ClientUI(clientSocketHandler);
	    	clientSocketHandler.setClientUI(clientUI);
	    	clientUI.start();
	    	
	    	Thread socketHandlerThread = new Thread(clientSocketHandler); //create a new thread for the client socket handler
	    	socketHandlerThread.start(); //start the thread
	    	
	    	
	    }catch(ConnectException connectException) {
	    	JOptionPane.showMessageDialog(null, ClientConstant.SERVER_ADDRESS_UNAVAILABLE,"Error",JOptionPane.ERROR_MESSAGE);
	    	System.exit(1);
	    }catch (IOException e) {
	    	System.out.println("IOException occurred: " + e.getMessage());
		}
	}

}
