package servidor;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JTextArea;

/**
 * Thread mantenedora da conexão
 * @author Francisco
 *
 */
public class ThreadConexao extends Thread{

	private JTextArea textArea;

	private String ip;
        

	private OutputStream os;
	
	private InputStream is;
	
	public ThreadConexao(JTextArea textArea, String ip) {
		this.textArea = textArea;
		this.ip = ip;
		try {
			Socket socket = new Socket(ip, 9000);
			os = socket.getOutputStream();
			is = socket.getInputStream();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		while(true){
			try {
				int count = 0;
				byte [] buffer = new byte[1024];
				while(count != -1){
					count = is.read(buffer);
					if(count != -1){
						ByteArrayOutputStream baos = new ByteArrayOutputStream();
						baos.write(buffer, 0, count);
						String msg = new String(baos.toByteArray());
						textArea.append(msg);
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
	}
	
	public void enviar(String msg){
		try {
			os.write(msg.getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
