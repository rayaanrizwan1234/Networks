import java.net.ServerSocket;
import java.text.Bidi;
import java.net.*;
import java.io.*;
import java.util.*;
import java.util.concurrent.*;

public class Server 
{
	// created hashmap for items 
	private HashMap<String, Bid> items = new HashMap<String, Bid>(); // Creating a hashmap named 'items' to store bids

	private ServerSocket severSocket; // initializing the server socket

	public Server(){
		try{
			severSocket = new ServerSocket(6420); // creating the ServerSocket object on 6420
		}
		// if it is unable to work then it catches exception
		catch(IOException e) {
			System.err.println("Could not listen on port: 6420");
			System.exit(0);
		}
	}

	// method to run server 
	public void runServer() { 
		// Creating a new Executor object with a fixed thread pool of size 30
        Executor executor = Executors.newFixedThreadPool(30);
		// creating a new FileWriter object
		FileWriter logWriter = null; 

		try {
			// creates a log.txt files
			File file = new File("log.txt");
			// if the file does not exist then it creates a new file
			logWriter = new FileWriter(file.getName(), true);
		} catch (IOException e) {
			System.err.println("Could not create log file.");
		}

		try {
			// infinite loop
			while (true){
			// creating a new socket object
			Socket clientSocket = severSocket.accept();
			// prints out the port number of the client
            System.out.println("clientSocket port: " + clientSocket.getPort() );
			// executes the ClientHandler class
			executor.execute(new ClientHandler(clientSocket, items, logWriter));
			}
		}
		// if it is unable to work then it catches exception
		catch (IOException ex) {
			System.out.println(ex);
		} finally {
			try {
				if (logWriter != null) {
					logWriter.close();
				}
			} catch (IOException ex) {
				System.err.println("Failed to close log file: " + ex.getMessage());
			}
		}
	}

	public static void main( String[] args )
	{
		// creating a new Server object
		Server server = new Server();
		// running the server
		server.runServer();
	}
}
