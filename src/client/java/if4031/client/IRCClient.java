package if4031.client;

import java.util.Scanner;

public class IRCClient {
    public static String PROGRAM_NAME = "Apache Thrift IRC";
    public static String WELCOME_MESSAGE = "Welcome to " + PROGRAM_NAME + "!\nEnter your nickname to login..\n";
    public static String NICKNAME_PROMPT = "nickname: ";

    public void run() {
        // welcome message
        Scanner scin = new Scanner(System.in);
        System.out.println(WELCOME_MESSAGE);

        String nickname;
        // TODO use regex, use better error handling
        do {
            System.out.print(NICKNAME_PROMPT);
            nickname = scin.nextLine();
        } while (nickname.contains(" "));

        // main loop
        // cleanup
    }

    public static void main(String[] args) {
        IRCClient ircClient = new IRCClient();
        ircClient.run();
    }
}
