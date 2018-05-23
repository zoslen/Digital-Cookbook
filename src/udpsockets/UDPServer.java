package udpsockets;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.crypto.Mac;

/**
 * UDPServer / this class is the server terminal of the UDP exercise
 * @author QingDerui
 * @version 1.0
 *
 */
public class UDPServer {
     
	public static void main(String[] args) throws Exception{
		
		/*
		 * To receive the question
		 */
		// create a UDP socket and its port is 12000 (the start or end point)
		DatagramSocket socket = new DatagramSocket(12000);
		
		// create the data package
		
		byte[] data = new byte[1024]; //this shows how big one data package is
		
		DatagramPacket packet = new DatagramPacket(data, data.length);
		
		socket.receive(packet); // waiting the data coming
		
		//read the data by byte
		String problem = new String(data, 0, packet.getLength()); 
		
		//test whether the server receive the message from Server
		System.out.println("Client says: " + problem);
		
		
		/*
		 * To send the answer as the feedback
		 */
		//calculating
		UDPServer udpServer = new UDPServer();
		byte[] feedback = udpServer.calculate(problem);
	    
		// Send
		DatagramPacket result = new DatagramPacket( feedback, feedback.length, packet.getAddress(), packet.getPort() );
		socket.send(result);
		
			
	}
	
	/**
	 * This method helps calculate the 
	 * @param problem / the request given by the client
	 * @return the result of the calculation
	 */
	public byte[] calculate( String problem ){
		
		float num1,num2,result = 0;
		
		// Using the matcher to get the number and the operating symbol and store them into a list
		ArrayList<String> coefficients = new ArrayList<String>();
		StringBuffer sBuffer = new StringBuffer(problem);
		
		// Matching all the number and operating symbol in the question string
		Pattern pattern = Pattern.compile("[0-9]+|\\+|\\-|\\*|\\/|\\%");
		Matcher matcher = pattern.matcher(sBuffer);
		
		// start matching
		while(matcher.find()){
			
			String a = matcher.group() ;
			coefficients.add(a);

			
		}
		
		num1 = Integer.parseInt(coefficients.get(0));
		num2 = Integer.parseInt(coefficients.get(2));
		String string = coefficients.get(1);
		
		// calculating
		switch(string){
		case "-":
			
			result = num1 - num2;
			break;
		
		case "+":
			
			result = num1 + num2;
			break;
		
		case "*":
			
			result = num1 * num2;
			break;
		
		case "%":
		
		case "/":
			
			result = ( num1 / num2 );
			break;		
			
		}
		
		//Only take 2 decimals after the integer
		NumberFormat nFormat=NumberFormat.getNumberInstance(); 
	    nFormat.setMaximumFractionDigits(2);
		
	    byte[] answer= (problem + "=" + nFormat.format(result)).getBytes();
		return answer;
	}
	
}

