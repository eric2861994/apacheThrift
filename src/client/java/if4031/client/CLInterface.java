package if4031.client;

import if4031.client.command.IRCCommandFactory;
import if4031.client.rpc.RPCException;

import java.io.PrintStream;
import java.util.Scanner;

class CLInterface {

    private final Scanner scanner;
    private final PrintStream out;
    private final IRCClient ircClient;

    CLInterface(Scanner _scanner, PrintStream _out, IRCClient _ircClient) {
        scanner = _scanner;
        out = _out;
        ircClient = _ircClient;
    }

    private void run() throws RPCException {
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
        IRCCommandFactory.ParseStatus status;
        do {
            out.print(COMMAND_PROMPT);
            commandString = scanner.nextLine();

            IRCClient.ParseExecuteResult parseExecuteResult = ircClient.parseExecute(commandString);
            status = parseExecuteResult.getStatus();
            if (status == IRCCommandFactory.ParseStatus.ERROR) {
                out.println(ERROR_MESSAGE);
                // TODO impl more robust error handling
            }
        } while (status != IRCCommandFactory.ParseStatus.EXIT);

        ircClient.logout();
        // TODO handle logout failure
    }

    public static void main(String[] args) throws RPCException {
        String serverAddress = args[1];
        int serverPort = Integer.parseInt(args[2]);
        int refreshTime = Integer.parseInt(args[0]);
        // TODO handle user input robustly
        IRCClient ircClient = new IRCClient(serverAddress, serverPort, refreshTime);

        Scanner scanner = new Scanner(System.in);
        CLInterface CLInterface = new CLInterface(scanner, System.out, ircClient);
        CLInterface.run();
    }

    private static String PROGRAM_NAME = "Apache Thrift IRC";
    private static String WELCOME_MESSAGE = "Welcome to " + PROGRAM_NAME + "!\nEnter your nickname to login..\n";
    private static String ERROR_MESSAGE = "Error!";
    private static String NICKNAME_PROMPT = "nickname: ";
    private static String COMMAND_PROMPT = ">> ";
}
