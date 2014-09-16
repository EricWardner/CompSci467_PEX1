import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;



public class ClientPlayer implements Runnable{
		
	ArrayList<Client> clients;
	DatagramSocket s;
	
	public ClientPlayer(ArrayList<Client> clients, DatagramSocket s){
		this.clients = clients;
		this.s = s;
	}	
		
		  @Override
		  public void run(){
			  List<Integer> picks = new ArrayList<>();
			  for(int i = 1; i <=75; i++){
				  picks.add(i);
			  }
			  Collections.shuffle(picks);
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
				  //Integer randomNum =(int) (Math.random()*75);
				  Integer randomNum = picks.get(w);
				  
				  for(int i = 0; i < clients.size(); i++){
					  clients.get(i).card.newPick(randomNum);
					  sendData = ("pick:"+randomNum+":"+0+":"+clients.size()+" ").getBytes();
					  if(clients.get(i).card.isWinner()){
						  winner = i;
						  w = 75;
						  for(int j = 0; j < clients.size(); j++){
							  if(j == winner){
								  sendData = ("pick:"+randomNum+":"+0+":"+clients.size()+"You won!").getBytes();
							  }else{
								  sendData = ("pick:"+randomNum+":"+0+":"+clients.size()+"You did not win. Sorry!").getBytes();
							  }
							  DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, clients.get(j).ip, clients.get(j).port);
							  try {
								s.send(sendPacket);
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						  }
						  
						  						  
					  }
					  System.out.println(w);
					  if(w != 75){
						  DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, clients.get(i).ip, clients.get(i).port);
						  try {
							s.send(sendPacket);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					  }
					  
				  }
				  try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				  
			  }			  
			  
		  }
}