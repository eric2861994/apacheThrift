package if4031.client;

import if4031.client.command.Command;
import if4031.client.command.IRCCommandFactory;
import if4031.client.rpc.RPCClient;
import if4031.client.rpc.RPCException;
import if4031.client.rpc.ThriftRPCClient;
import if4031.common.IRCService;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;

// TODO this is the main program
public class IRCClient {

    private final IRCCommandFactory ircCommandFactory = new IRCCommandFactory();
    private final RPCClient rpcClient;
    private IRCClientListener ircClientListener;

    private String nickname;
    private int userID;
    private int refreshTime; // in milliseconds

    private ClientState clientState = ClientState.LOGGED_OUT;
    private ThreadState threadState = ThreadState.STOPPED;

    IRCClient(String server, int port, int _refreshTime) {
        TTransport transport = new TSocket(server, port);
        TProtocol protocol = new TBinaryProtocol(transport);
        IRCService.Client client = new IRCService.Client(protocol);
        rpcClient = new ThriftRPCClient(client);

        refreshTime = _refreshTime;
    }

    void monitorThreadState() {
        if (clientState == ClientState.JOINED_CHANNEL && threadState == ThreadState.STOPPED) {
            // TODO impl
            threadState = ThreadState.STARTED;

        } else if (clientState != ClientState.JOINED_CHANNEL && threadState == ThreadState.STARTED) {
            // TODO impl
            threadState = ThreadState.STOPPED;
        }
    }

    private void setState(ClientState _clientState) {
        clientState = _clientState;
        monitorThreadState();
    }

    public void setIrcClientListener(IRCClientListener _ircClientListener) {
        ircClientListener = _ircClientListener;
    }

    void login(String nickname) throws RPCException {
        userID = rpcClient.login(nickname);
        setState(ClientState.LOGGED_IN);
    }

    ParseExecuteResult parseExecute(String line) {
        IRCCommandFactory.ParseResult parseResult = ircCommandFactory.parse(line);

        IRCCommandFactory.ParseStatus status = parseResult.getStatus();
        if (status == IRCCommandFactory.ParseStatus.OK) {
            Command command = parseResult.getCommand();
//            command.execute();
        }

        return new ParseExecuteResult(status, parseResult.getReason());
    }

    void logout() throws RPCException {
        rpcClient.logout(userID);
        setState(ClientState.LOGGED_OUT);
    }

    public IRCClientListener getIrcClientListener() {
        return ircClientListener;
    }

    public int getUserID() {
        return userID;
    }

    public void setNickname(String _nickname) {
        nickname = _nickname;
        ircClientListener.notifyNicknameChange(nickname);
    }

    private enum ClientState {
        JOINED_CHANNEL,
        LOGGED_IN,
        LOGGED_OUT
    }

    private enum ThreadState {
        STARTED,
        STOPPED
    }

    class ParseExecuteResult {
        IRCCommandFactory.ParseStatus status;
        String reason;

        ParseExecuteResult(IRCCommandFactory.ParseStatus _status, String _reason) {
            status = _status;
            reason = _reason;
        }

        public IRCCommandFactory.ParseStatus getStatus() {
            return status;
        }

        public String getReason() {
            return reason;
        }
    }
}
