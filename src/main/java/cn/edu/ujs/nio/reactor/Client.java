package cn.edu.ujs.nio.reactor;


import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.stream.IntStream;

public class Client {
    public static void main(String[] args) throws Exception {

        IntStream.range(0, 10).forEach(i -> {
            try {
                String sentence;
                String modifiedSentence;

                BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
                Socket clientSocket = new Socket("localhost", 7070);

                DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());


                //BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                //sentence = inFromUser.readLine();
                outToServer.writeBytes("Hello11122344 ..." + '\n');
                BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                sentence = inFromServer.readLine();
                System.out.println("Response from Server : " + sentence);
                clientSocket.close();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        });


    }
}
