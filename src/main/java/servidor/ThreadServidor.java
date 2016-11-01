package servidor;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ThreadServidor extends Thread{

	private static boolean started;
	
	public List<ThreadCliente> clientes = new ArrayList<ThreadCliente>();
	
	public static void iniciar(){
		if(!started){
			started = true;
			new ThreadServidor().start();
		}
	}
	
	@Override
	public void run() {
		try {
			ServerSocket server = new ServerSocket(9000);
			while(started){
				Socket socket = server.accept();
				ThreadCliente thread = new ThreadCliente(clientes, socket);
				clientes.add(thread);
				thread.start();
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
