import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Server {

    private Map<Integer,Account> accountsMap;

    public Server() {
        accountsMap = Collections.synchronizedMap(new HashMap<>());
    }

    public void setAccountsMap(Map<Integer, Account> accountsMap) {
        this.accountsMap = accountsMap;
    }

    public Map<Integer, Account> getAccountsMap() {
        return accountsMap;
    }

    public synchronized Account getAccount(int key) {
        return accountsMap.get(key);
    }

    public synchronized Account getAccount(String username) {
        for (Account account : accountsMap.values()) {
            if (account.getUsername().equals(username)) {
                return account;
            }
        }
        return null;
    }

    public synchronized ArrayList<String> getAccountsNames() {

        ArrayList<String> names = new ArrayList<>();

        for (Account account : accountsMap.values()) {
            names.add(account.getUsername());
        }
        return names;
    }

    public synchronized boolean accountExists(int authToken) {
        return accountsMap.containsKey(authToken);
    }

    public synchronized void addAccount(Account account) {
        accountsMap.put(account.getAuthToken(),account);
    }



    public synchronized boolean accountExists(String username) {
        for (Account account : accountsMap.values()) {
            if (account.getUsername().equals(username)) {
                return true;
            }
        }
        return false;
    }


    public static void main(String[] args) {
        //creating Server object
        Server server = new Server();

        try (ServerSocket serverSocket = new ServerSocket(Integer.parseInt(args[0]))) {
            while (true)
                new MessagingServer(serverSocket.accept(), server).start();

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }


}