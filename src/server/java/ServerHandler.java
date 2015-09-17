import exception.ChannelException;
import if4031.common.IRCService;
import if4031.common.Message;
import org.apache.thrift.TException;

import java.util.List;

/**
 * Created by nim_13512065 on 9/17/15.
 */
public class ServerHandler implements IRCService.Iface {
    private IRCData ircData;
    public ServerHandler() {
        this.setIrcData(new IRCData());
    }

    @Override
    public int login(String nickname) throws TException {
        return ircData.login(nickname);
    }

    @Override
    public boolean changeNickname(int user, String newNick) throws TException {
        return ircData.changeNick(user, newNick);
    }

    @Override
    public void logout(int user) throws TException {
        ircData.logout(user);
    }

    @Override
    public void joinChannel(int user, String channel) throws TException {
        try {
            ircData.joinChannel(user, channel);
        } catch (ChannelException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Message> getMessage(int user) throws TException {
        return ircData.getMessage(user);
    }

    @Override
    public List<Message> sendMessageToChannel(int user, String channel, Message message) throws TException {
        return ircData.sendMessageToChannel(user, channel, message);
    }

    @Override
    public List<Message> sendMessage(int user, Message message) throws TException {
        return ircData.sendMessage(user, message);
    }


    @Override
    public void leaveChannel(int user, String channel) throws TException {
        ircData.leaveChannel(user, channel);
    }

    public IRCData getIrcData() {
        return ircData;
    }

    public void setIrcData(IRCData ircData) {
        this.ircData = ircData;
    }
}
