package if4031.client;

import if4031.client.command.Command;
import if4031.client.command.IRCCommandFactory;

import java.io.PrintStream;
import java.util.Scanner;

class IRCClient {

    private final Scanner scanner;
    private final PrintStream out;
    private final IRCCommandFactory ircCommandFactory;

    private IRCClient(Scanner _scanner, PrintStream _out, IRCCommandFactory _ircCommandFactory) {
        scanner = _scanner;
        out = _out;
        ircCommandFactory = _ircCommandFactory;
    }

    private void run() {
        // welcome message
        out.println(WELCOME_MESSAGE);

        String nickname;
        // TODO use regex, use better error handling
        do {
            out.print(NICKNAME_PROMPT);
            nickname = scanner.nextLine();
        } while (nickname.contains(" "));

        // main loop
        while (true) {
            out.print(COMMAND_PROMPT);
            String line = scanner.nextLine();
            IRCCommandFactory.ParseResult parseResult = ircCommandFactory.parse(line);

            IRCCommandFactory.ParseStatus status = parseResult.getStatus();
            if (status == IRCCommandFactory.ParseStatus.EXIT) {
                break;

            }


            if (status == IRCCommandFactory.ParseStatus.OK) {
                Command command = parseResult.getCommand();
                out.println("command: " + parseResult.getCommand());
                command.execute();

            } else if (status == IRCCommandFactory.ParseStatus.IGNORE) {
                // reprompt without any message

            } else if (status == IRCCommandFactory.ParseStatus.ERROR) {
                out.println("error: " + parseResult.getReason());
            }
        }

        // cleanup
    }

    private static String PROGRAM_NAME = "Apache Thrift IRC";
    private static String WELCOME_MESSAGE = "Welcome to " + PROGRAM_NAME + "!\nEnter your nickname to login..\n";
    private static String NICKNAME_PROMPT = "nickname: ";
    private static String COMMAND_PROMPT = ">> ";

    private enum ClientState {
        LOGGED_IN,
        LOGGED_OUT
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        IRCCommandFactory ircCommandFactory = new IRCCommandFactory();
        IRCClient ircClient = new IRCClient(scanner, System.out, ircCommandFactory);
        ircClient.run();
    }
}
