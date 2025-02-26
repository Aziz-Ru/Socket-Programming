package org.example.Email;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Enumeration;
import java.util.List;
import java.util.Scanner;

public class Sendmail {

    public static void main(String[] args) {

    getIpAddress();

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


    public static  void  sendMail(){
//        Scanner scanner=new Scanner(System.in);
        String smtpServer="smtp.gmail.com";
        int smtpPort=465;
        String title="This Send Mail Job Done By Java";
        String body="Ow Can You Send Message Guy";
//        System.out.println("Enter Sender Email:");
        String email="**@gm.com";
//        System.out.println("Enter Sender Email Password");
        String password="*****#";
//        System.out.println("Enter Receiver Email:");
        String receiver="aziu@gmail.com";

        try (SSLSocket socket=(SSLSocket) SSLSocketFactory.getDefault().createSocket(smtpServer,smtpPort)){

            BufferedReader reader=new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedWriter writer=new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            sendCMD(writer,"EHLO "+smtpServer+"\r\n");

            printServerResponse(reader);
            printServerResponse(reader);
            printServerResponse(reader);
            printServerResponse(reader);
            printServerResponse(reader);
            printServerResponse(reader);
            printServerResponse(reader);
            printServerResponse(reader);
            printServerResponse(reader);

            sendCMD(writer,"AUTH LOGIN\r\n");
            sendCMD(writer,new String(Base64.getEncoder().encode(email.getBytes(StandardCharsets.UTF_8)))+"\r\n");
            sendCMD(writer,new String(Base64.getEncoder().encode(password.getBytes(StandardCharsets.UTF_8)))+"\r\n");
            sendCMD(writer,"MAIL FROM:<"+email+">\r\n");
            sendCMD(writer,"RCPT TO:<"+receiver+">\r\n");
            sendCMD(writer,"DATA\r\n");
            sendCMD(writer,"FROM: "+email+"\r\n");
            sendCMD(writer,"TO: "+receiver+"\r\n");
            sendCMD(writer,"Subject: "+title+"\r\n");
            sendCMD(writer,body+"\r\n");
            sendCMD(writer,".\r\n");
            printServerResponse(reader);
            sendCMD(writer,"QUIT\r\n");
            printServerResponse(reader);

            writer.close();
            reader.close();
            System.out.println("Email sent Successfully");


        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public static void printServerResponse(BufferedReader reader){
       try {
           System.out.println("Server:"+reader.readLine());
       } catch (Exception e) {
           throw new RuntimeException(e);
       }
    }
    public static void   sendCMD(BufferedWriter writer,String cmd){
        try{
            writer.write(cmd);
            writer.flush();
            Thread.sleep(100);
            System.out.println("Command:"+cmd);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}
