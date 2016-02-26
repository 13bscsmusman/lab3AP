package socketpackage;

import java.io.*;
import java.util.*;
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

public class SocketClient {

  public final static int SOCKET_PORT = 13267;  // you may change this
  public final static String FILE_TO_SEND = "C:/Users/shirazi1/Desktop/aaa.txt";  // you may change this
  public final static String SERVER = "127.0.0.1";  // localhost

  
  public static void serialize(String uName,String notes){
    try
        {
            studentInfo si = new studentInfo(uName, notes);
            FileOutputStream fos = new FileOutputStream("C:/Users/shirazi1/Desktop/aaa.txt");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(si);
            oos.close();
            fos.close();
        }
        catch (Exception e)
        { e. printStackTrace(); }  
      
  }
  
  public static void main (String [] args ) throws IOException {
      System.out.println("Enter 3 usernames and notes");
      for (int i = 0; i < 3; i++) {
          Scanner sc=new Scanner(System.in);
          System.out.println("Enter the username");
          String user=sc.nextLine();
          System.out.println("Enter the notes");
          String note=sc.nextLine();
          serialize(user,note);
          
      }
  
      
    FileInputStream fis = null;
    BufferedInputStream bis = null;
    OutputStream os = null;
    
    Socket sock = null;
        try {
          sock = new Socket(SERVER, SOCKET_PORT);
          System.out.println("Connecting...");
          System.out.println("Accepted connection : " + sock);
          // send file
          File myFile = new File (FILE_TO_SEND);
          byte [] mybytearray  = new byte [(int)myFile.length()];
          fis = new FileInputStream(myFile);
          bis = new BufferedInputStream(fis);
          bis.read(mybytearray,0,mybytearray.length);
          os = sock.getOutputStream();
          System.out.println("Sending " + FILE_TO_SEND + "(" + mybytearray.length + " bytes)");
          os.write(mybytearray,0,mybytearray.length);
          os.flush();
          System.out.println("Done.");
          
          sock.close();
          sock = new Socket(SERVER, SOCKET_PORT);
          Scanner sq=new Scanner(System.in);
          System.out.println("Enter the username to search");
          String searchUser=sq.nextLine();
          
          PrintWriter out=new PrintWriter(sock.getOutputStream(),true);
          out.println(searchUser);
        }
        finally {
          if (bis != null) bis.close();
          if (os != null) os.close();
          if (sock!=null) sock.close();
       }
      
    }
    
 }
