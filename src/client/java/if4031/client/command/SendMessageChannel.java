package if4031.client.command;

import if4031.client.IRCClient;

/**
 * Command to send a message to a specific channel.
 */
class SendMessageChannel implements Command {
    private final String channelName;
    private final String message;

    SendMessageChannel(String _channelName, String _message) {
        channelName = _channelName;
        message = _message;
    }

    @Override
    public void execute(IRCClient ircClient) {
        // TODO impl
    }

    @Override
    public String toString() {
        return '@' + channelName + ' ' + message;
    }
}
