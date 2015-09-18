package if4031.client;

import if4031.client.command.Command;
import if4031.client.command.IRCCommandFactory;

import java.io.PrintStream;
import java.util.Scanner;

class IRCClient {

    private final IRCCommandFactory ircCommandFactory;

    private ChannelState channelState = ChannelState.NOT_JOINED;
    private LoggedState loggedState = LoggedState.LOGGED_OUT;

    private IRCClient(IRCCommandFactory _ircCommandFactory) {
        ircCommandFactory = _ircCommandFactory;
    }

   

    private enum LoggedState {
        LOGGED_IN,
        LOGGED_OUT
    }

    private enum ChannelState {
        JOINED_CHANNEL,
        NOT_JOINED
    }
}
