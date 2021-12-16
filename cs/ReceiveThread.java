package cs;

import java.net.DatagramSocket;
import java.net.DatagramPacket;
import java.net.SocketException;
import java.io.IOException;

/**
 * A Receiver Thread to receive IP address and server status
 * @author Robert Masek, Sua "Joshua" Lee, Thao Phung 
 * @version 16 March 2020
 */
public class ReceiveThread implements Runnable {
	private HostList hostList;
	private DatagramSocket socket;

	/**
	 * constructor for Receive Thread
	 * @param hostList the list of hostList
	 */
	public ReceiveThread(HostList hostList) {
		this.hostList = hostList;
		socket = hostList.getSocket();
	}
	
	/**
	 * run method of the Thread
	 */
	public void run() {
		try {
			byte[] incomingData;
			while (true) {
				// receive host's information from sender
				incomingData = new byte[1024];
				DatagramPacket incomingPacket = new DatagramPacket(incomingData, incomingData.length);
				socket.receive(incomingPacket);
				String messageReceived = new String(incomingPacket.getData());
				
				// array of info of the host received
				String[] infoList = messageReceived.split(" ");
				try {
					String receivedIP = infoList[0];
					String isServer = infoList[1];
					String isActive = infoList[2];
					
					// get the sender Host object
					String senderIP = incomingPacket.getAddress().toString().substring(1);
					Host sendHost = hostList.getHost(hostList.getHostbyIP(senderIP));
					// get the received Host object
					Host receivedHost = hostList.getHost(hostList.getHostbyIP(receivedIP));
					
					// update the server status of the receiving packages
					if (isServer.startsWith("true")) {
						receivedHost.updateServerStatus(true);
					} else {
						receivedHost.updateServerStatus(false);
					}
					
					// if the sender is the server
					if (sendHost.getServerStatus())
					{
						// if the receiving package is not a server 
						// this means that the sender is a server and this host is a client, the received package is another client
						// update the active status of the received client only
						if (!receivedHost.getServerStatus())
						{
							receivedHost.updateActiveStatus((isActive.startsWith("true")) ? true : false); 
						}
						// else, the received package is the server
						else { 
							// check if sender is received host, update both the time stamp and active status of the server accordingly
							if (sendHost.getIPAddress().equals(receivedHost.getIPAddress()))
							{
								receivedHost.updateTimeStamp(System.currentTimeMillis()); 
								receivedHost.updateActiveStatus(true); 
							} 
							// else, there are 2 server, this is not valid, remove 1 server
							else { 
								receivedHost.updateServerStatus(false);
							}
						}
					}
					// if the sender is the client, update both the client's time stamp and active status
					else {
						receivedHost.updateTimeStamp(System.currentTimeMillis()); 
						receivedHost.updateActiveStatus(true); 
					}
					
				} catch (IndexOutOfBoundsException e) {
					System.out.println("Data was corrupt, wait for sender to resend..");
				}
			}
		} catch (SocketException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}