
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.ArrayList;



public class ClientPlayer implements Runnable{
		
	ArrayList<Client> clients;
		
	public ClientPlayer(ArrayList<Client> clients){
		this.clients = clients;
	}	
		
		  @Override
		  public void run(){		  
			  try {
				DatagramSocket s = new DatagramSocket(2016);
			
			  for(int i = 0; i < clients.size(); i++){
				  BingoCard card = new BingoCard();
				  byte[] sendData = new byte[512];
				  
				  sendData = ("card:"+card.getCardValues()).getBytes();
				  DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, clients.get(i).ip, clients.get(i).port);
				  s.send(sendPacket);
			  }
			  
			  } catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			  
			  
			  
			  
		  }
}