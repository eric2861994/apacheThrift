package if4031.client;

import if4031.client.config.ClientConfiguration;
import if4031.client.config.PropertyConfiguration;
import org.apache.thrift.transport.TTransportException;

import java.io.IOException;
import java.util.Scanner;

class ClientProgram {
    private static final String PROPERTY_FILE = "/client.properties";

    public static void main(String[] args) throws TTransportException, IOException {
        ClientConfiguration configuration = new PropertyConfiguration(PROPERTY_FILE);

        String serverAddress = configuration.getString("serverAddress");
        int serverPort = configuration.getInt("serverPort");

        int refreshMillis = configuration.getInt("refreshTimeMillis");
        int toleranceMillis = configuration.getInt("toleranceTimeMillis");


        IRCClient ircClient = new IRCClient(serverAddress, serverPort, refreshMillis, toleranceMillis);

        Scanner scanner = new Scanner(System.in);
        CLInterface clInterface = new CLInterface(scanner, System.out, ircClient);

        ircClient.setIrcClientListener(clInterface);

        ircClient.start();
        clInterface.run();

        ircClient.stop();
    }
}
