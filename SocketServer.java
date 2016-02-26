/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package socketpackage;

/**
 *
 * @author shirazi1
 */
import java.net.ServerSocket;
import java.io.*;
import java.net.Socket;

class studentInfo implements Serializable 
{
    String username;
    String notes;
    studentInfo(String n, String c)
    {
        this.username = n;
        this.notes = c;
    }
}

/**
* A simple socket server
* @author faheem
*
*/


public class SocketServer {

  public final static int SOCKET_PORT = 13267;      // you may change this
  public final static String
       FILE_TO_RECEIVED = "C:/Users/shirazi1/Desktop/lalal.txt";  // you may change this, I give a
                                                            // different name because i don't want to
                                                            // overwrite the one used by server...
  public static void deserialize(){
    studentInfo si=null ;
    try  
    {
        FileInputStream fis = new FileInputStream(FILE_TO_RECEIVED);
        ObjectInputStream ois = new ObjectInputStream(fis);
        si = (studentInfo)ois.readObject();
        System.out.println(si);
    } 
    catch (Exception e)
    { e.printStackTrace(); }
    System.out.println(si.username);
    System.out. println(si.notes);
      
      
  }
  public final static int FILE_SIZE = 6022386; // file size temporary hard coded
                                               // should bigger than the file to be downloaded

  public static void main (String [] args ) throws IOException {
    int bytesRead;
    int current = 0;
    FileOutputStream fos = null;
    BufferedOutputStream bos = null;
    Socket sock = null;
    ServerSocket servsock = null;
    try {
      
      
      
      
      servsock = new ServerSocket(SOCKET_PORT);
      System.out.println("Waiting...");
      
           
        sock = servsock.accept();
        // receive file
        byte [] mybytearray  = new byte [FILE_SIZE];
        InputStream is = sock.getInputStream();
        fos = new FileOutputStream(FILE_TO_RECEIVED);
        bos = new BufferedOutputStream(fos);
        bytesRead = is.read(mybytearray,0,mybytearray.length);
        current = bytesRead;

        do {
           bytesRead =
              is.read(mybytearray, current, (mybytearray.length-current));
           if(bytesRead >= 0) current += bytesRead;
        } while(bytesRead > -1);

        bos.write(mybytearray, 0 , current);
        bos.flush();
        System.out.println("File " + FILE_TO_RECEIVED
            + " downloaded (" + current + " bytes read)");
        
        
        sock = servsock.accept();
        BufferedReader reader = new BufferedReader(new InputStreamReader(sock.getInputStream()));
        String message = reader.readLine();
        System.out.println("Message Received: " + message); 
        
        for (int i = 0; i < 3; i++) {
          deserialize();
        }

      
    }
    finally {
      if (fos != null) fos.close();
      if (bos != null) bos.close();
      if (sock != null) sock.close();
    }
  }

}
