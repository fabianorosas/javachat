package chat;
/*
 * Fabiano Rosas
 * 1257056
 */

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import javax.swing.JLabel;

import java.awt.Font;

import javax.swing.JTextArea;
import javax.swing.JScrollPane;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.CardLayout;

import javax.swing.table.DefaultTableModel;
import javax.swing.SwingConstants;

import java.awt.Component;
import java.awt.ComponentOrientation;

import javax.swing.border.LineBorder;

import java.awt.Color;
import java.awt.Insets;

import javax.swing.JList;

import java.awt.Dimension;
import java.io.IOException;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import javax.swing.border.MatteBorder;
import javax.swing.AbstractListModel;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ScrollPaneConstants;
import javax.swing.ListSelectionModel;

public class App {

	private JFrame frame;
	
	private JMenuBar menuBar;
	
	private CardLayout cl;
	
	private final JPanel panel_server;
	private final JPanel panel_start;
	private	JTextField tFPortServer;
	private final JButton btnStartServer;
	
	private final DefaultTableModel model;
	
	private JPanel panel_client;
	private JPanel panel_connect;
	private JLabel lblIp;
	private JTextField tFIP;
	private JLabel lblPort;
	private JTextField tFPort;
	private final JButton btnConnect;
	
	private final JPanel panel_log;
	private JScrollPane scrollPane;
	private static JTextArea tAOutput;
	private JTextField txtType;
	private JButton btnSend;
	private JButton btnDisconnect;
	
	private JPanel panel_blank;
	protected static JLabel lblStatusServer;
	
	protected static JLabel lblStatusClient;
	private String receiverAddr;
	private JLabel lblTo;
	
	private JScrollPane scrollPane_1;
	
	private static JTextArea tAOutputServer;

	private static JList<String> listServer;
	private static JList<String> listClient;
	
	private static Host host;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					App window = new App();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public App() {
		frame = new JFrame();
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				host.close();
			}
		});
		frame.setResizable(false);
		frame.setBounds(100, 100, 494, 322);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		menuBar = new JMenuBar();
		menuBar.setAlignmentX(Component.LEFT_ALIGNMENT);
		frame.setJMenuBar(menuBar);

		panel_server = new JPanel();
		panel_server.setLayout(null);
		
		panel_blank = new JPanel();
		
		panel_client = new JPanel();
		panel_client.setLayout(null);
		
		frame.getContentPane().setLayout(new CardLayout(0, 0));
		frame.getContentPane().add(panel_server, "server");
		frame.getContentPane().add(panel_blank, "blank");
		frame.getContentPane().add(panel_client, "client");
		
		cl = (CardLayout)(frame.getContentPane().getLayout());
		cl.show(frame.getContentPane(), "blank");
		
		panel_start = new JPanel();
		panel_start.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_start.setBounds(150, 80, 133, 85);
		panel_server.add(panel_start);
		panel_start.setLayout(null);
		
		JLabel lblPortServer = new JLabel("Port:");
		lblPortServer.setHorizontalAlignment(SwingConstants.TRAILING);
		lblPortServer.setHorizontalTextPosition(SwingConstants.LEADING);
		lblPortServer.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		lblPortServer.setBounds(12, 22, 43, 15);
		panel_start.add(lblPortServer);
		
		tFPortServer = new JTextField();
		tFPortServer.setText("8080");
		tFPortServer.setColumns(10);
		tFPortServer.setBounds(60, 20, 54, 19);
		panel_start.add(tFPortServer);
		
		btnStartServer = new JButton("Start");
		btnStartServer.setBounds(22, 51, 92, 22);
				
		panel_connect = new JPanel();
		panel_connect.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_connect.setBounds(108, 66, 217, 114);
		panel_client.add(panel_connect);
		panel_connect.setLayout(null);
		
		lblIp = new JLabel("IP:");
		lblIp.setHorizontalAlignment(SwingConstants.TRAILING);
		lblIp.setBounds(23, 14, 30, 15);
		panel_connect.add(lblIp);
		
		tFIP = new JTextField();
		try {
			tFIP.setText(InetAddress.getLocalHost().getHostAddress());
		} catch (UnknownHostException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		tFIP.setBounds(63, 12, 114, 19);
		panel_connect.add(tFIP);
		tFIP.setColumns(10);
		
		lblPort = new JLabel("Port:");
		lblPort.setHorizontalAlignment(SwingConstants.TRAILING);
		lblPort.setBounds(12, 43, 41, 15);
		panel_connect.add(lblPort);
		
		tFPort = new JTextField();
		tFPort.setText("8080");
		tFPort.setBounds(63, 41, 49, 19);
		panel_connect.add(tFPort);
		tFPort.setColumns(10);
				
		btnConnect = new JButton("Connect");
				
		btnConnect.setBounds(62, 80, 92, 22);
		panel_connect.add(btnConnect);
		
		panel_log = new JPanel();
		panel_log.setBounds(0, 0, 279, 252);
		panel_client.add(panel_log);
		panel_log.setVisible(false);
		panel_log.setLayout(null);
				
		scrollPane = new JScrollPane();
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setBounds(12, 12, 255, 181);
		panel_log.add(scrollPane);
				
		tAOutput = new JTextArea();
		tAOutput.setLineWrap(true);
		tAOutput.setWrapStyleWord(true);
		tAOutput.setEditable(false);
		scrollPane.setViewportView(tAOutput);
				
		txtType = new JTextField();
		txtType.setToolTipText("Type here...");
		txtType.setBounds(12, 219, 209, 21);
		txtType.setColumns(10);
				
		btnSend = new JButton("Send");
		btnSend.setMargin(new Insets(2, 2, 2, 2));
		btnSend.setHorizontalAlignment(SwingConstants.LEFT);
		btnSend.setHorizontalTextPosition(SwingConstants.LEADING);
		btnSend.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		btnSend.setBounds(221, 219, 46, 21);
				
		model = new DefaultTableModel();
		model.addColumn("#");
		model.addColumn("Server IP:port");
		model.addColumn("Clients");
		
		JMenuItem mntmServer = new JMenuItem("Server");
		
		mntmServer.setMaximumSize(new Dimension(60, 700));
		mntmServer.setMargin(new Insets(2, 2, 2, 0));
		mntmServer.setHorizontalTextPosition(SwingConstants.LEADING);
		mntmServer.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		mntmServer.setAlignmentX(Component.LEFT_ALIGNMENT);
		
		JMenuItem mntmClient = new JMenuItem("Client");
		mntmClient.setMaximumSize(new Dimension(60, 32767));
		mntmClient.setAlignmentX(Component.LEFT_ALIGNMENT);
		
		listClient = new JList<>();
		listClient.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listClient.setVisibleRowCount(15);
		listClient.setVisible(false);
		listClient.setBorder(new TitledBorder(new LineBorder(new Color(184, 207, 229)), "Client list:", TitledBorder.LEADING, TitledBorder.BELOW_TOP, null, null));
		listClient.setBounds(291, 12, 183, 228);
		
		lblStatusClient = new JLabel("");
		lblStatusClient.setFont(new Font("Monospaced", Font.ITALIC, 10));
		lblStatusClient.setBorder(new MatteBorder(1, 0, 0, 0, new Color(0, 0, 0)));
		lblStatusClient.setBounds(0, 252, 488, 20);
		panel_client.add(lblStatusClient);
		
		lblStatusServer = new JLabel("");
		lblStatusServer.setFont(new Font("Monospaced", Font.ITALIC, 10));
		lblStatusServer.setBorder(new MatteBorder(1, 0, 0, 0, new Color(0, 0, 0)));
		lblStatusServer.setBounds(0, 251, 486, 21);
		panel_server.add(lblStatusServer);
		
		btnDisconnect = new JButton("Disconnect");
		btnDisconnect.setVisible(false);
		btnDisconnect.setBounds(357, 252, 117, 20);
		
		listServer = new JList<>();
		listServer.setBorder(new TitledBorder(new LineBorder(new Color(184, 207, 229)), "Client list:", TitledBorder.LEADING, TitledBorder.BELOW_TOP, null, null));
		listServer.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listServer.setVisibleRowCount(15);
		listServer.setVisible(false);
		listServer.setBounds(10, 10, 160, 229);
		panel_server.add(listServer);
		
		scrollPane_1 = new JScrollPane();
		scrollPane_1.setVisible(false);
		scrollPane_1.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane_1.setBounds(180, 10, 294, 229);
		panel_server.add(scrollPane_1);
		
		tAOutputServer = new JTextArea();
		tAOutputServer.setLineWrap(true);
		tAOutputServer.setVisible(false);
		tAOutputServer.setWrapStyleWord(true);
		scrollPane_1.setViewportView(tAOutputServer);
		
//-------------------------------------------------------------------------------------------//
		/*
		 * Buttons:	Start client
		 * 			Start server
		 */
		
		/**
		 * Starts the client.
		 */
		btnConnect.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				panel_log.setVisible(true);
				btnDisconnect.setVisible(true);
				panel_connect.setVisible(false);
				listClient.setVisible(true);
				
				try {
					host = new Client(tFIP.getText(), Integer.parseInt(tFPort.getText()));
				} catch (NumberFormatException | IOException e) {
					lblStatusClient.setText(e.toString());
					host.close();
				}
				host.execute();
			}
		});
		
		/**
		 * Instantiates and runs a server.
		 */
		btnStartServer.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				panel_start.setVisible(false);
				listServer.setVisible(true);
				tAOutputServer.setVisible(true);
				scrollPane_1.setVisible(true);
				
				try {
					host = new Server(InetAddress.getLocalHost().getHostAddress(), Integer.parseInt(tFPortServer.getText()));
				} catch (NumberFormatException | SocketException | UnknownHostException e) {
					lblStatusServer.setText(e.toString());
				}
				host.execute();
			}
		});
		panel_start.add(btnStartServer);

//-------------------------------------------------------------------------------------------//
		/*
		 * Trivial stuff:
		 */
		
		/**
		 * Shows server pane
		 */
		mntmServer.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				cl.show(frame.getContentPane(), "server");
			}
		});
		menuBar.add(mntmServer);
		
		/**
		 * Shows client pane
		 */
		mntmClient.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				cl.show(frame.getContentPane(), "client");
			}
		});		
		menuBar.add(mntmClient);

		/**
		 * Clicks the send button when the user presses RET
		 * in the text field
		 */
		txtType.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER)
					btnSend.doClick();
			}
		});
		panel_log.add(txtType);
		
		/**
		 * Sends typed line to the server.
		 * Outputs server response in textArea.
		 */
		btnSend.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(!lblTo.getText().equals("To: ")){
					try {
						((Client) host).sendLine(lblTo.getText(), txtType.getText());
					} catch (IOException e) {
						lblStatusClient.setText(e.toString());
					}
					txtType.setText("");
				}
			}
		});
		panel_log.add(btnSend);
		
		lblTo = new JLabel("To: ");
		lblTo.setBounds(12, 205, 209, 15);
		panel_log.add(lblTo);
		
		/**
		 * Disconnects the client
		 */
		btnDisconnect.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				tAOutput.setText("");
				listClient.removeAll();
				try {
					((Client) host).disconnect();
				} catch (IOException e) {
					lblStatusClient.setText(e.toString());
				}
				panel_log.setVisible(false);
				listClient.setVisible(false);
				panel_connect.setVisible(true);
			}
		});
		panel_client.add(btnDisconnect);
		
		/**
		 * Waits for a click on the list of clients
		 */
		listClient.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				receiverAddr = listClient.getModel().getElementAt(listClient.getSelectedIndex());
				if(receiverAddr.equals("All"))
					receiverAddr = "999.999.999.999:99999";
				lblTo.setText("To: " + receiverAddr);
			}
		});
		panel_client.add(listClient);			

//-------------------------------------------------------------------------------------------//	
	}//constructor
	
	/*
	 * Methods to synchronize the GUI
	 */
	
	/**
	 * Updates the client list in the clients screen
	 */
	public static void updateList(String clientList){
		final String lst = clientList;
		AbstractListModel<String> model = new AbstractListModel<String>() {
			private static final long serialVersionUID = 1L;
			String[] values = ("All#" + lst.replaceAll("(\\d*\\.\\d*\\.\\d*\\.\\d*)#(\\d*)", "$1:$2")).trim().split("#");
			@Override
			public int getSize() {
				return values.length;
			}
			@Override
			public String getElementAt(int index) {
				return values[index];
			}
		}; 
		
		if(host instanceof Client)
			listClient.setModel(model);
		else
			listServer.setModel(model);
	}

	/**
	 * Updates the message area of the clients
	 */
	public static void showMessage(String msg){
		if(host instanceof Client){
			tAOutput.append(msg.trim());
			tAOutput.append("\n");
		}
		else{
			tAOutputServer.append(msg.trim());
			tAOutputServer.append("\n");
		}
	}
}//class