package if4031.client.command;

import if4031.client.IRCClient;
import if4031.client.rpc.RPCClient;
import if4031.client.rpc.RPCException;

/**
 * Abstract representation of IRC Command.
 */
public interface Command {

    /**
     * Execute the command.
     */
    void execute(IRCClient ircClient, RPCClient rpcClient) throws RPCException;
}
