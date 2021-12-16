package cs;

import java.net.*;
import java.util.ArrayList;

/**
 * A class representing the list of hosts, with relevant operations
 * @author Robert Masek, Sua "Joshua" Lee, Thao Phung
 * @version 16 March 2020
 */
public class HostList
{
	private ArrayList<Host> hostList;
	private DatagramSocket socket;
	
	/**
	 * constructor for HostList class
	 * create a socket and a List of hosts
	 */
	public HostList()
	{
		try
		{
			socket = new DatagramSocket(9876);
		}
		catch (SocketException e)
		{
			e.printStackTrace();
		}
		hostList = new ArrayList<Host>();
	}
	
	/**
	 * add a new Host to the list
	 * @param newHost the new host to be added
	 */
	public void addHost(Host newInfo)
	{
		hostList.add(newInfo);
	}
	
	/**
	 * search a host by its IP address
	 * @param IP IP of the desired host
	 * @return the index of the desired host in the list of hosts
	 */
	public int getHostbyIP(String IP)
	{
		int notFound = -1;
		
		for (int index = 0; index < hostList.size(); index++)
		{
			if (hostList.get(index).getIPAddress().equals(IP))
			{
				return index;
			}
		}
		
		return notFound;
	}
	
	/**
	 * get a host by its index
	 * @param index the index of the desired host
	 * @return the Host object at the given index in the list of hosts
	 */
	public Host getHost(int index)
	{
		return hostList.get(index);
	}
	
	/**
	 * get the IP address of the server 
	 * @return the IP address of the server 
	 */
	public String getServerIP()
	{
		for (int index = 0; index < hostList.size(); index++)
		{
			if (hostList.get(index).getActiveStatus() && hostList.get(index).getServerStatus())  
			{
				return hostList.get(index).getIPAddress();
			}
		}
		// if no one is the server, return the first active host in the list
		return getFirstActiveIP();
	}
	
	/**
	 * get the first active host in the list 
	 * @return the IP address of the first active host in the list
	 */
	public String getFirstActiveIP()
	{	
		String result = "";
		for (int index = 0; index < hostList.size(); index++)
		{
			if (hostList.get(index).getActiveStatus())
			{
				return hostList.get(index).getIPAddress();
			}
		}
		return result;
	}
	
	/**
	 * get the socket for the connection
	 * @return the socket for the connection
	 */
	public DatagramSocket getSocket()
	{
		return socket;
	}
	
	/**
	 * get the size of the list of host
	 * @return the size of the list of host
	 */
	public int getHostListSize()
	{
		return hostList.size();
	}
	
	/**
	 * get the host information
	 * @param index index of the desired host in the list
	 * @return the String containing the IP address of the host, its server status and active status
	 */
	public String getHostSummary(int index)
	{
		return new String(hostList.get(index).getIPAddress() + " " + getHostServerStatus(index) + " "
				+ getHostStatus(index));
	}
	
	/** 
	 * get the server status of the host
	 * @param index
	 * @return the String "server" if the host is the server, "client" otherwise
	 */
	public String getHostServerStatus(int index)
	{
		return hostList.get(index).getServerStatus() ? "Server" : "Client";
	}
	
	/**
	 * get the host active status 
	 * @param index
	 * @return the String "Active" if the host is active, "Inactive" otherwise
	 */
	public String getHostStatus(int index)
	{
		return hostList.get(index).getActiveStatus() ? "Active" : "Inactive";
	}
	
	/**
	 * display the information of all host in the list
	 */
	public  void displayList() {
		for (int index = 0; index < hostList.size(); index++)
		{
			System.out.println(getHostSummary(index));
		}
	}
}