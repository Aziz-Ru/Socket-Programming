package org.example.TCP;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDateTime;

public class TcpServer {

    public static void main(String[] args) {
        try(ServerSocket server= new ServerSocket(3000)) {
            System.out.println("Server is Listening on Port 3000");
            while (true){
                Socket socket=server.accept();
                BufferedReader reader=new BufferedReader(new InputStreamReader(socket.getInputStream()));
                BufferedWriter writer=new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

                String client=reader.readLine();
                System.out.println("Client: "+client);
                writer.write(client+" "+ LocalDateTime.now().toLocalDate().toString());
                writer.newLine();
                writer.flush();

            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }



}
