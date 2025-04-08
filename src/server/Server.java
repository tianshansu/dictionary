package server;


import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicInteger;
import javax.swing.JOptionPane;
import constants.ServerConstant;
import exceptions.PortOutOfRangeException;
import services.DictionaryService;


public class Server {
	public static AtomicInteger currentUserCount = new AtomicInteger(0);
	
	

	public static AtomicInteger getCurrentUserCount() {
		return currentUserCount;
	}


	public static void setCurrentUserCount(AtomicInteger currentUserCount) {
		Server.currentUserCount = currentUserCount;
	}


	public static void main(String[] args){
		ServerSocket serverSocket=null;
		int portNum=0;
		String dictFileName;
		DictionaryService dictionaryService=null;
		
		//set environment to English(for display error msg)
		Locale.setDefault(Locale.US);
		
		
		// Check whether the argument is correct (both content and format)
	    if(args.length != 2) {
	    	exitWithErrorMsg(ServerConstant.ARGUMENT_INCORRECT_LENGTH);
	    }
	    try {
	    	portNum=Integer.parseInt(args[0]);//get the port number from command line
	    	if(portNum<1024 || portNum>65535) {
	    		throw new PortOutOfRangeException();
	    	}
	    	
	    	dictFileName=args[1];//get the dictionary file name from command line
	    	String filePath="src/"+dictFileName;
	    	File dictFile=new File(filePath);
	    	//if the dictionary file does not exist, exit the program
	    	if(!dictFile.exists()) {
	    		exitWithErrorMsg(ServerConstant.fILE_NOT_EXSIST);
	    	}
	    	
	    	dictionaryService=new DictionaryService(filePath);
	    	
	    }catch(NumberFormatException numberFormatException) {
	    	exitWithErrorMsg(ServerConstant.PORT_NUMBER_INCORRECT_FORMAT);
	    }catch(PortOutOfRangeException portOutOfRangeException) {
	    	exitWithErrorMsg(ServerConstant.PORT_NUMBER_INCORRECT_RANGE);
	    }
	    
	  //new a dictionary service and pass the file path to it
    	
    	ServerUI serverUI=new ServerUI(dictionaryService);
 	    serverUI.start();
 	    
 	    dictionaryService.setServerUI(serverUI); //pass server UI to service (for updating UI)
 	    dictionaryService.initiateWordsCount();
	    
		//receive connections from the clients
		try {
			serverSocket = new ServerSocket(portNum);
			System.out.println("Server is ready to receive connection!");
			while(true) {
				Socket clientSocket=serverSocket.accept(); // wait and accept a connection
				ClientConnectionHandler clientConnectionHandler=new ClientConnectionHandler(clientSocket);
				clientConnectionHandler.setDictionaryService(dictionaryService);
				clientConnectionHandler.setServerUI(serverUI);
				Thread t = new Thread(clientConnectionHandler); //when the connection is established, create a new thread for that client
                t.start(); //start the thread
                currentUserCount.incrementAndGet(); //add the user count
                serverUI.updateUserCount(currentUserCount.get());
			}
		} catch (IOException e) {
			System.out.println("IOException occurred: " + e.getMessage());
			currentUserCount.decrementAndGet(); //decrease the user count
            serverUI.updateUserCount(currentUserCount.get());
		}finally {
			try {
				serverSocket.close();
			} catch (IOException e) {
				System.out.println("IOException occurred: " + e.getMessage());
			}
		}
	}
	
	
	/**
	 * Exit the program with an error message
	 * @param msg error message
	 */
	private static void exitWithErrorMsg(String msg) {
		JOptionPane.showMessageDialog(null, msg,"Error",JOptionPane.ERROR_MESSAGE);
		System.exit(1);
	}

}
