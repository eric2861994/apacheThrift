package if4031.client;

import java.io.PrintStream;
import java.util.Scanner;

class CLIProgram {
    private final Scanner scanner;
    private final PrintStream out;

    CLIProgram(Scanner _scanner, PrintStream _out) {
        scanner = _scanner;
        out = _out;
    }

    private void run() {
        // welcome message
        out.println(WELCOME_MESSAGE);

        // get nickname
        String nickname;
        do {
            out.print(NICKNAME_PROMPT);
            nickname = scanner.nextLine();
        } while (nickname.contains(" "));

        // login
        ircClient.login(nickname);

        // main loop
//        ircClient.parseExecute();
//        ircClient.logout();



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

    public static void main(String[] args) {
        System.out
        Scanner scanner = new Scanner(System.in);
        CLIProgram cliProgram = new CLIProgram();
        cliProgram.run();
        // cliProgram terminated
    }

    private static String PROGRAM_NAME = "Apache Thrift IRC";
    private static String WELCOME_MESSAGE = "Welcome to " + PROGRAM_NAME + "!\nEnter your nickname to login..\n";
    private static String NICKNAME_PROMPT = "nickname: ";
    private static String COMMAND_PROMPT = ">> ";
}
