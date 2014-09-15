import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.ArrayList;



public class ClientPlayer implements Runnable{
		
	ArrayList<Client> clients;
	DatagramSocket s;
	public ClientPlayer(ArrayList<Client> clients, DatagramSocket s){
		this.clients = clients;
		this.s = s;
	}	
		
		  @Override
		  public void run(){		  
			  System.out.println("game being run");
			  boolean gameOver = false;
			  int winner = 0;
			  byte[] sendData = new byte[512];
			  System.out.println(clients.size());
			
			  for(int i = 0; i < clients.size(); i++){
				  BingoCard card = new BingoCard();
				  clients.get(i).card = card;				  
				  
				  sendData = ("card:"+card.getCardValues()).getBytes();
				  DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, clients.get(i).ip, clients.get(i).port);
				  try {
					s.send(sendPacket);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			  }
			  
			  while(!gameOver){
				  Integer randomNum =(int) (Math.random()*75);
				  
				  for(int i = 0; i < clients.size(); i++){
					  clients.get(i).card.newPick(randomNum);
					  sendData = (clients.get(i).card).toString().getBytes();
					  DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, clients.get(i).ip, clients.get(i).port);
					  try {
						s.send(sendPacket);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				  }
				  for(int i = 0; i < clients.size(); i++){
					  if(clients.get(i).card.isWinner()){
						  gameOver = true;
						  winner = i;
					  }
				  }
				  
			  }
			  sendData = "winner".getBytes();
			  DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, clients.get(winner).ip, clients.get(winner).port);
			  
			  
			  
			  
			  
		  }
}