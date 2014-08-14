package chat;
/*
 * Fabiano Rosas
 * 1257056
 */

import java.io.*;
import java.net.*;
import java.util.regex.Pattern;

public class Server extends Host {
	
	private String brdcst;
	
	public Server(String ip, int port) throws SocketException{
		super();

		this.socket = new DatagramSocket(port);
		this.brdcst = "999.999.999.999#99999";

		App.lblStatusServer.setText("SERVER: " + ip + ":" + port);
	}
	
	@Override
	public void runHost() throws IOException {
		while(true){
			msg = receiveMessage();
			if( msg.startsWith("1#") ) { //client connection
				App.showMessage(dtgReceive.getAddress().getHostAddress() + ":" + dtgReceive.getPort() + " connected!");
				clientList = clientList.concat(dtgReceive.getAddress().getHostAddress() + "#" + dtgReceive.getPort() + "#");
				sendBroadcast("2#" + clientList);
				App.updateList(clientList);
			}
			else if( msg.startsWith("3#") ){ //message exchange
				if(msg.substring(2).startsWith(brdcst)){
					sendBroadcast("4#" + msg.substring(2));
				}
				else{
					String[] msgSplit = msg.split("#");
					String toSend = "4#" + dtgReceive.getAddress().getHostAddress() + "#" + dtgReceive.getPort() + "#" + msgSplit[3].trim(); 
					sendMessage(toSend, msgSplit[1], Integer.parseInt(msgSplit[2]));
				}
				App.showMessage(msg.substring(2));
			}
			else if( msg.startsWith("5#") ){ //client disconnection
				App.showMessage(dtgReceive.getAddress().getHostAddress() + ":" + dtgReceive.getPort() + " disconnected!");
				String removeHost = dtgReceive.getAddress().getHostAddress() + "#" + dtgReceive.getPort() + "#";
				clientList = clientList.replaceFirst(Pattern.quote(removeHost), "");
				sendBroadcast("2#" + clientList);
				App.updateList(clientList);
			}
			else{
				App.lblStatusServer.setText("STRAY MESSAGE: " + msg);
			}
		}
	}
	
	private void sendBroadcast(String toSend) throws IOException{
		if(!clientList.equals("")){
			for(String port : clientList.replaceAll("(\\d*\\.){3}\\d*#", "").split("#")){
				sendMessage(toSend, "255.255.255.255", Integer.parseInt(port));
			}
		}
	}
}