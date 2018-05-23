package udpsockets;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import javafx.scene.chart.PieChart.Data;

/* What is the implementation difference on client- and on server-side in comparison to the TCP approach?
 * 
 * A: UDP uses DatagramSocket as the terminal of two sides and DatagramPacket to carry the data.
 * And in TCP data are passed through by stream , this will guarantee the safety of the transmission. 
 * UDP need to package the data(byte) into a DatagramPacket, and the DatagramPacket also contains the address and the port of the terminal.
 * UDP use send() and receive() method based on bytes , TCP use InputStream & OutputStream based on characters.
 */


/**
 * UDPClient / this class is the client side of UDP socket
 * @author Qing Derui
 * @version 1.0
 *
 */
public class UDPClient {

	public static void main(String[] args) throws Exception{
		
		
		// to specify the destination server address
		InetAddress address = InetAddress.getLocalHost();
		
		// create the data you want to send
		byte[] data = "20/6".getBytes();
		
		// create the sending package to send your data, include the address and port
		DatagramPacket packet = new DatagramPacket(data, data.length, address , 12000);
		
		
		// the socket point of client
		DatagramSocket socket = new DatagramSocket();
		
		// send the message to the server
		socket.send(packet);
		
		/*
		 * To receive the answer from the server
		 */
		byte[] result = new byte[1024];
		DatagramPacket answer = new DatagramPacket(result, result.length);
		socket.receive(answer);
		
		// turn the answer into string
		String answerStr = new String(result, 0, answer.getLength());
		System.out.println(answerStr);
		
		
	}
	
}
