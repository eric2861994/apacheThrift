import if4031.common.IRCService;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TSimpleServer;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TServerTransport;

/**
 * Created by nim_13512065 on 9/17/15.
 */
public class Server {
    public Server() {

    }


    public static void main (String[] args) {
        final IRCService.Processor processor;
        try {
            final ServerHandler handler = new ServerHandler();
            processor = new IRCService.Processor(handler);
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    listener(processor);
                }
            };
            new Thread(runnable).start();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void listener (IRCService.Processor processor) {
        try {
            TServerTransport tServerTransport = new TServerSocket(9090);
            TServer tserver = new TSimpleServer(new TServer.Args(tServerTransport).processor(processor));
            System.out.println("server starts...");
            tserver.serve();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
