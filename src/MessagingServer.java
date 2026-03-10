import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class MessagingServer extends Thread {

    private Socket socket;
    private Server server;

    public MessagingServer(Socket socket, Server server) {
        this.socket = socket;
        this.server = server;
    }

    public void run() {

        try {
            // Initializing BufferedReader , PrintWriter objects which will be used to communicate with the client
            BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter output = new PrintWriter(socket.getOutputStream(), true);

            // Reading a message from the client
            String echoString = input.readLine();


            // Splitting the message from the client and storing it in a String array
            String[] clientOutput = echoString.split(" ");


            if(clientOutput[0].equals("1"))
                createAccount(clientOutput[1], output);
            else if(clientOutput[0].equals("2"))
                showAccounts(Integer.parseInt(clientOutput[1]), output);
            else if(clientOutput[0].equals("3"))
                sendMessage(Integer.parseInt(clientOutput[1]), clientOutput[2], getMessageBody(clientOutput), output);
            else if(clientOutput[0].equals("4"))
                showInbox(Integer.parseInt(clientOutput[1]), output);
            else if(clientOutput[0].equals("5"))
                readMessage(Integer.parseInt(clientOutput[1]), Integer.parseInt(clientOutput[2]), output);
            else if(clientOutput[0].equals("6"))
                deleteMessage(Integer.parseInt(clientOutput[1]), Integer.parseInt(clientOutput[2]), output);

        } catch (IOException e) {
            System.out.println("IOException Was Raised: " + e.getMessage());
        } catch (NullPointerException e) {
            System.out.println("Null Pointer Exception");
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                System.out.println("Socket Could Not Be Closed");
            }
        }
    }

    // function to get the body from the message
    private String getMessageBody(String[] bodyArray) {

        String actualMessage = "";
        for (int i = 3; i < bodyArray.length; i++) {
            actualMessage += bodyArray[i];
            actualMessage += " ";
        }
        return actualMessage;
    }

    //function to create account
    private void createAccount(String username,PrintWriter output) {

        if (!isValidUserName(username)) {
            output.println("Invalid Username");
            return;
        }

        if (server.accountExists(username)) {
            output.println("Sorry, the user already exists");
            return;
        }

        int authToken = getAuthToken();
        server.addAccount(new Account(username, authToken));
        output.println(authToken);
    }


    //function to check if a username is valid
    public boolean isValidUserName(String username)
    {

        // Regex to check valid username.
        String regex = "^[A-Za-z]\\w{2,29}$";

        // Compile the ReGex
        Pattern p = Pattern.compile(regex);

        // If the username is empty
        // return false
        if (username == null) {
            return false;
        }

        // Pattern class contains matcher() method
        // to find matching between given username
        // and regular expression.
        Matcher m = p.matcher(username);

        // Return if the username
        // matched the ReGex
        return m.matches();
    }


    //function to print all accounts , using authToken
    private void showAccounts(int authToken, PrintWriter output) {

        if (!server.accountExists(authToken)) {
            output.println("Invalid Auth Token");
            return;
        }

        ArrayList<String> names = server.getAccountsNames();
        String stringToEcho = "";
        int counter = 1;
        for (String name : names) {
            stringToEcho += counter + ". " + name + "\n";
            counter++;
        }
        output.println(stringToEcho);
    }


    //function to send a message using specific authToken
    private void sendMessage(int authToken, String recipient, String message, PrintWriter output) {

        if (!server.accountExists(authToken)) {
            output.println("Invalid Auth Token");
            return;
        }

        Account receiverAccount = server.getAccount(recipient);
        if (receiverAccount == null) {
            output.println("User does not exist");
            return;
        }

        receiverAccount.addMessage(new Message(server.getAccount(authToken).getUsername(), receiverAccount.getUsername(), message));
        output.println("OK");
    }

    //function to print all messages , using authToken
    private void showInbox(int authToken, PrintWriter output) {

        if (!server.accountExists(authToken)) {
            output.println("Invalid Auth Token");
            return;
        }

        Account accountUser = server.getAccount(authToken);
        List<Message> messages = accountUser.getMessagesBox();
        String echoString = "";

        for (int i = 0; i < messages.size(); i++) {
            echoString += i + ". from: " + messages.get(i).getSender();
            if (messages.get(i).isRead()) {
                echoString += "\n";
            } else {
                echoString += "*\n";
            }
        }

        output.println(echoString);

    }

    //function to read a message from inbox , using specific ID
    private void readMessage(int authToken, int messageID, PrintWriter output) {
        if (!server.accountExists(authToken)) {
            output.println("Invalid Auth Token");
            return;
        }

        Account account = server.getAccount(authToken);
        Message message = account.getMessage(messageID);
        if (message == null) {
            output.println("Message ID does not exist");
            return;
        }
        message.setRead(true);
        output.println("(" + message.getSender() + ")" + message.getBody());
    }


    //function to delete a message from inbox , using specific ID
    private void deleteMessage(int authToken, int messageID, PrintWriter output) {
        if (!server.accountExists(authToken)) {
            output.println("Invalid Auth Token");
            return;
        }

        Account account = server.getAccount(authToken);
        if (!account.removeMessage(messageID)) {
            output.println("Message does not exist");
            return;
        }
        output.println("OK");
    }

    // return an int from 0 to 99999
    private int getAuthToken() {

        Random rand = new Random();
        // Setting the upper bound to generate the random number(authToken) in specific range
        int upperbound = 100000;
        // Generating the random value from 0 to 99999
        // using nextInt()
        int int_random = rand.nextInt(upperbound);
        return int_random;

    }
}