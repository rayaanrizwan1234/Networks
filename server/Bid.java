/* Description: The Bid class is a class that creates a bid object with private variables for the bid amount and the bidder's IP address. 
It has getter and setter methods to get and set the bid and bidder variables. 
The class is used in the auction system to keep track of bids for different items. */

public class Bid { // class to create a bid object
	// private variables
	private Double bid;
	private String bidder;

	public Bid(Double bid, String bidder) {
		this.bid = bid;
		this.bidder = bidder;
	}

	// getters and setters
	// gets bid of class
	public Double getBid() {
		return bid;
	}

	// gets bidder of item
	public String getBidder() {
		return bidder;
	}

	// sets bid of item
	public void setBid(Double bid) {
		this.bid = bid;
	}

	// sets bidder of item
	public void setBidder(String bidder) {
		this.bidder = bidder;
	}
}
