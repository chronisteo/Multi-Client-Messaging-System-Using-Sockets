import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {

    //implementing Client
    public static void main(String[] args) {

        try (Socket socket = new Socket(args[0],Integer.parseInt(args[1]))) {
            // Initializing BufferedReader,PrintWriter objects which will be used to communicate with the server
            BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter output = new PrintWriter(socket.getOutputStream(), true);

            // The message to the server
            String messageToServer = "";

            // Adding all the args[] arguments to the messageToServer separated by a space
            for (int i = 2; i < args.length; i++) {
                messageToServer += args[i] + " ";
            }

            // Sending the message
            output.println(messageToServer);

            // Reading the response from server
            while ((messageToServer = input.readLine()) != null) {
                System.out.println(messageToServer);
            }

        } catch (IOException e) {
            System.out.println("Client Error Detected: " + e.getMessage());

        }
    }
}