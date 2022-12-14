package socket;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.net.Socket;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.FileInputStream;
import java.io.FileWriter;

import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.*;

public class socketforapp {
   private static FileWriter fr;
   public static void main(String[] args) throws Exception {
      InetAddress host = InetAddress.getByName("140.135.236.112");
      Selector selector = Selector.open();
      ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
      serverSocketChannel.configureBlocking(false);
      serverSocketChannel.bind(new InetSocketAddress(host, 7000));
      serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
      SelectionKey key = null;
      
      System.out.println("Server already opens");
      while (true) {
    	 System.out.println("Keep Opening");
         if (selector.select() <= 0)
            continue;
         
         Set<SelectionKey> selectedKeys = selector.selectedKeys();
         Iterator<SelectionKey> iterator = selectedKeys.iterator();

         while (iterator.hasNext()) {
        	System.out.println("New client");
            key = (SelectionKey) iterator.next();
            iterator.remove();
            if (key.isAcceptable()) {
               SocketChannel sc = serverSocketChannel.accept();
               sc.configureBlocking(false);
               sc.register(selector, SelectionKey.OP_READ);
               System.out.println("Connection Accepted: " + sc.getLocalAddress() + "n");
            } // if

            if (key.isReadable()) {
               SocketChannel sc = (SocketChannel) key.channel();
               ByteBuffer bb = ByteBuffer.allocate(1024);
               sc.read(bb);
               String result = new String(bb.array()).trim();
               System.out.println("Message received: " + result + " Message length= " + result.length());
               if (result.length() <= 0) {
                  sc.close();
                  System.out.println( "Connection closed..." );
                  System.out.println( "Server will keep running. " +
                     "Try running another client to " +
                     "re-establish connection");
               } // if
               else {
            	   fr = new FileWriter("routePlan.txt", true);
                   fr.write(result + "\n");
                   fr.flush();
                   fr.close();
               } // else
            }
         }
      }
   }
}
/*
public class socketforapp {

    private static int serverport = 12133;     //????????? Port
    private static ServerSocket serverSocket; //????????????Socket
    private static int count=0; //??????????????? Client ?????????
    private static FileWriter fr;
 
    // ??? ArrayList ??????????????? Client ?????????
    private static ArrayList clients = new ArrayList();
 
    public static void main(String[] args) {   
        try {
            serverSocket = new ServerSocket(serverport);
            System.out.println("Server is start.");
            // ???????????????????????????
            System.out.println("Waiting for client connect");
            // ???Server????????????
            fr = new FileWriter("Test123.txt");

            while (!serverSocket.isClosed()) { 
                // ?????????????????????????????????
                waitNewClient();
            }
            
            fr.close();
        } catch (IOException e) {
            System.out.println("Server Socket ERROR");
        }
        
    }
 
    // ???????????? Client ?????????
    public static void waitNewClient() {
        try {
            Socket socket = serverSocket.accept();
            ++count;
            System.out.println("????????????????????????"+count);
 
            // ?????????????????? Client ???
            addNewClient(socket);
            
        } catch (IOException e) {}
    }
 
    // ???????????? Client ???
    public static void addNewClient(final Socket socket) throws IOException {
        // ???????????????????????????
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    // ???????????? Client ???
                    clients.add(socket);
                    // ?????????????????? 
                    BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));      
                    // ???Socket????????????????????????
                    while (socket.isConnected()) {
                        // ???????????????????????????
                        String msg= br.readLine();
                        if(msg==null){
                         System.out.println("Client Disconnected!");
                         break;
                        }
                        //????????????
                        fr.write(msg + "\n");
                        fr.flush();
                        System.out.println(msg);
                        // ?????????????????????????????????
                        castMsg(msg);
                    }
                } catch (IOException e) {
                 e.getStackTrace();
                }
                finally{
                   // ???????????????
                   clients.remove(socket);
                   --count;
                   System.out.println("????????????????????????"+count);
                }
            }
        });
 
        // ???????????????
        t.start();
    }
 
    // ?????????????????????????????????
    public static void castMsg(String Msg){
        // ??????socket??????
        Socket[] clientArrays =new Socket[clients.size()]; 
        // ??? clients ????????????????????? clientArrays
        clients.toArray(clientArrays);
        // ?????? clientArrays ?????????????????????
        for ( Socket socket : clientArrays ) {
            try {
                // ????????????????????????
                BufferedWriter bw;
                bw = new BufferedWriter( new OutputStreamWriter(socket.getOutputStream()));
                // ?????????????????????
                bw.write(Msg+"\n");
                // ????????????
                bw.flush();
            } catch (IOException e) {}
        }
    }
	
}
*/