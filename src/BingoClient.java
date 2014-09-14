/**
 * BingoClient- client to be run in command line which
 * connects to a bingo server and plays the game.
 * 
 * @author C16Eric.Wardner - 27 Aug 2014
 */
import java.io.IOException;
import java.net.*;

class BingoClient {
	public static void main(String args[]) {
		//initialize default ip and port
		String ip = "DFCS2";
		int port = 12345;

		//if there is an argument passed at runtime
		if (args.length > 0) {
			//if the argument is help output manual
			if (args[0].equalsIgnoreCase("help")
					|| args[0].equalsIgnoreCase("-help")) {
				printHelp();
			}
			//set arguments to ip and port variable then run client with arguments
			else if (args[0].equalsIgnoreCase("-ip")
					&& args[2].equalsIgnoreCase("-p")) {
				
				ip = args[1];
				port = Integer.parseInt(args[3]);
				
				try {
					conectToServer(ip, port);
				} catch (SocketTimeoutException e1) {
					System.out.println("Server not responding! Quitting Game...");
				} catch (IOException e2) {
					System.out.println("Server does not exist! please try again with a different server address");
				} catch (IllegalArgumentException e3){
					System.out.println("port number is invalid! Try again.");
				}
				
			} else {
				printHelp();
			}
		}else{
			//no arguments passed at runtime
		try {
			conectToServer(ip, port);
		} catch (SocketTimeoutException e1) {
			System.out.println("Server not responding! Quitting Game...");
		} catch (IOException e2) {
			System.out.println("Server does not exist! please try again with a different server address");
		} catch (IllegalArgumentException e3){
			System.out.println("port number is invalid! Try again.");
		}
		}
	}

	/**
	 * Connects to the specified server to play the Bingo game
	 * @param ip - String, the ip address to connect to
	 * @param port - int, the port number to connect to.
	 * @throws IOException
	 * @throws SocketTimeoutException - Timeout is set at 15 seconds
	 */
	private static void conectToServer(String ip, int port) throws IOException,
			SocketTimeoutException {
		// TODO Auto-generated method stub
		DatagramSocket clientSocket = new DatagramSocket();
		//sets timeout to 15 seconds
		clientSocket.setSoTimeout(15000);
		//sets ip address from input
		InetAddress IPAddress = InetAddress.getByName(ip);
		byte[] sendData = new byte[128];
		byte[] receiveData = new byte[128];
		Boolean playing = false;

		String input = "joingame";
		sendData = input.getBytes();
		DatagramPacket sendPacket = new DatagramPacket(sendData,
				sendData.length, IPAddress, port);
		clientSocket.send(sendPacket);

		DatagramPacket receivePacket = new DatagramPacket(receiveData,
				receiveData.length);
		clientSocket.receive(receivePacket);
		String serverData = new String(receivePacket.getData());
		System.out.println(serverData);

		if (serverData.contains("playeraccepted")) {
			playing = true;

		}

		while (playing) {
			//get data from server
			clientSocket.receive(receivePacket);
			//make data a string and print to user
			serverData = new String(receivePacket.getData());
			System.out.println(serverData);
			//check if game is over
			if (serverData.contains("You did not win. Sorry!")
					|| serverData.contains("You won!")) {
				playing = false;
			}
		}
		
		clientSocket.close();
	}

	/**
	 * Outputs options for BingoClient program to connect with server
	 */
	private static void printHelp() {
		// TODO Auto-generated method stub
		System.out.println("\nUsage: BingClient [-ip] [-p]\n\n" + "Options:\n"
				+ "\t-ip\t\tSpecify the IP address to connect to.\n"
				+ "\t-p\t\tSpecify port to connect to\n");
	}

}
