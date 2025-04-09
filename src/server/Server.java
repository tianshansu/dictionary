package server;


import java.io.File;
import java.io.IOException;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import javax.swing.JOptionPane;
import constants.ServerConstant;
import exceptions.PortOutOfRangeException;
import services.DictionaryService;

/**
 * The Server, responsible for handle client connections(start threads)
 */
public class Server {
	public static AtomicInteger currentUserCount = new AtomicInteger(0); //record current user count
	private static final Map<String, ClientConnectionHandler> userHandlers = new ConcurrentHashMap<>();//record all user connections
	private static ServerUI serverUI;

	/**
	 * get current user count
	 * @return current user count(int)
	 */
	public static AtomicInteger getCurrentUserCount() {
		return currentUserCount;
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
	    	
	    	 //new a dictionary service and pass the file path to it
	    	dictionaryService=new DictionaryService(filePath);
	    	
	    } catch(NumberFormatException numberFormatException) {
	    	exitWithErrorMsg(ServerConstant.PORT_NUMBER_INCORRECT_FORMAT);
	    }catch(PortOutOfRangeException portOutOfRangeException) {
	    	exitWithErrorMsg(ServerConstant.PORT_NUMBER_INCORRECT_RANGE);
	    }
	    
    	serverUI=new ServerUI(dictionaryService);
 	    serverUI.start();
 	    
 	    dictionaryService.setServerUI(serverUI); //pass server UI to service (for updating UI)
 	    dictionaryService.initiateWordsCount();
	    
		//receive connections from the clients
		try {
			serverSocket = new ServerSocket(portNum);
			System.out.println("Server is ready to receive connection!");
			while(true) {
				Socket clientSocket=serverSocket.accept(); // wait and accept a connection
				String userId = UUID.randomUUID().toString().substring(0, 8);
				ClientConnectionHandler clientConnectionHandler=new ClientConnectionHandler(clientSocket,userId);
				userHandlers.put(userId, clientConnectionHandler);//add the user id and connection to map
				clientConnectionHandler.setDictionaryService(dictionaryService);
				clientConnectionHandler.setServerUI(serverUI);
				Thread t = new Thread(clientConnectionHandler); //when the connection is established, create a new thread for that client
                t.start(); //start the thread
                currentUserCount.incrementAndGet();
                serverUI.updateUserCount(currentUserCount.get());//update the user count in UI
               
                Set<String> connectedUserIds = userHandlers.keySet();
                serverUI.refreshUsers(new HashSet<>(connectedUserIds)); 
			}
		} catch (BindException e) {
	        //check if there is already a server running
	        System.out.println("Port " + portNum + " is already in use. Server might already be running.");
	        JOptionPane.showMessageDialog(null, "The server is already running!", "Error", JOptionPane.ERROR_MESSAGE);
	        System.exit(1);
	    }catch (IOException e) {
			System.out.println("IOException occurred: " + e.getMessage());
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

	/**
	 * remove client connection(either when server manually remove or client actively remove)
	 * @param userId the id of the user to be removed
	 * @param clientDisconnects true if the client actively disconnects
	 */
	public static synchronized void removeConnection(String userId,boolean clientDisconnects) {
        ClientConnectionHandler handler=userHandlers.get(userId);
        if (handler == null) {
            System.out.println("Cannot find handler for userId: " + userId);
            return;
        }
        
        //check whether client actively disconnects or the server choose to disconnect the client
        if(clientDisconnects) {
        	try {
                handler.getClientSocket().close();
            } catch (IOException e) {
                System.out.println("Socket already closed for userId: " + userId);
            }
        }else {
        	try {
    			handler.getOutputStream().writeUTF("SERVER_SHUTDOWN");
    	        handler.getOutputStream().flush();
    			handler.getClientSocket().close();
    		} catch (IOException e) {
    			System.out.println("Client connection remove failed!");
    		} 
        }
		
		userHandlers.remove(userId);
        serverUI.refreshUsers(new HashSet<>(userHandlers.keySet())); 
        
    }
}
