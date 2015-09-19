package if4031.client.command;

import if4031.client.IRCClient;
import if4031.client.rpc.Message;
import if4031.client.rpc.RPCClient;
import if4031.client.rpc.RPCException;

import java.util.List;

class GetMessagesCommand implements Command {

    @Override
    public String toString() {
        return "/refresh";
    }

    @Override
    public void execute(IRCClient ircClient, RPCClient rpcClient) throws RPCException {
        List<Message> messages = rpcClient.getMessages(ircClient.getUserID());
        ircClient.getIrcClientListener().notifyMessageArrive(messages);
    }
}
