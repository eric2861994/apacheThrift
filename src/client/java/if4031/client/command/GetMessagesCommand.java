package if4031.client.command;

import if4031.client.IRCClient;
import if4031.client.rpc.RPCException;

class GetMessagesCommand implements Command {

    @Override
    public String toString() {
        return "/refresh";
    }

    @Override
    public void execute(IRCClient ircClient) throws RPCException {
        // TODO impl
    }
}
