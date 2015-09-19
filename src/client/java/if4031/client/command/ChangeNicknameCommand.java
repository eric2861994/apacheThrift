package if4031.client.command;

import if4031.client.IRCClient;
import if4031.client.rpc.RPCClient;
import if4031.client.rpc.RPCException;

class ChangeNicknameCommand implements Command {
    private String newNickname;

    ChangeNicknameCommand(String _newNickname) {
        newNickname = _newNickname;
    }

    @Override
    public String toString() {
        return "/nick " + newNickname;
    }

    @Override
    public void execute(IRCClient ircClient, RPCClient rpcClient) throws RPCException {
        boolean success = rpcClient.changeNickname(ircClient.getUserID(), newNickname);
        if (success) {
            ircClient.setNickname(newNickname);
            // TODO setNickname might not be needed
        } else {
            ircClient.getIrcClientListener().notifyFailedNicknameChange();
        }
    }
}
