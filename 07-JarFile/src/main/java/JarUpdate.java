import java.io.*;
import java.util.*;
import java.util.zip.*;
import java.util.jar.*;

/**
 * springboot可执行jar更新内嵌jar<br>
 * 
 * 参考：https://stackoverflow.com/questions/3110701/java-add-class-to-jar-archive-at-runtime
 * 
 * @author yangsai
 *
 */
public class JarUpdate {
   /**
    * main()
    */
   public static void main(String[] args) throws IOException {
      // Get the jar name and entry name from the command-line.

      String jarName = "E:\\Workspaces\\workspace_java\\Learn-java\\07-JarFile\\src\\main\\resources\\B02-ConfigRead.jar";
      String fileName = "E:\\Workspaces\\workspace_java\\Learn-java\\07-JarFile\\src\\main\\resources\\log4j-over-slf4j-1.7.22.jar";
      String name = "BOOT-INF\\lib\\log4j-over-slf4j-1.7.22.jar";

      // Create file descriptors for the jar and a temp jar.

      File jarFile = new File(jarName);
      File tempJarFile = new File(jarName + ".tmp");

      // Open the jar file.

      JarFile jar = new JarFile(jarFile);
      System.out.println(jarName + " opened.");

      // Initialize a flag that will indicate that the jar was updated.

      boolean jarUpdated = false;

      try {
         // Create a temp jar file with no manifest. (The manifest will
         // be copied when the entries are copied.)

         Manifest jarManifest = jar.getManifest();
         JarOutputStream tempJar =
            new JarOutputStream(new FileOutputStream(tempJarFile));

         // Allocate a buffer for reading entry data.

         byte[] buffer = new byte[1024];
         int bytesRead;

         try {
            // Open the given file.
        	File updateJarFile = new File(fileName);
            FileInputStream file = new FileInputStream(updateJarFile);

            try {
               // Create a jar entry and add it to the temp jar.

//               JarEntry entry = new JarEntry(name);
//               tempJar.putNextEntry(entry);

            	JarEntry nestedEntry = new JarEntry(name);
        		byte[] nestedJarData = getJarData(file);
        		nestedEntry.setSize(updateJarFile.length());
        		nestedEntry.setCompressedSize(updateJarFile.length());
//        		if (unpackNested) {
//        			nestedEntry.setComment("UNPACK:0000000000000000000000000000000000000000");
//        		}
        		CRC32 crc32 = new CRC32();
        		crc32.update(nestedJarData);
        		nestedEntry.setCrc(crc32.getValue());

        		nestedEntry.setMethod(ZipEntry.STORED);
        		tempJar.putNextEntry(nestedEntry);
               // Read the file and write it to the jar.

//               while ((bytesRead = file.read(buffer)) != -1) {
//                  tempJar.write(buffer, 0, bytesRead);
//               }
               tempJar.write(nestedJarData);
               System.out.println(nestedEntry.getName() + " added.");
            }
            finally {
               file.close();
            }

            // Loop through the jar entries and add them to the temp jar,
            // skipping the entry that was added to the temp jar already.

            for (Enumeration entries = jar.entries(); entries.hasMoreElements(); ) {
               // Get the next entry.

               JarEntry entry = (JarEntry) entries.nextElement();

               // If the entry has not been added already, add it.

               if (! entry.getName().equals(name)) {
                  // Get an input stream for the entry.

                  InputStream entryStream = jar.getInputStream(entry);

                  // Read the entry and write it to the temp jar.

                  tempJar.putNextEntry(entry);

                  while ((bytesRead = entryStream.read(buffer)) != -1) {
                     tempJar.write(buffer, 0, bytesRead);
                  }
               }
            }

            jarUpdated = true;
         }
         catch (Exception ex) {
        	 ex.printStackTrace();

            // Add a stub entry here, so that the jar will close without an
            // exception.

            tempJar.putNextEntry(new JarEntry("stub"));
         }
         finally {
            tempJar.close();
         }
      }
      finally {
         jar.close();
         System.out.println(jarName + " closed.");

         // If the jar was not updated, delete the temp jar file.

         if (! jarUpdated) {
            tempJarFile.delete();
         }
      }

      // If the jar was updated, delete the original jar file and rename the
      // temp jar file to the original name.

      if (jarUpdated) {
         jarFile.delete();
         tempJarFile.renameTo(jarFile);
         System.out.println(jarName + " updated.");
      }
   }
   
   private static byte[] getJarData(FileInputStream file) throws Exception {
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//		JarOutputStream jarOutputStream = new JarOutputStream(byteArrayOutputStream);
		byte[] buffer = new byte[1024];
        int bytesRead;
		while ((bytesRead = file.read(buffer)) != -1) {
			byteArrayOutputStream.write(buffer, 0, bytesRead);
         }
//		jarOutputStream.close();
		return byteArrayOutputStream.toByteArray();
	}
}