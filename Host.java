package chat;
/*
 * Fabiano Rosas
 * 1257056
 */

import java.io.IOException;
import java.net.*;
import javax.swing.SwingWorker;
import chat.Client;

public class Host extends SwingWorker<Void,Void>{
	protected DatagramSocket socket;
	protected DatagramPacket dtgSend;
	protected DatagramPacket dtgReceive;
	    
	protected String msg;
	
	protected String clientList;
	
	public Host() {
		clientList = "";
	}

	/**
	 * Runs things in background, freeing the graphical interface.
	 * 
	 * @see javax.swing.SwingWorker#doInBackground()
	 */
    @Override
	public Void doInBackground() throws IOException {
    	runHost();
    	return null;
    }
    
	/**
	 * Method to be used by the child classes
	 * @throws IOException  
	 */
	protected void runHost() throws IOException{
		/* 
    	 * Client and server logic goes here
    	 */
    }

	protected void sendMessage(String toSend, String ip, int port) throws IOException{
		dtgSend = new DatagramPacket(toSend.getBytes(), toSend.length(), InetAddress.getByName(ip), port);
		socket.send(dtgSend);
	}
	
	protected String receiveMessage() throws IOException{
		dtgReceive = new DatagramPacket(new byte[512], 512);
   	 	socket.receive(dtgReceive);
   	 	return new String(dtgReceive.getData());
	}	
	
    /**
     * Closes the socket.
     * Exits program.
     */
    public void close(){
    	if(this instanceof Client){
    		try {
    			((Client) this).disconnect();
    		} catch (IOException e1) {
    			System.err.println(e1.toString());
    		}
    	}
    	this.socket.close();
    	System.exit(0);
    }
 }