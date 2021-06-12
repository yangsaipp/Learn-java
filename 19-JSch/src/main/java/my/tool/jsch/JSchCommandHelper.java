package my.tool.jsch;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

import my.tool.config.SessionConfig;

public class JSchCommandHelper {

	private static final Logger log = LoggerFactory.getLogger(JSchCommandHelper.class);
	
	private static final int CONNECT_TIMEOUT = 5000;
	
	public static Session getSession(SessionConfig remote) throws JSchException {
	    
	    JSch jSch = new JSch();
	    if (Files.exists(Paths.get(remote.getIdentity()))) {
	        jSch.addIdentity(remote.getIdentity(), remote.getPassphrase());
	    }
	    Session session = jSch.getSession(remote.getUser(), remote.getHost(),remote.getPort());
	    session.setPassword(remote.getPassword());
	    session.setConfig("StrictHostKeyChecking", "no");
	    session.connect(CONNECT_TIMEOUT);
	    if (session.isConnected()) {
	    	log.info("Host({}) connected." , remote.getHost());
	    }
	    return session;
	}
	
	/**
	 * 执行命令
	 * @param session
	 * @param command
	 * @return
	 * @throws JSchException
	 */
	public static List<String> remoteExecute(Session session, String command) {
		log.debug(">> {}", command);
	    List<String> resultLines = new ArrayList<>();
	    ChannelExec channel = null;
	    try{
	        channel = (ChannelExec) session.openChannel("exec");
	        channel.setCommand(command);
	        InputStream input = channel.getInputStream();
	        channel.connect(CONNECT_TIMEOUT);
	        try {
	            BufferedReader inputReader = new BufferedReader(new InputStreamReader(input));
	            String inputLine = null;
	            while((inputLine = inputReader.readLine()) != null) {
	                log.debug("   {}", inputLine);
	                resultLines.add(inputLine);
	            }
	        } finally {
	            if (input != null) {
	                try {
	                    input.close();
	                } catch (Exception e) {
	                    throw e;
	                }
	            }
	        }
	    } catch (Exception e) {
	    	throw new RuntimeException("execute command IOcxecption. command=" + command, e);
	    } finally {
	        if (channel != null) {
	            try {
	                channel.disconnect();
	            } catch (Exception e) {
//	                log.error("JSch channel disconnect error:", e);
	                throw new RuntimeException("JSch channel disconnect error:", e);
	            }
	        }
	    }
	    return resultLines;
	}
	
	/**
	 * SCPto，从本地上传文件到Linux上
	 * @param source
	 * @param session
	 * @param destination
	 * @return
	 */
	public static long scpTo(String source, Session session, String destination) {
	    FileInputStream fileInputStream = null;
	    try {
	        ChannelExec channel = (ChannelExec) session.openChannel("exec");
	        OutputStream out = channel.getOutputStream();
	        InputStream in = channel.getInputStream();
	        boolean ptimestamp = false;
	        String command = "scp";
	        if (ptimestamp) {
	            command += " -p";
	        }
	        command += " -t " + destination;
	        channel.setCommand(command);
	        channel.connect(CONNECT_TIMEOUT);
	        if (checkAck(in) != 0) {
	            return -1;
	        }
	        File _lfile = new File(source);
	        if (ptimestamp) {
	            command = "T " + (_lfile.lastModified() / 1000) + " 0";
	            // The access time should be sent here,
	            // but it is not accessible with JavaAPI ;-<
	            command += (" " + (_lfile.lastModified() / 1000) + " 0\n");
	            out.write(command.getBytes());
	            out.flush();
	            if (checkAck(in) != 0) {
	                return -1;
	            }
	        }
	        //send "C0644 filesize filename", where filename should not include '/'
	        long fileSize = _lfile.length();
	        command = "C0644 " + fileSize + " ";
	        if (source.lastIndexOf('/') > 0) {
	            command += source.substring(source.lastIndexOf('/') + 1);
	        } else {
	            command += source;
	        }
	        command += "\n";
	        out.write(command.getBytes());
	        out.flush();
	        if (checkAck(in) != 0) {
	            return -1;
	        }
	        //send content of file
	        fileInputStream = new FileInputStream(source);
	        byte[] buf = new byte[1024];
	        long sum = 0;
	        while (true) {
	            int len = fileInputStream.read(buf, 0, buf.length);
	            if (len <= 0) {
	                break;
	            }
	            out.write(buf, 0, len);
	            sum += len;
	        }
	        //send '\0'
	        buf[0] = 0;
	        out.write(buf, 0, 1);
	        out.flush();
	        if (checkAck(in) != 0) {
	            return -1;
	        }
	        return sum;
	    } catch(JSchException e) {
//	        log.error("scp to catched jsch exception, ", e);
	        throw new RuntimeException(String.format("[scpTo] scp to catched jsch exception. source=%s, destination=%s", source, destination), e);
	    } catch(IOException e) {
//	        log.error("scp to catched io exception, ", e);
	        throw new RuntimeException(String.format("[scpTo] scp to catched io exception. source=%s, destination=%s", source, destination), e);
	    } catch(Exception e) {
//	        log.error("scp to error, ", e);
	        throw new RuntimeException(String.format("[scpTo] scp to error. source=%s, destination=%s", source, destination), e);
	    } finally {
	        if (fileInputStream != null) {
	            try {
	                fileInputStream.close();
	            } catch (Exception e) {
	                log.error("File input stream close error, ", e);
	            }
	        }
	    }
	}
	
	private static int checkAck(InputStream in) throws IOException {
	    int b=in.read();
	    // b may be 0 for success,
	    //          1 for error,
	    //          2 for fatal error,
	    //          -1
	    if(b==0) return b;
	    if(b==-1) return b;
	    if(b==1 || b==2){
	        StringBuffer sb=new StringBuffer();
	        int c;
	        do {
	            c=in.read();
	            sb.append((char)c);
	        }
	        while(c!='\n');
	        if(b==1){ // error
	            log.debug(sb.toString());
	        }
	        if(b==2){ // fatal error
	            log.debug(sb.toString());
	        }
	    }
	    return b;
	}
	
	/**
	 * 从Linux服务器上下载文件
	 * @param session
	 * @param source
	 * @param destination
	 * @return
	 */
	public static long scpFrom(Session session, String source, String destination) {
	    FileOutputStream fileOutputStream = null;
	    try {
	        ChannelExec channel = (ChannelExec) session.openChannel("exec");
	        channel.setCommand("scp -f " + source);
	        OutputStream out = channel.getOutputStream();
	        InputStream in = channel.getInputStream();
	        channel.connect();
	        byte[] buf = new byte[1024];
	        //send '\0'
	        buf[0] = 0;
	        out.write(buf, 0, 1);
	        out.flush();
	        while(true) {
	            if (checkAck(in) != 'C') {
	                break;
	            }
	        }
	        //read '644 '
	        in.read(buf, 0, 4);
	        long fileSize = 0;
	        while (true) {
	            if (in.read(buf, 0, 1) < 0) {
	                break;
	            }
	            if (buf[0] == ' ') {
	                break;
	            }
	            fileSize = fileSize * 10L + (long)(buf[0] - '0');
	        }
	        String file = null;
	        for (int i = 0; ; i++) {
	            in.read(buf, i, 1);
	            if (buf[i] == (byte) 0x0a) {
	                file = new String(buf, 0, i);
	                break;
	            }
	        }
	        // send '\0'
	        buf[0] = 0;
	        out.write(buf, 0, 1);
	        out.flush();
	        // read a content of lfile
	        File outFile = null;
//	        if (Files.isDirectory(Paths.get(destination))) {
        	if (destination.indexOf('.') == -1) { // 为目录
	        	outFile = new File(destination, file);
//	            fileOutputStream = new FileOutputStream(destination + File.separator +file);
	        } else {
	            outFile = new File(destination);
	        }
	        FileUtils.createParentDirectories(outFile);
	        fileOutputStream = new FileOutputStream(outFile);
	        long sum = 0;
	        while (true) {
	            int len = in.read(buf, 0 , buf.length);
	            if (len <= 0) {
	                break;
	            }
	            sum += len;
	            if (len >= fileSize) {
	                fileOutputStream.write(buf, 0, (int)fileSize);
	                break;
	            }
	            fileOutputStream.write(buf, 0, len);
	            fileSize -= len;
	        }
	        return sum;
	    } catch(JSchException e) {
//	        log.error("scp to catched jsch exception, ", e);
	        throw new RuntimeException(String.format("[scpFrom] scp to catched jsch exception. source=%s, destination=%s", source, destination), e);
	    } catch(IOException e) {
//	        log.error("scp to catched io exception, ", e);
	        throw new RuntimeException(String.format("[scpFrom] scp to catched io exception. source=%s, destination=%s", source, destination), e);
	    } catch(Exception e) {
//	        log.error("scp to error, ", e);
	        throw new RuntimeException(String.format("[scpFrom] scp to error. source=%s, destination=%s", source, destination), e);
	    } finally {
	        if (fileOutputStream != null) {
	            try {
	                fileOutputStream.close();
	            } catch (Exception e) {
//	                log.error("File output stream close error, ", e);
	                throw new RuntimeException("File output stream close error, ", e);
	            }
	        }
	    }
	}
}
