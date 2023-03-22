import java.net.*;
import java.io.*;
import java.util.*;

/*
 * This is a class for the clien. it establishes a connection with a server on port 6420. 
 * The main method validates user input before sending it to the server. then it reads the servers response
 * and prints it out. 
 */

public class Client {
	// client socket
	private Socket socket = null;
	private PrintWriter socketOutput = null;
	private BufferedReader socketInput = null;

	/*
	 * The connect method establishes a client-side connection to a server running
	 * on the same
	 * machine at port 6420. It creates a socket, initializes the input and output
	 * streams,
	 * writes the arguments to the server, reads and prints the server's response,
	 * and
	 * finally closes the input stream and the socket.
	 */
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
			while (itemString != null) {
				System.out.println(itemString);
				itemString = socketInput.readLine();
			}

			// closes BufferedReader and the socket
			socketInput.close();
			socket.close();
		} catch (IOException e) {
			System.out.println(e);
		}
	}

	public static void main(String[] args) {
		// does validation on the arguments pased in by the user
		if (args.length == 0) { // if user passes in nothing
			System.out.println("Invalid Arguments. Input 'show' or 'bid item price' or 'item nameOfItem'");
		}
		// if its not one of the valid commands then it exits
		else if (!args[0].equals("show") && !args[0].equals("bid") && !args[0].equals("item")) {
			System.out.println("Invalid Arguments. Input 'show' or 'bid item price' or 'item nameOfItem'");
		} else if (args[0].equals("show") && args.length != 1) {
			System.out.println("Invalid Arguments. Too many arguments");
		} else if (args[0].equals("bid") && args.length != 3) {
			if (args.length == 1) {
				System.out.println("Invalid Arguments. No item or price inputted");
			} else if (args.length == 2) {
				System.out.println("Invalid Arguments. No price inputted");
			} else {
				System.out.println("Invalid Arguments. Too many arguments");
			}
		} else if (args[0].equals("item") && args.length != 2) {
			if (args.length == 1) {
				System.out.println("Invalid Arguments. No item inputted");
			}
			System.out.println("Invalid Arguments. Too many arguments");
		} else {
			if (args[0].equals("bid")) {
				try {
					Double price = Double.parseDouble(args[2]);
				} catch (NumberFormatException e) {
					System.err.println(e.getMessage());
					System.exit(0);
				}
			}
			String result = String.join(" ", args);
			// creates client object and calls connect with user arguments passed
			Client client = new Client();
			client.connect(result);
		}
	}
}