package if4031.client.command;

import if4031.client.IRCClient;
import if4031.client.rpc.RPCException;

class JoinChannelCommand implements Command {
    private final String channelName;

    JoinChannelCommand(String _channelName) {
        channelName = _channelName;
    }

    @Override
    public String toString() {
        return "/join " + channelName;
    }

    @Override
    public void execute(IRCClient ircClient) throws RPCException {
        // TODO impl
    }
}
