package if4031.client.command;

import if4031.client.IRCClient;

class LeaveChannelCommand implements Command {
    private final String channelName;

    LeaveChannelCommand(String _channelName) {
        channelName = _channelName;
    }

    @Override
    public void execute(IRCClient ircClient) {
        // TODO impl
    }

    @Override
    public String toString() {
        return "/leave " + channelName;
    }
}
