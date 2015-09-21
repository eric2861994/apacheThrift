package if4031.client;

import if4031.client.rpc.Message;
import org.apache.thrift.transport.TTransportException;

import java.io.PrintStream;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;
import java.util.concurrent.LinkedBlockingQueue;

class CLInterface implements IRCClientListener {

    private final Scanner scanner;
    private final PrintStream out;
    private final IRCClient ircClient;

    private Queue<String> messagesQ = new LinkedBlockingQueue<>();

    CLInterface(Scanner _scanner, PrintStream _out, IRCClient _ircClient) {
        scanner = _scanner;
        out = _out;
        ircClient = _ircClient;
    }

    private void printMessages() {
        while (!messagesQ.isEmpty()) {
            out.println(messagesQ.remove());
        }
    }

    private void run() {
        // display welcome message
        out.println(WELCOME_MESSAGE);

        // get nickname
        String nickname;
        do {
            out.print(NICKNAME_PROMPT);
            nickname = scanner.nextLine();
        } while (nickname.contains(" "));
        // TODO implement the case where user don't give a nickname

        ircClient.login(nickname);
        // TODO handle login failure

        // main loop
        String commandString;
        while (true) {
            printMessages();

            out.print(COMMAND_PROMPT);
            commandString = scanner.nextLine();

            if (commandString.equals("/exit")) {
                break;
            }

            if (commandString.equals("")) {
                // TODO impl

            } else {
                ircClient.parseExecute(commandString);
            }
        }

        ircClient.logout();
        printMessages();
        // TODO handle logout failure
    }

    @Override
    public void notifyFailure(int failureCode, String reason) {
        messagesQ.add("Failure " + failureCode + ": " + reason);
    }

    @Override
    public void notifyLeaveChannel(String channelName) {
        messagesQ.add("Left channel: " + channelName);
    }

    @Override
    public void notifyJoinChannel(String channelName) {
        messagesQ.add("Joined channel: " + channelName);
    }

    @Override
    public void notifyLogin() {
        messagesQ.add("Logged in.");
    }

    @Override
    public void notifyLogout() {
        messagesQ.add("Logged out.");
    }

    @Override
    public void notifyMessageArrive(List<Message> messages) {
        for (Message m : messages) {
            messagesQ.add("[" + m.getSendTime() + " ] #" + m.getChannel() + " (" + m.getSender() + "): " + m.getBody());
        }
    }

    @Override
    public void notifyNicknameChange(String newNickname) {
        messagesQ.add("Nickname changed to " + newNickname);
    }

    public static void main(String[] args) throws TTransportException {
//        String serverAddress = args[1];
//        int serverPort = Integer.parseInt(args[2]);
//        int refreshTime = Integer.parseInt(args[0]);
        String serverAddress = "localhost";
        int serverPort = 9090;
        int refreshTime = 5;

        // TODO handle user input robustly
        IRCClient ircClient = new IRCClient(serverAddress, serverPort, refreshTime);

        Scanner scanner = new Scanner(System.in);
        CLInterface clInterface = new CLInterface(scanner, System.out, ircClient);
        ircClient.setIrcClientListener(clInterface);

        ircClient.start();
        clInterface.run();
        ircClient.stop();
    }

    private static String PROGRAM_NAME = "Apache Thrift IRC";
    private static String WELCOME_MESSAGE = "Welcome to " + PROGRAM_NAME + "!\nEnter your nickname to login..\n";
    private static String ERROR_MESSAGE = "Error!";
    private static String NICKNAME_PROMPT = "nickname: ";
    private static String COMMAND_PROMPT = ">> ";
}
