package if4031.client.command;

public class IRCCommandFactory {
    public enum ParseStatus {
        OK, ERROR, EXIT, IGNORE
    }

    public class ParseResult {
        private final ParseStatus status;
        private final String reason;
        private final Command command;

        public ParseResult(ParseStatus _status, String _reason, Command _command) {
            status = _status;
            reason = _reason;
            command = _command;
        }

        public ParseStatus getStatus() {
            return status;
        }

        public String getReason() {
            return reason;
        }

        public Command getCommand() {
            return command;
        }
    }

    public ParseResult parse(String line) {
        /*
        format of a command: /<command> <params> or @<channelName> <message> or \<message> or <message>
        <command> = nick|join|leave|exit|refresh
        for nick command, <params> = <nickname>
        for join|leave command, <params> = <channelName>
        for exit|refresh command, <params> = <EMPTY>
        <nickname>|<channelName> = <WORD>
        <message> = <STRING>
        */
        if (line.length() == 0) {
            return new ParseResult(ParseStatus.IGNORE, IGNORE_TEXT, null);
        }

        // line must contains at least one character
        switch (line.charAt(0)) {
            /*
            handles line format: /<command> <params>
            <command> = nick|join|leave|exit|refresh
            for nick command, <params> = <nickname>
            for join|leave command, <params> = <channelName>
            for exit|refresh command, <params> = <EMPTY>
            <nickname>|<channelName> = <WORD>
            */
            case '/': {
                String[] tokens = line.substring(1).split("\\s+");
                if (tokens.length == 1) {
                    String firstToken = tokens[0].toLowerCase();
                    if (firstToken.equals("exit")) {
                        return new ParseResult(ParseStatus.EXIT, "", null);
                    }

                } else {
                    return new ParseResult(ParseStatus.ERROR, "Command not found", null);
                }
            }

            /*
            format of a command: /<command> <params> or @<channelName> <message> or \<message> or <message>
            <command> = nick|join|leave|exit|refresh
            for nick command, <params> = <nickname>
            for join|leave command, <params> = <channelName>
            for exit|refresh command, <params> = <EMPTY>
            <nickname>|<channelName> = <WORD>
            <message> = <STRING>
            */
            case '@': {
                // split string in two with the first occurence of a space, discarding the space itself.
                int firstSpaceIdx = line.indexOf(" ");
                String channelName = line.substring(1, firstSpaceIdx);
                if (channelName.equals("")) {
                    return new ParseResult(ParseStatus.ERROR, "Channel name is empty", null);
                }
                String message = line.substring(firstSpaceIdx + 1);
                if (message.equals("")) {
                    return new ParseResult(ParseStatus.ERROR, "Message is empty", null);
                }
                return new ParseResult(ParseStatus.OK, "", new SendMessageChannel(channelName, message));
            }

            /*
            handles line format: \<message>
            <message> = <STRING>
            */
            case '\\': {

            }

            default:
                return new ParseResult(ParseStatus.ERROR, "First character is not '/' or '@'", null);
        }
    }

    private static String IGNORE_TEXT = "nothing entered";
}
