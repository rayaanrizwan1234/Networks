import java.io.BufferedReader;
import java.nio.Buffer;
import java.net.ServerSocket;
import java.text.Bidi;
import java.time.LocalDate;
import java.time.LocalTime;
import java.net.*;
import java.io.*;
import java.util.*;

public class ClientHandler implements Runnable{
	// initializing variables
    private Socket clientSocket;
    private HashMap<String, Bid> items;
    private FileWriter logWriter;
    // reading in from client
    private BufferedReader input;
    // writing to client
    private PrintWriter writer;
	// constructor
    public ClientHandler(Socket clientSocket, HashMap<String, Bid> items, FileWriter logWriter) {
        this.clientSocket = clientSocket;
        this.items = items;
        this.logWriter = logWriter;
    }

	// method to show items
	public void show(){
		// if there are no items in the hashmap then it prints out that there are no items
		if (items.isEmpty()){
			writer.println("There are currently no items in this auction");
		}  
		else {
			// if there are items in the hashmap then it prints out the items
			for (Map.Entry<String, Bid> item : items.entrySet()) {
				writer.println(item.getKey() + " : " + item.getValue().getBid()+" : "+item.getValue().getBidder());
			}	
		}
	}

	// method to bid
	public void bid(String itemBid, int price){
		// validation to check if item exists
		if (items.containsKey(itemBid))
		{	
			// Check if price is valid
			if (price <= 0){
				writer.println("Failure");
			} else {
				// loops over whole hashmap
					for (Map.Entry<String, Bid> item : items.entrySet()) {
						// if we find the item
						if (item.getKey().equals(itemBid)){
							// if the price is less than or equal to the current bid then it prints out rejected
							if (price <= item.getValue().getBid()){
								writer.println("Rejected.");
							} else {
								// if the bid is valid then sets item price to the new price 
								item.getValue().setBid(price);
								InetAddress inetAddress = clientSocket.getInetAddress();
								String ipAddress = inetAddress.getHostAddress();
								// sets the bidder to the ip address of the client
								item.getValue().setBidder(ipAddress);
								// put it back into the hashmap
								items.put(itemBid, item.getValue());
								writer.println("Accepted.");
							}
						}
					}
				}

		} else { // if the item does not exist then it prints out failure
			writer.println("Failure");
		}
	}

	// method to add an item
	public void item(String item) {
		// validation to check if item exists
		if (items.containsKey(item)){
			writer.println("Failure");
		} else {
		// if the item does not exist then create a new bid with a price of 0 and a bidder of no bids
		Bid bid = new Bid(0, "<no bids>");
		// add item to hashmap
		items.put(item, bid);
		writer.println("Accepted.");
		}
	}

	// method to write to log file
	public void logWriting(String request){
		// gets the ip address of the client and the date and time
		InetAddress inet = clientSocket.getInetAddress();
		LocalDate date = LocalDate.now();
		LocalTime time = LocalTime.now();
		try {
			// writes to log file
			logWriter.write(date + "|" + time + "|" + inet.getHostAddress() + "|"+ request +"\n");
			logWriter.flush();	
		} catch (Exception e) { // if there is an error then it prints out the error
			System.err.println("Failed to write to log file: " + e.getMessage());
		}
	}

	// run method
    @Override
    public void run(){
        try{
			// while the client socket is not closed
			while(!clientSocket.isClosed()){

			// reading from client and writing to client
			input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			writer = new PrintWriter(clientSocket.getOutputStream(), true);

			// reading in client arguments
			String in = input.readLine();
			String[] inarr = in.split(" ");
			// if user requests to show items
			if (inarr[0].equals("show")){
				show();
				logWriting("show");
			}
			// if user requests to bid
			else if (inarr[0].equals("bid")){
				// gets item and price
				String itemBid = inarr[1];
				String priceString = inarr[2];
				int price = Integer.parseInt(priceString);
				bid(itemBid, price);
				logWriting("bid");
			}
			// if user requests to add an item 
			else {
				String item = inarr[1];
				item(item);
				logWriting("item");
			}		
		
			// close connections
            input.close();
			writer.close();
			clientSocket.close();
			}

        } catch (IOException e){
            System.err.println("I/O exception during execution\n");
            System.exit(1);
        }
    }
}
