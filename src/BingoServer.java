import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;



public class BingoServer{		
	 	
	public static int numWaiting = 0;
	public static int numPlaying = 0;
	 
	public static int[] port = new int[1000];
	public static InetAddress[] ip = new InetAddress[1000];
	
	public static void main(String[] args) throws InterruptedException, SocketException{
		
		DatagramSocket s = new DatagramSocket(2016);		
	  	
		ClientConnector initializer;	
		ClientPlayer gamePlayer;
		
		while(true){
			initializer = new ClientConnector(s);			
			Thread initThread = new Thread(initializer);
			gamePlayer = new ClientPlayer(s);
			Thread gameThread = new Thread(gamePlayer);
			//start waiting room
			initThread.start();
			//wait for waiting room thread to end
			initThread.join();
			//start game playing thread
			gameThread.start();
			//start next waiting room
			initThread.start();
			//wait for game thread to end and repeat loop
			gameThread.join();
		}	
		
		
	}
}
	
	
	
	
	
	


//byte[] recvData = new byte[512];
//	byte[] sendData = new byte[512];
	
//DatagramPacket recvPacket = new DatagramPacket(recvData, recvData.length);
	
	

//	public static void main(String[] args) throws IOException, SocketException, InterruptedException {
//		
//		DatagramSocket s = new DatagramSocket(2016);
//        byte[] recvData = new byte[512];
//        byte[] sendData = new byte[512];
//        String welcome =	"\nCard:"; 
//        int numPlayers = 0;
//        int[] port = new int[1000];
//        InetAddress[] ip = new InetAddress[1000]; 
//        DatagramPacket recvPacket = new DatagramPacket(recvData, recvData.length);
//        
//        BingoCard[] bingo = new BingoCard[1000];
//        String[] card = new String[1000];     
//        
//		while(true){			
//			
//			for(int i = 0; i < 1000; i++){
//				//try{
//				s.receive(recvPacket);						
//				ip[i] = recvPacket.getAddress();
//				port[i] = recvPacket.getPort();				
//				bingo[i] = new BingoCard();
//				card[i] = bingo.toString();
//				System.out.println("New connection!");
//				System.out.println("IP address; " + ip[i].toString()+ " : "+ port[i]);
//				
//				if(numPlayers == 0){
//					String hostWelcome = welcome+"\n\n\tYou are the game's host!\n\tPlease tyee \"play\" and enter to begin the game:";
//					sendData = hostWelcome.getBytes();
//					DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, ip[i], port[i]);
//					s.send(sendPacket);
//				}else{
//					sendData = welcome.getBytes();
//					DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, ip[i], port[i]);
//					s.send(sendPacket);
//				}
//				
//				numPlayers++;
//				
//				if(recvPacket.getData().toString().equals("play")){
//					i = 9000;
//				}
//				
//			}
//			
//
////			for(int i = 0; i < numPlayers; i++){
////				if(numPlayers == 0){
////					String hostWelcome = welcome+"\n\n\tYou are the game's host!\n\tPlease type \"play\" and enter to begin the game:";
////					sendData = hostWelcome.getBytes();
////				}else{
////					sendData = welcome.getBytes();
////				}
////				DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, ip[i], port[i]);
////				s.send(sendPacket);				
////			}
//			
//							
//			
//			for(int i = 0; i < 40; i++){
//				
//				for(int j = 0; j < numPlayers; j++){
//					recvPacket = new DatagramPacket(recvData, recvData.length);
//					s.receive(recvPacket);
//				}
//				
//				Integer randomNum =(int) (Math.random()*75);				
//				
//				
//				for(int j = 0; j < numPlayers; j++){
//					bingo[j].newPick(randomNum);
//					sendData = card[j].getBytes();
//					DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, ip[j], port[j]);
//					s.send(sendPacket);
//				}
//				//System.out.println("packet sent to client");
//				
//				for(int j = 0; j < numPlayers; j++){
//					card[j] = "\n"+"   \n Number Call: " + randomNum.toString() + "\n" + "\n" + bingo[j].toString();
//								
//					if(bingo[j].isWinner()){
//						i = 999;
//					}
//				}
//				Thread.sleep(5000);
//			}
//			for(int i = 0; i < numPlayers; i++){
//				if(bingo[i].isWinner()){
//					String message = "CONGATULATIONS, YOU WON!!!!";
//					sendData = message.getBytes();
//				}else{
//					String message = "Sorry, you lost...";
//					sendData = message.getBytes();
//				}
//				
//				
//					DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, ip[i], port[i]);
//					s.send(sendPacket);
//			}
//			
//		}
//		
//		
//		
//	}
//}