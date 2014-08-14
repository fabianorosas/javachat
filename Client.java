package chat;
/*
 * Fabiano Rosas
 * 1257056
 */

import java.io.IOException;
import java.net.*;

public class Client extends Host {
	
	private String serverAddr;
	private int serverPort;
	
	public Client(String ip, int port) throws IOException{	//connect to <ip>:<port>
		super();
		
		this.serverAddr = ip;
		this.serverPort = port;
		this.socket = new DatagramSocket();
   	 	
		App.lblStatusClient.setText("CLIENT: " + this.socket.getLocalAddress().getHostAddress() + ":" + this.socket.getLocalPort());
		
		connect();
    }
	
	@Override
	public void runHost() throws IOException {
		while(true){
			msg = receiveMessage();
			
			if(msg.startsWith("2#")){
				clientList = msg.substring(2);
				App.updateList(clientList);
			}
			else if(msg.startsWith("4#")){
				App.showMessage(msg.substring(2));
			}
			else{
				App.lblStatusClient.setText("STRAY MESSAGE: " + msg);
			}
		}
    }

	private void connect() throws IOException{
		sendMessage("1#", serverAddr, serverPort);
	}
	
	public void disconnect() throws IOException{
		sendMessage("5#", serverAddr, serverPort);
	}

	public void sendLine(String whom, String toSend) throws IOException{
		sendMessage("3#" + whom.substring(4).replaceAll(":", "#") + "#" + toSend, serverAddr, serverPort);
	}
 }