package org.example;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    public static   void  runServer(){
        try(ServerSocket server=new ServerSocket(3000)) {
            System.out.println("Server is Listening Port 3000");
            /**
             * Make an InputStreamReader chained to the Socket’s
             * low-level (connection) input stream
             * InputStreamReader is a bridge between a low level byte stream
             * (like on coming from character )
             * and high level character stream like bufferedReader
             *
             */
            /**
             * To write data to a Socket, use Buffered writer
             *
             */
            /**
             * The server goes into a permanent loop waiting for client request
             */

            while (true){
                Socket socket=server.accept();
                BufferedReader reader=new BufferedReader(new InputStreamReader(socket.getInputStream()));
                BufferedWriter writer=new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                System.out.println("New Client Connected");
                String clientMsg;

                while ((clientMsg=reader.readLine())!=null){
                    if(clientMsg.equals("\\e\\n\\d")){
                        break;
                    }
                    System.out.println("Client: "+clientMsg);
                    writer.write("Server:"+clientMsg+"\n");
                    writer.flush();
                }


                /**
                 * writer.flush(); forces the BufferedWriter to immediately send
                 * any buffered data to the output stream.
                 */


            }

        } catch (Exception e) {

            System.out.println(e.getMessage());
        }
    }
}
