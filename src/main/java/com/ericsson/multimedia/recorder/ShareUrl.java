package com.ericsson.multimedia.recorder;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class ShareUrl {
	
	private static String commonUrl = "http://localhost:3000/";
	
	

	public static String generateShareUrl() {
		InetAddress ip;
        String hostname;
        String url = null;
        
        try {
            ip = InetAddress.getLocalHost();
            hostname = ip.getHostName();
            System.out.println("Your current IP address : " + ip);
            System.out.println("Your current Hostname : " + hostname);
            url=commonUrl+"joinRoom?room="+hostname;
 
        } catch (UnknownHostException e) { 
            e.printStackTrace();
        }
		return url;
	}
	
	public static String generateHostName(){
		InetAddress ip;
        String hostname="default_room";
        try {
			ip = InetAddress.getLocalHost();
			hostname = ip.getHostName();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        return hostname;
 
	}
	
	public static String generateCreateRoomUrl() {
		String url = null;
		url=commonUrl+"createRoom?room="+generateHostName();
		return url;
	}
	
	public static void main(String[] args) {
		ShareUrl.generateShareUrl();
	}
	
}
