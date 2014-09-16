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
			  
			  for(int w = 0; w<75; w++){
				  Integer randomNum =(int) (Math.random()*75);
				  
				  for(int i = 0; i < clients.size(); i++){
					  clients.get(i).card.newPick(randomNum);
					  sendData = ("pick:"+randomNum+":"+0+":"+clients.size()+" ").getBytes();
					  if(clients.get(i).card.isWinner()){
						  gameOver = true;
						  winner = i;
						  sendData = ("pick:"+randomNum+":"+0+":"+clients.size()+"You won!").getBytes();
					  }else if(gameOver){
						  sendData = ("pick:"+randomNum+":"+0+":"+clients.size()+"You did not win. Sorry!").getBytes();
						  if(i == clients.size()-1){
							  w = 75;
						  }
					  }					  
					  
					  DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, clients.get(i).ip, clients.get(i).port);
					  try {
						s.send(sendPacket);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					  
				  }
				  try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
//				  for(int i = 0; i < clients.size(); i++){
//					  System.out.println("checking for winner");
//					  if(clients.get(i).card.isWinner()){
//						  gameOver = true;
//						  winner = i;
//					  }
//				  }
				  
			  }
//			  System.out.println("winner!: "+ winner);
//			  for(int i = 0; i < clients.size(); i++){
//				  if(i == winner){
//					  sendData = "You won!".getBytes();
//				  }else{
//					  sendData = "You did not win. Sorry!".getBytes();
//				  }
//				  System.out.println(sendData);
//				  DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, clients.get(i).ip, clients.get(i).port);
//				  try {
//					s.send(sendPacket);
//				  } catch (IOException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				  }
//			  }
			  
			  
			  
			  
			  
			  
		  }
}