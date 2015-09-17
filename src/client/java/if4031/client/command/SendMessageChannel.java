package if4031.client.command;

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
    public void execute() {
        // TODO impl
    }

    @Override
    public String toString() {
        return '@' + channelName + ' ' + message;
    }
}
