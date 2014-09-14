
import java.net.DatagramSocket;


public class ClientConnector implements Runnable{
		
	DatagramSocket s;

		public ClientConnector(DatagramSocket s){
			this.s = s;
		}
		
		  @Override
		  public void run(){	
			  	
			  while(BingoServer.numWaiting < 2){
				  
				  //do waiting room stuff
				  
			  }			  
		  }
	}