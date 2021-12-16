package p2p;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

/**
 * A Receiver Thread to receive IP address of other peer
 * @author Robert Masek, Sua "Joshua" Lee, Thao Phung
 * @version 3/16/2020
 */
public class ReceiveThread implements Runnable
{
	private PeerList peer;
	private DatagramSocket socket;
	
	/**
	 * constructor for Receive Thread
	 * @param peer
	 */
	public ReceiveThread(PeerList peer)
	{
		this.peer = peer;
		this.socket = peer.getSocket();
	}
	
	/**
	 * run method for this Thread
	 */
	public void run()
	{
		try
		{
			byte[] incomingData = new byte[1024];
			int targetIndex;

        	while(true) 
        	{
        		// receive data from another peer
            	DatagramPacket incomingPacket = new DatagramPacket(incomingData, incomingData.length);
            	socket.receive(incomingPacket);
            
            	// get peer's IP address
            	InetAddress otherIP = incomingPacket.getAddress();
			                
            	// update received peer's time stamp and active status
            	targetIndex = peer.getPeerByIP(otherIP.toString().substring(1));
            	peer.updatePeerTimestamp(targetIndex, System.currentTimeMillis());
            	peer.updatePeerStatus(targetIndex, true);
        	}
		}
		catch (SocketException e) 
        {
            e.printStackTrace();
        } 
        catch (IOException i) 
        {
            i.printStackTrace();
        } 
	}
}