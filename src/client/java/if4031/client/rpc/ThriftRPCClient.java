package if4031.client.rpc;

import if4031.common.IRCService;
import org.apache.thrift.TException;

import java.util.ArrayList;
import java.util.List;

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
     * @param remoteMessages remote messages
     * @return local messages
     */
    private List<Message> remoteToLocalMessages(List<if4031.common.Message> remoteMessages) {
        List<Message> localMessages = new ArrayList<>();
        for (if4031.common.Message oneRemoteMessage : remoteMessages) {
            Message oneLocalMessage = new Message(
                    oneRemoteMessage.getSender(),
                    oneRemoteMessage.getChannel(),
                    oneRemoteMessage.getBody(),
                    oneRemoteMessage.getSendTime()
            );
            localMessages.add(oneLocalMessage);
        }

        return localMessages;
    }

    @Override
    public List<Message> getMessages(int user) throws RPCException {
        try {
            List<if4031.common.Message> remoteMessages = thriftClient.getMessage(user);

            return remoteToLocalMessages(remoteMessages);

        } catch (TException e) {
            throw new RPCException(e);
        }
    }

    private if4031.common.Message stringToRemoteMessage(String message) {
        return new if4031.common.Message("", "", message, 0);
    }

    @Override
    public List<Message> sendMessageToChannel(int user, String channel, String message) throws RPCException {
        if4031.common.Message sendMessage = stringToRemoteMessage(message);
        try {
            List<if4031.common.Message> remoteMessages = thriftClient.sendMessageToChannel(user, channel, sendMessage);

            return remoteToLocalMessages(remoteMessages);

        } catch (TException e) {
            throw new RPCException(e);
        }
    }

    @Override
    public List<Message> sendMessage(int user, String message) throws RPCException {
        if4031.common.Message sendMessage = stringToRemoteMessage(message);
        try {
            List<if4031.common.Message> remoteMessages = thriftClient.sendMessage(user, sendMessage);

            return remoteToLocalMessages(remoteMessages);

        } catch (TException e) {
            throw new RPCException(e);
        }
    }

    @Override
    public void leaveChannel(int user, String channel) throws RPCException {
        try {
            thriftClient.leaveChannel(user, channel);
        } catch (TException e) {
            throw new RPCException(e);
        }
    }
}
