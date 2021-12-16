package p2p;

import java.net.*;
import java.util.ArrayList;

/**
 * A class representing the list of peers, with relevant operations
 * @author Robert Masek, Sua "Joshua" Lee, Thao Phung
 * @version 3/16/2020
 */
public class PeerList {
	private DatagramSocket socket = null;
	private ArrayList<Peer> peerList;
	
	/**
	 * constructor for PeerList, create socket and initialize the peer list
	 */
	public PeerList() 
	{
		try
		{
			this.socket = new DatagramSocket(9876);
		}
		catch (SocketException e) 
        {
            e.printStackTrace();
        }
		this.peerList = new ArrayList<Peer>();
	}
	
	/**
	 * add a new peer to the list of peer
	 * @param peer the new peer
	 */
	public void addPeer(Peer peer)
	{
		peerList.add(peer);
	}
	
	/**
	 * get the Peer object by its index
	 * @param index the index of the desired peer
	 * @return the desired Peer object 
	 */
	public Peer getPeer(int index)
	{
		return peerList.get(index);
	}
	
	/**
	 * update this peer's active status
	 * @param index the index of the peer that needs updating
	 * @param status the new status
	 */
	public void updatePeerStatus(int index, boolean status)
	{
		peerList.get(index).updateStatus(status);
	}
	
	/**
	 * update the peer's new time stamp
	 * @param index the index of the peer that need updating
	 * @param time the new time stamp
	 */
	public void updatePeerTimestamp(int index, long timeStamp)
	{
		peerList.get(index).updateTimeStamp(timeStamp);
	}
	
	/**
	 * get the index of a Peer in the list of peers, given its IP address
	 * @param IP the IP of the desired Peer
	 * @return the index of the desired Peer
	 */
	public int getPeerByIP(String IP)
	{
		int result = -1;	
		for (int index = 0; index < peerList.size(); index++)
		{
			if (peerList.get(index).getIPAddress().equals(IP))
			{
				return index;
			}
		}
		return result;
	}
	
	/**
	 * get the size of the list of peers
	 * @return
	 */
	public int getListSize()
	{
		return peerList.size();
	}
	
	/**
	 * get the socket for this connection
	 * @return
	 */
	public DatagramSocket getSocket()
	{
		return socket;
	}
	
	/**
	 * get the summary for this peer, including the Peer's IP address and its status
	 * @param index the index of the desired peer in the list of peers
	 * @return the String represents the information of the peer
	 */
	public String getPeerSummary(int index)
	{
		return peerList.get(index).toString();
	}
}