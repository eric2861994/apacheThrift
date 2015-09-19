package if4031.client;

import if4031.client.rpc.Message;

import java.util.List;

public interface IRCClientListener {
    void notifyNicknameChange(String newNickname);

    void notifyFailedNicknameChange();

    void notifyMessageArrive(List<Message> messages);
}
