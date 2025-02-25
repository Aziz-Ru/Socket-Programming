package org.example.Email;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Scanner;

public class Sendmail {

    public static void main(String[] args) {
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
