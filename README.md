# Multi-threaded Dictionary Server
This is a university project developed for the Distributed Systems course.

A Java-based dictionary system built using a client-server architecture with TCP sockets and multithreading.  
The server supports concurrent client connections, and clients can search, add, update, or delete word entries through a Swing-based GUI application.

## Features
- TCP socket communication between client and server
- Multi-threaded server (thread-per-connection model)
- Custom message protocol for client-server interactions
- Real-time updates shared across all clients
- Swing-based GUI for user-friendly interaction
- Thread-safe in-memory dictionary management
- Error handling for invalid input and network issues

## Technologies
- Java
- TCP Sockets
- Java Threads
- Java Swing (GUI)

### Server
java -jar DictionaryServer.jar <port> <dictionary-file>

### Client
java -jar DictionaryClient.jar <server-address> <server-port>
