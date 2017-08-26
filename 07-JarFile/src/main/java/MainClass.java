import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.zip.ZipEntry;

/**
 * 查看jar里的文件的压缩方式
 * @author yangsai
 *
 */
public class MainClass {

  public static void main(String[] args) throws IOException {

//    JarFile jf = new JarFile(new File("E:\\Workspaces\\workspace_java\\Learn-java\\07-JarFile\\src\\main\\resources\\app.jar"));
    JarFile jf = new JarFile(new File("E:\\Workspaces\\workspace_java\\Learn-java\\07-JarFile\\src\\main\\resources\\B02-ConfigRead.jar"));
    Enumeration e = jf.entries();
    while (e.hasMoreElements()) {
      JarEntry je = (JarEntry) e.nextElement();
      String name = je.getName();
      Date lastModified = new Date(je.getTime());
      long uncompressedSize = je.getSize();
      long compressedSize = je.getCompressedSize();

      int method = je.getMethod();
      if (method == ZipEntry.STORED) {
    	System.out.println(String.format("==%s==STORED==", name));
        System.out.println(name + " was stored at " + lastModified);          
        System.out.println("with a size of  " + uncompressedSize 
         + " bytes"); 
      }
      else if (method == ZipEntry.DEFLATED) {
    	System.out.println(String.format("==%s==DEFLATED==", name));
        System.out.println(name + " was deflated at " + lastModified);
        System.out.println("from  " + uncompressedSize + " bytes to " 
         + compressedSize + " bytes, a savings of " 
         + (100.0 - 100.0*compressedSize/uncompressedSize) + "%");
      }
      else {
        System.out.println(name 
         + " was compressed using an unrecognized method at " 
         + lastModified);
        System.out.println("from  " + uncompressedSize + " bytes to " 
         + compressedSize + " bytes, a savings of " 
         + (100.0 - 100.0*compressedSize/uncompressedSize) + "%");
      }
    }
  }
}