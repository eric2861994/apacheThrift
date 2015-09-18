package if4031.client.command;

class LeaveChannelCommand implements Command {
    private final String channelName;

    LeaveChannelCommand(String _channelName) {
        channelName = _channelName;
    }

    @Override
    public void execute() {
        // TODO impl
    }

    @Override
    public String toString() {
        return "/leave " + channelName;
    }
}
