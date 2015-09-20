package if4031.client.command;

import if4031.client.IRCClient;
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
    public void execute(IRCClient ircClient) throws RPCException {
        // TODO impl
    }
}
