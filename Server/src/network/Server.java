package network;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Server {
	
	private static final String NEW_CONNECTION_MESSAGE = "New connection!";
	private static final String USER_SENT_MESSAGE = "User sent";
	private static final int PORT = 3001;
	private static final String SERVER_ON = "Server on port ";
	private static final String GET = "GET";
	private static final String NEW_REQUEST = "New request: ";
	private ServerSocket server;
	private DataInputStream inputStream;
	private ArrayList<String> users;

	public Server() throws IOException {
		users = new ArrayList<String>();
		users.add("Andres");
		users.add("Juana");
		users.add("Angie");
		users.add("Brayan");
		users.add("Cristina");
		users.add("Sebastian");
		server = new ServerSocket(PORT);
		Logger.getGlobal().log(Level.INFO, SERVER_ON + PORT);
		acceptConnections();
	}

	private void acceptConnections() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				while(!server.isClosed()) {
					try {
						Socket connection = server.accept();
						Logger.getGlobal().log(Level.INFO, NEW_CONNECTION_MESSAGE);
						inputStream = new DataInputStream(connection.getInputStream());
						String request = inputStream.readUTF();
						Logger.getGlobal().log(Level.INFO, NEW_REQUEST + request);
						switch (request) {
						case GET:
							int index = inputStream.readInt();
							response(connection, index);
							Logger.getGlobal().log(Level.INFO, USER_SENT_MESSAGE);
							break;
						default:
							break;
						}
					} catch (IOException e) {
						Logger.getGlobal().log(Level.SEVERE, e.toString());
					}
				}
			}
		}).start();
	}
	
	private void response(Socket connection, int index) throws IOException {
		try {
			DataOutputStream outputStream = new DataOutputStream(connection.getOutputStream());
			outputStream.writeUTF(users.get(index));
		} catch (IOException e) {
			Logger.getGlobal().log(Level.SEVERE, e.toString());
		}
	}
	
	public static void main(String[] args) {
		try {
			new Server();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}