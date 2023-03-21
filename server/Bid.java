public class Bid { // class to create a bid object
	// private variables
	private Integer bid;
	private String bidder;

	public Bid (Integer bid, String bidder){
		this.bid = bid;
		this.bidder = bidder;
	}

	// getters and setters
	public Integer getBid(){
		return bid;
	}

	public String getBidder(){
		return bidder;
	}

	public void setBid(Integer bid){
		this.bid = bid;
	}

	public void setBidder(String bidder){
		this.bidder = bidder;
	}
}
