package org.example.TCP;

import java.io.*;
import java.net.Socket;

public class TcpClient {

    public static void main(String[] args) {

        try (Socket socket=new Socket("localhost",3000)){
            BufferedWriter writer=new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            BufferedReader reader=new BufferedReader(new InputStreamReader(socket.getInputStream()));

            writer.write("Hi Server\n");
            writer.flush();
            String msgServer=reader.readLine();
            System.out.println("Server: "+msgServer);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
