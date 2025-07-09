package org.example.Email;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.Enumeration;
import java.util.List;
import java.util.Scanner;

public class Sendmail {

    public static void main(String[] args) {

    getIpAddress();

    }

    static   BufferedReader reader;
    static BufferedWriter writer;

    static void sendMail(){
        String smtpServer="smtp.gmail.com";
        int port=465;
        String sendMail="s2111076121@ru.ac.bd";
        String rcptMail="aziz29ru@gmail.com";
        String password="zzfw ixwf guzw imcz ";

        String title="2111076121";
        String message= LocalDateTime.now().toLocalDate().toString();

        try(SSLSocket server= (SSLSocket) SSLSocketFactory.getDefault().createSocket(smtpServer,port)){

            reader=new BufferedReader(new InputStreamReader(server.getInputStream()));
            writer=new BufferedWriter(new OutputStreamWriter(server.getOutputStream()));

            send("EHLO "+smtpServer);
            receive();
            receive();
            receive();
            receive();
            receive();
            receive();
            receive();
            receive();
            receive();
            send("AUTH LOGIN");
            receive();
            send(new String(Base64.getEncoder().encode(sendMail.getBytes())));
            receive();
            send(new String(Base64.getEncoder().encode(password.getBytes())));
            receive();
            send("MAIL FROM:<"+sendMail+">");
            receive();
            send("RCPT TO:<"+rcptMail+">");
            receive();
            send("DATA");
            receive();
            send("FROM:"+sendMail);
            send("TO:"+rcptMail);
            send("Subject:"+title);
            send(message);
            send(".");
            receive();
            send("QUIT");
            receive();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
    static void send(String cmd){
        try{
            writer.write(cmd+"\r\n");
            writer.flush();
            System.out.println("Client: "+cmd);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    static void receive(){
        try{
            System.out.println("Server: "+reader.readLine());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public  static void getIpAddress(){
        try {
            /**
             * Get All Network Interface
             */
            Enumeration<NetworkInterface> networkInterface= NetworkInterface.getNetworkInterfaces();
           while (networkInterface.hasMoreElements()){
            NetworkInterface network=networkInterface.nextElement();
               System.out.println("Interface Name:"+network.getName());
               if(network.getName().equals("wlp0s20f3")){
                  byte[] hardwareAddress=  network.getHardwareAddress();
                  StringBuilder mac=new StringBuilder();
                  for (byte b:hardwareAddress){
                      mac.append(String.format("%02X:",b));
                  }
                  System.out.println("Mac Address: "+mac);
                  Enumeration<InetAddress> address=network.getInetAddresses();
                   while (address.hasMoreElements()){
                       InetAddress add=address.nextElement();
                        if(add instanceof Inet4Address){
                            System.out.println("IPv4:"+add.getHostAddress());
                        }
                        else if(add instanceof Inet6Address){
                            System.out.println("IPv6:"+add.getHostAddress());
                        }

                   }
               }

               else if (network.getName().equals("docker0")){
                   System.out.println("DisplayName "+network.getDisplayName());
                   byte[] hardwareAddress=network.getHardwareAddress();
                   StringBuilder macAdd=new StringBuilder();
                   for(byte b:hardwareAddress){
                       macAdd.append(String.format("%02X:",b));
                   }
                   System.out.println(network.getName()+" MAC: "+macAdd);
                   Enumeration<InetAddress> addressEnumeration=network.getInetAddresses();
                   while (addressEnumeration.hasMoreElements()){
                       InetAddress add=addressEnumeration.nextElement();
                       if(add instanceof Inet4Address){
                           System.out.println("IPv4:"+add.getHostAddress());
                       }
                       else if(add instanceof Inet6Address){
                           System.out.println("IPv6:"+add.getHostAddress());
                       }

                   }

               }
           }


        } catch (Exception e) {

            System.out.println(e.getMessage());
        }
    }

}


