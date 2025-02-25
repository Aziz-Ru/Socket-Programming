package org.example;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {
    public static void main(String[] args){
        runClient();
    }
    public static void runClient(){

        try(Socket client=new Socket("127.0.0.1",3000)){
            BufferedReader userinput=new BufferedReader(new InputStreamReader(System.in));
            BufferedReader reader=new BufferedReader((new InputStreamReader(client.getInputStream())));
            BufferedWriter writer=new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));

            while (true){
                System.out.print("Enter You Message:");
                String msg=userinput.readLine();
                if(msg==null){
                    break;
                }
                writer.write(msg+"\n");
                writer.flush();
                String response= reader.readLine();
                System.out.println(response);

            }



            reader.close();
            writer.close();
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}
