package if4031.client.command;

class JoinChannelCommand implements Command {
    private final String channelName;

    JoinChannelCommand(String _channelName) {
        channelName = _channelName;
    }

    @Override
    public void execute() {
        // TODO impl
    }

    @Override
    public String toString() {
        return "/join " + channelName;
    }
}
