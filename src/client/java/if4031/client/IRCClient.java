package if4031.client;

import if4031.client.command.Command;
import if4031.client.command.IRCCommandFactory;
import if4031.client.rpc.RPCClient;
import if4031.client.rpc.RPCException;
import if4031.client.rpc.ThriftRPCClient;
import if4031.common.IRCService;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.protocol.TProtocolFactory;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;

import java.io.PrintStream;
import java.util.Scanner;

// TODO this is the main program
class IRCClient {

    private final IRCCommandFactory ircCommandFactory = new IRCCommandFactory();
    private final RPCClient rpcClient;

    private int userID;

    private ChannelState channelState = ChannelState.NOT_JOINED;
    private LoggedState loggedState = LoggedState.LOGGED_OUT;

    IRCClient(String server, int port) {
        TTransport transport = new TSocket(server, port);
        TProtocol protocol = new TBinaryProtocol(transport);
        IRCService.Client client = new IRCService.Client(protocol);
        rpcClient = new ThriftRPCClient(client);
    }

    void login(String nickname) throws RPCException {
        // TODO handle login Exception
        userID = rpcClient.login(nickname);
    }

    boolean parseExecute(String line) {
        IRCCommandFactory.ParseResult result = ircCommandFactory.parse(line);
        // TODO implement this
    }

    void logout() throws RPCException {
        // TODO handle logout failure
        rpcClient.logout(userID);
    }

    private enum LoggedState {
        LOGGED_IN,
        LOGGED_OUT
    }

    private enum ChannelState {
        JOINED_CHANNEL,
        NOT_JOINED
    }
}
