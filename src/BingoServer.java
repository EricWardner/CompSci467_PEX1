import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.ArrayList;

public class BingoServer {

	ArrayList<Client> clients = new ArrayList<Client>(1000);
	ArrayList<Client> clients2 = new ArrayList<Client>(1000);

	public BingoServer() {

		try {
			DatagramSocket s = new DatagramSocket(2016);

			while (clients.size() < 2) {
				try {
					waitingLobby(s);
				} catch (Exception e) {
					//System.out.println("waiting for players");
				}
			}

			while (true) {
				clients2.addAll(clients);
				ClientPlayer gamePlayer = new ClientPlayer(clients2, s);
				Thread gameThread = new Thread(gamePlayer);

				if (clients.size() >= 2) {

					gameThread.start();

					clients.clear();
				} else {
					try {
						//System.out.println("waiting for more players");
						waitingLobby(s);
					} catch (IOException e) {
						// TODO Auto-generated catch block

					}
				}
				while (gameThread.isAlive()) {
					try {
						//System.out.println("waiting while alive");
						waitingLobby(s);
					} catch (IOException e) {
						// TODO Auto-generated catch block

					}
				}
				clients2.clear();

			}
		} catch (SocketException e1) {
			// TODO Auto-generated catch block
			System.out.println("socket unavailable");
		}

	}

	public void waitingLobby(DatagramSocket s) throws IOException,
			NullPointerException {
		String playerAccepted = "playeraccepted";
		String error = "error";
		String gameisfull = "gameisfull";
		s.setSoTimeout(16);
		byte[] recvData = new byte[512];
		DatagramPacket recvPacket = new DatagramPacket(recvData,
				recvData.length);
		s.receive(recvPacket);
		String clientData = new String(recvPacket.getData());
		byte[] sendData = new byte[512];

		if (clientData.contains("joingame") && clients.size() < 1000) {
			Client player = new Client(recvPacket.getAddress(),
					recvPacket.getPort());

			sendData = playerAccepted.getBytes();
			DatagramPacket sendPacket = new DatagramPacket(sendData,
					sendData.length, recvPacket.getAddress(),
					recvPacket.getPort());
			s.send(sendPacket);

			clients.add(player);
		} else if (!clientData.contains("joingame")) {
			sendData = error.getBytes();
			DatagramPacket sendPacket = new DatagramPacket(sendData,
					sendData.length, recvPacket.getAddress(),
					recvPacket.getPort());
			s.send(sendPacket);
		} else if (clients.size() >= 1000) {
			sendData = gameisfull.getBytes();
			DatagramPacket sendPacket = new DatagramPacket(sendData,
					sendData.length, recvPacket.getAddress(),
					recvPacket.getPort());
			s.send(sendPacket);
		}

	}

	public static void main(String[] args) throws InterruptedException,
			SocketException {

		new BingoServer();

	}

}