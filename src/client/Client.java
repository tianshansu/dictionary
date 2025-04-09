package client;

import java.net.*;
import java.io.*;
import constants.*;
import exceptions.*;

public class Client {

	public static void main(String[] args) {
		
	    Socket s1;
	    int portNum=0;
	    String serverAddr="";
	    
	    // Check whether the argument is correct (both content and format)
	    if(args.length != 2) {
	    	exitWithErrorMsg(ClientConstant.ARGUMENT_INCORRECT_LENGTH);
	    }
	    try {
	    	serverAddr=args[0];
		    InetAddress.getByName(serverAddr);
		    
	    	portNum=Integer.parseInt(args[1]); 
	    	if(portNum<1024 || portNum>65535) {
	    		throw new PortOutOfRangeException(ClientConstant.PORT_NUMBER_INCORRECT_RANGE);
	    	}
	    }catch(NumberFormatException numberFormatException) {
	    	exitWithErrorMsg(ClientConstant.PORT_NUMBER_INCORRECT_FORMAT);
	    }catch(PortOutOfRangeException portOutOfRangeException) {
	    	exitWithErrorMsg(ClientConstant.PORT_NUMBER_INCORRECT_RANGE);
	    }catch(UnknownHostException unknownHostException) {
	    	exitWithErrorMsg(ClientConstant.SERVER_ADDRESS_INCORRECT);
	    }
	    	
	    //create client socket and new client socket handler thread and UI
	    try {
	    	//create client socket
	    	s1 = new Socket(serverAddr,portNum);
	    	
	    	//create clientSocketHandler, to send and receive data from server
	    	ClientSocketHandler clientSocketHandler=new ClientSocketHandler(s1);
	    	
	    	//generate the client UI
	    	ClientUI clientUI=new ClientUI(clientSocketHandler);
	    	clientSocketHandler.setClientUI(clientUI);
	    	clientUI.start();
	    	
	    	Thread socketHandlerThread = new Thread(clientSocketHandler); //create a new thread for the client socket handler
	    	socketHandlerThread.start(); //start the thread
	    	
	    	
	    }catch(ConnectException connectException) {
	    	exitWithErrorMsg(ClientConstant.SERVER_ADDRESS_UNAVAILABLE);
	    }catch (IOException e) {
	    	System.out.println("IOException occurred: " + e.getMessage());
		}
	}
	
	/**
	 * Exit the program with an error message
	 * @param msg error message
	 */
	private static void exitWithErrorMsg(String msg) {
		System.out.println(msg);
		System.exit(1);
	}

}
