package if4031.client.rpc;

import if4031.common.IRCService;
import org.apache.thrift.TException;

import java.util.ArrayList;
import java.util.List;

// TODO handle exceptional cases

class ThriftRPCClient implements RPCClient {

    private final IRCService.Client thriftClient;

    ThriftRPCClient(IRCService.Client _thriftClient) {
        thriftClient = _thriftClient;
    }

    @Override
    public int login(String nickname) throws RPCException {
        try {
            return thriftClient.login(nickname);
        } catch (TException e) {
            throw new RPCException(e);
        }
    }

    @Override
    public boolean changeNickname(int user, String newNick) throws RPCException {
        try {
            return thriftClient.changeNickname(user, newNick);
        } catch (TException e) {
            throw new RPCException(e);
        }
    }

    @Override
    public void logout(int user) throws RPCException {
        try {
            thriftClient.logout(user);
        } catch (TException e) {
            throw new RPCException(e);
        }
    }

    @Override
    public void joinChannel(int user, String channel) throws RPCException {
        try {
            thriftClient.joinChannel(user, channel);
        } catch (TException e) {
            throw new RPCException(e);
        }
    }

    /**
     * Changes remote messages to local messages.
     *
     * @param serverMessages remote messages
     * @return local messages
     */
    private List<Message> remoteToLocalMessages(List<if4031.common.Message> serverMessages) {
        List<Message> clientMessages = new ArrayList<>();
        for (if4031.common.Message oneServerMessage: serverMessages) {
            Message oneClientMessage = new Message(
                    oneServerMessage.getSender(),
                    oneServerMessage.getChannel(),
                    oneServerMessage.getBody(),
                    oneServerMessage.getSendTime()
            );
            clientMessages.add(oneClientMessage);
        }

        return clientMessages;
    }

    @Override
    public List<Message> getMessages(int user) throws RPCException {
        try {
            List<if4031.common.Message> serverMessages = thriftClient.getMessage(user);

            return remoteToLocalMessages(serverMessages);

        } catch (TException e) {
            throw new RPCException(e);
        }
    }

    @Override
    public List<Message> sendMessageToChannel(int user, String channel, String message) throws RPCException {
        if4031.common.Message clientMessage = new if4031.common.Message("", channel, message, 0);
        thriftClient.sendMessageToChannel(user, channel, message);
    }

    @Override
    public List<Message> sendMessage(int user, String message) throws RPCException {
        return null;
    }

    @Override
    public void leaveChannel(int user, String channel) throws RPCException {

    }
}
