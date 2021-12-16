package p2p;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

/**
 * Sender Thread to send notification to other peers
 * @author Robert Masek, Sua "Joshua" Lee, Thao Phung
 * @version 3/16/2020
 */
public class SendThread implements Runnable {
	private PeerList peerList;
	private DatagramSocket socket;
	private Random timer;
	private InetAddress myIP;

	/**
	 * constructor for SendThread
	 * @param peerList 
	 * @param myIP
	 */
	public SendThread(PeerList peerList, InetAddress myIP) {
		this.peerList = peerList;
		this.socket = peerList.getSocket();
		this.timer = new Random();
		this.myIP = myIP;
	}
	
	/**
	 * run method for this Thread
	 */
	public void run() {
		try {
			while (true) {
				// iterates through the list of peers and send my IP to them
				for (int index = 0; index < peerList.getListSize(); index++) {
					Peer currentPeer = peerList.getPeer(index);
					// check if the peer is still active, update its status accordingly
					if (currentPeer.getTimeStamp() <= System.currentTimeMillis() - 30000) {
						peerList.updatePeerStatus(index, false);
					}
					// print out the summary of the selective peer (IP address and status)
					System.out.println(peerList.getPeerSummary(index));
					
					// destination IP address is the IP address of current peer
					InetAddress destIP = InetAddress.getByName(currentPeer.getIPAddress());
					// send my IP to destination IP address
					byte[] data = myIP.toString().getBytes();
					DatagramPacket sendPacket = new DatagramPacket(data, data.length, destIP, 9876);
					socket.send(sendPacket);
				}

				System.out.print("Finished sending my IP to other peers at: ");
				DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
				LocalDateTime now = LocalDateTime.now();
				System.out.println(dtf.format(now) +"\n");

				// sleeps a random amount of time from 0-30 seconds before send again
				Thread.sleep(timer.nextInt(30000));
			}
		} catch (SocketException e) {
			e.printStackTrace();
		} catch (IOException i) {
			i.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}