import exception.ChannelException;
import if4031.common.Message;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by nim_13512065 on 9/17/15.
 */
public class IRCData {
    private static int pointer = 1;
    private List<IRCChannel> ircChannels;
    private List<IRCUser> ircUsers;
    public IRCData() {
        this.setIrcChannels(new ArrayList<IRCChannel>());
        this.setIrcUsers(new ArrayList<IRCUser>());
    }

    public IRCData (IRCData ircData) {
        this.setIrcChannels(new ArrayList<IRCChannel>());
        for (IRCChannel ircChannel : ircData.getIrcChannels()) {
            this.getIrcChannels().add(ircChannel);
        }

        this.setIrcUsers(new ArrayList<IRCUser>());
        for (IRCUser ircUser : ircData.getIrcUsers()) {
            this.getIrcUsers().add(ircUser);
        }
    }

    public void addUser () {
        int uid = this.generateUserId();
        String nickname = this.generateNickname();
        IRCUser ircUser = new IRCUser(uid, nickname);
        this.ircUsers.add(ircUser);
    }

    public IRCUser findIRCUserByUserId(int userId) {
        for (IRCUser ircuser : this.getIrcUsers()) {
            if (ircuser.getUserId() == userId) {
                return ircuser;
            }
        }

        return null;
    }

    public IRCChannel findIRCChannel (String channel) {
        for (IRCChannel ircChannel : this.getIrcChannels()) {
            if (ircChannel.equals(channel)) {
                return ircChannel;
            }
        }

        return null;
    }

    public boolean changeNick(int userId, String nickname) {
        for (IRCUser ircuser : this.getIrcUsers()) {
            if (ircuser.getUserId() == userId) {
                ircuser.setNickname(nickname);
                return true;
            }
        }
        return false;
    }

    public String generateNickname() {
        return new BigInteger(130, new SecureRandom()).toString(32);
    }

    private synchronized int generateUserId() {
        int ret = pointer;
        pointer++;
        return ret;
    }


    public List<IRCChannel> getIrcChannels() {
        return ircChannels;
    }

    public void setIrcChannels(List<IRCChannel> ircChannels) {
        this.ircChannels = ircChannels;
    }

    public List<IRCUser> getIrcUsers() {
        return ircUsers;
    }


    public void setIrcUsers(List<IRCUser> ircUsers) {
        this.ircUsers = ircUsers;
    }


    public int login(String nickname) {
        int uid = this.generateUserId();
        IRCUser ircUser = new IRCUser(uid, nickname);
        this.ircUsers.add(ircUser);
        return uid;
    }

    public void logout(int user) {
        IRCUser ircUser = this.findIRCUserByUserId(user);
        this.getIrcUsers().remove(ircUser);
        this.getIrcChannels().remove(ircUser.getUserId());
    }

    public void joinChannel(int user, String channel) throws ChannelException {
        IRCUser ircUser = this.findIRCUserByUserId(user);
        IRCChannel ircChannel = this.findIRCChannel(channel);
        if (ircChannel == null) {
            IRCChannel newircChannel = new IRCChannel(channel);
            newircChannel.addUser(user);
            this.getIrcChannels().add(newircChannel);

            ircUser.addIRCChannels(channel); //reference
        } else {
            ircChannel.addUser(user);
            ircUser.addIRCChannels(ircChannel.getChannelName());
        }
    }

    public List<Message> getMessage(int user) {
        IRCUser ircUser = this.findIRCUserByUserId(user);
        List<Message> messages = new ArrayList<Message> (ircUser.getMessages());
        return messages;
    }

    public void leaveChannel(int user, String channel) {
        IRCUser ircUser = this.findIRCUserByUserId(user);
        try {
            IRCChannel removed = ircUser.removeIRCChannel(channel);
            IRCChannel ircChannel = this.findIRCChannel(removed.getChannelName());
            ircChannel.removeUser(user);
        } catch (ChannelException e) {
            e.printStackTrace();
        }
    }

    public List<Message> sendMessageToChannel(int user, String channel, Message message) {
        IRCUser myIRCUser = this.findIRCUserByUserId(user);
        myIRCUser.addMessage(message);

        IRCChannel ircChannel = this.findIRCChannel(channel);
        List<Integer> recipients = ircChannel.getIntegers();
        for (Integer recipient : recipients) {
            if (recipient != null) {
                IRCUser ircUser = this.findIRCUserByUserId(recipient);
                ircUser.addMessage(message);
            }
        }
        return myIRCUser.getMessages();
    }

    public List<Message> sendMessage(int user, Message message) {
        IRCUser myIRCUser = this.findIRCUserByUserId(user);
        myIRCUser.addMessage(message);

        List<String> ircChannelNames = myIRCUser.getIrcChannelNames();
        for (String ircChannelName : ircChannelNames) {

            IRCChannel ircChannel = this.findIRCChannel(ircChannelName);
            List<Integer> recipients = ircChannel.getIntegers();
            for (Integer recipient : recipients) {
                if (recipient != null) {
                    IRCUser ircUser = this.findIRCUserByUserId(recipient);
                    ircUser.addMessage(message);
                }
            }
        }
        return myIRCUser.getMessages();
    }
}
