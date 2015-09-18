package if4031.client;

import if4031.client.rpc.RPCException;

import java.io.PrintStream;
import java.util.Scanner;

class CLInterface {

    private final Scanner scanner;
    private final PrintStream out;
    private final IRCClient ircClient = new IRCClient();
    private final RPC

    CLIProgram(Scanner _scanner, PrintStream _out) {
        scanner = _scanner;
        out = _out;
    }

    private void run() throws RPCException {
        // welcome message
        out.println(WELCOME_MESSAGE);

        // get nickname
        String nickname;
        do {
            out.print(NICKNAME_PROMPT);
            nickname = scanner.nextLine();
        } while (nickname.contains(" "));
        // TODO implement the case where user don't give a nickname

        // login
        ircClient.login(nickname);
        // TODO handle login failure

        // main loop
        do {
            out.print(COMMAND_PROMPT);
            String line = scanner.next();
        } while (ircClient.parseExecute(line));
        // TODO is /exit the only exit condition?

        ircClient.logout();
        // TODO handle logout failure
    }

    public static void main(String[] args) throws RPCException {
        Scanner scanner = new Scanner(System.in);
        CLInterface CLInterface = new CLInterface(scanner, System.out);
        CLInterface.run();
        // CLInterface terminated
    }

    private static String PROGRAM_NAME = "Apache Thrift IRC";
    private static String WELCOME_MESSAGE = "Welcome to " + PROGRAM_NAME + "!\nEnter your nickname to login..\n";
    private static String NICKNAME_PROMPT = "nickname: ";
    private static String COMMAND_PROMPT = ">> ";
}
