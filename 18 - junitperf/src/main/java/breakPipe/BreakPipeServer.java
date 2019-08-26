package breakPipe;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class BreakPipeServer {

	//server程序
	public static void main(String[] args) {
	        try {
	            ServerSocket ss = new ServerSocket(3113);
	            Socket s = ss.accept();
	            InputStream is = s.getInputStream();
	            byte[] buf =new byte[1024];
	            int len = is.read(buf);
	            System.out.println("recv:"+new String(buf,0,len));
	            Thread.sleep(10000);
	            s.getOutputStream().write("hello".getBytes());
//	            s.getOutputStream().write("hello2".getBytes());
	            System.out.println("send over");
	            System.in.read();
	        }catch (Exception e){
	            e.printStackTrace();
	        }
	    }
	
}
