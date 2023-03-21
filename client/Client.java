import java.net.*;
import java.io.*;
import java.util.*;

public class Client 
{
	// client socket 
	private Socket socket = null;
	private PrintWriter socketOutput = null; 
	private BufferedReader socketInput = null;
	
	public void connect(String args) {
		try {
			// initialized socket
			socket = new Socket("localhost", 6420);
			// buffered reader reads in all characters in input stream
			socketInput = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			// allows client to write to server side
			socketOutput = new PrintWriter(socket.getOutputStream(), true);

			// writing the arguments to server
			socketOutput.println(args);

			// read the first line of input from the server
			String itemString = socketInput.readLine(); 

			// prints out all lines of input until it is empty
			while(itemString != null){
				System.out.println(itemString);
				itemString = socketInput.readLine();
			}

			// closes BufferedReader and the socket
			socketInput.close();
			socket.close();
		} 
		catch (IOException e){
		System.out.println( e );
		}
	}

	public static void main( String[] args )
	{
		// does validation on the arguments pased in by the user
		if (args.length == 0) { // if user passes in nothing
			System.out.println("Invalid Arguments");
			System.exit(1);
		}
		// if its not one of the valid commands then it exits
		else if (!args[0].equals("show") && !args[0].equals("bid") && !args[0].equals("item")){
			System.out.println("Invalid Arguments");
			System.exit(1);
		}
		String result = String.join(" ", args);
		// creates client object and calls connect with user arguments passed
		Client client = new Client();
		client.connect(result);
	}
}