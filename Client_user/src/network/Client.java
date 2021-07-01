package network;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Client {
	
	private static final int PORT = 3001;
	private static final String IP = "127.0.0.1";
	private static final String METHOD_GET = "GET";
	private Socket client;
	private DataOutputStream outputStream;
	private DataInputStream inputStream;
	
	public Client() throws IOException {
		client = new Socket(IP, PORT);
		outputStream = new DataOutputStream(client.getOutputStream());
		inputStream = new DataInputStream(client.getInputStream());
		System.out.println(requestUser(5));
	}
	
	public String requestUser(int index) {
		String name = "";
		try {
			outputStream.writeUTF(METHOD_GET);
			outputStream.writeInt(index);
			name = inputStream.readUTF();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return name;
	}
	
	public static void main(String[] args) {
		try {
			new Client();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}