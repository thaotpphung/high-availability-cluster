package p2p;

/**
* This class represents a Peer, including its relevant information and operations
* @author Robert Masek, Sua "Joshua" Lee, Thao Phung
* @version 3/16/2020
*/
public class Peer {
	private String IP;
	private long timeStamp;
	private boolean isActive;
	private int flag;
	private double version;
	private String reserved;
	
	/**
	 * constructor for Peer
	 * @param IP IP address of this peer
	 * @param timeStamp the time when the peer's iP was received
	 */
	public Peer(String IP, long timeStamp)
	{
		this.IP = IP;
		this.timeStamp = timeStamp;
		isActive = false;
		this.flag = 0; // 0 is server-client mode
		this.version = 1.0; // this is the first version
		this.reserved = null; // for future development
	}

	/**
	 * get the IP address of this peer
	 * @return the IP address of this peer
	 */
	public String getIPAddress()
	{
		return IP;
	}
	
	/**
	 * update the time stamp for this peer
	 * @param timeStamp the new time stamp of this peer
	 */
	public void updateTimeStamp(long timeStamp)
	{
		this.timeStamp = timeStamp;
	}
	
	/**
	 * get the time stamp of this peer
	 * @return the time stamp of this peer
	 */
	public long getTimeStamp()
	{
		return timeStamp;
	}
	
	/**
	 * get the active status of this peer
	 * @return true if the peer is active, false otherwise
	 */
	public boolean getStatus()
	{
		return isActive;
	}
	
	/**
	 * update the active status for this peer
	 * @param status the new status of this peer
	 */
	public void updateStatus(boolean status)
	{
		isActive = status;
	}
	
	/**
	 * @return the string that has the peer IP address and its active status
	 */
	public String toString()
	{
		return new String("Peer " + IP +  " " + (isActive ? "Active" : "Inactive"));
	}
}