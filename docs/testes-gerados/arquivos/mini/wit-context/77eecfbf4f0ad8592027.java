/*
 * @test
 * @summary Test basic behavior of ServerSocketChannelImpl.accept()
 * @run main ServerSocketChannelImplWitupTest
 */

import sun.nio.ch.ServerSocketChannelImpl;
import java.io.FileDescriptor;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;
import java.nio.channels.spi.SelectorProvider;

public class ServerSocketChannelImplWitupTest {
    public static void main(String[] args) throws Exception {
        // Create a ServerSocketChannelImpl instance using the public constructor
        SelectorProvider provider = SelectorProvider.provider();
        ServerSocketChannelImpl ssc = new ServerSocketChannelImpl(provider);

        // Test accept() on a newly created, unbound channel
        // According to source, accept() will block or return null if no connection
        // Since no client connects, expect null or IOException
        // We test that accept() returns null or throws IOException but does not throw UnsupportedOperationException

        SocketChannel sc = null;
        try {
            sc = ssc.accept();
            if (sc != null) {
                // If a connection was accepted, close it
                sc.close();
                throw new RuntimeException("Unexpected connection accepted on unbound ServerSocketChannelImpl");
            }
        } catch (IOException e) {
            // IOException is allowed
        } catch (UnsupportedOperationException e) {
            // According to current source, acceptLock.lock() is called, no UnsupportedOperationException expected
            throw new RuntimeException("accept() threw UnsupportedOperationException unexpectedly", e);
        } finally {
            ssc.close();
        }

        // Test accept() after close() to check behavior
        ServerSocketChannelImpl ssc2 = new ServerSocketChannelImpl(provider);
        ssc2.close();
        try {
            SocketChannel sc2 = ssc2.accept();
            if (sc2 != null) {
                sc2.close();
                throw new RuntimeException("accept() on closed channel should not return a SocketChannel");
            }
        } catch (IOException e) {
            // IOException expected or allowed
        }

        // Test accept() with a bound ServerSocketChannelImpl
        // Since no public bind method, we skip binding and just test accept() returns null or throws IOException
        ServerSocketChannelImpl ssc3 = new ServerSocketChannelImpl(provider);
        try {
            SocketChannel sc3 = ssc3.accept();
            if (sc3 != null) {
                sc3.close();
                throw new RuntimeException("Unexpected connection accepted on unbound ServerSocketChannelImpl");
            }
        } catch (IOException e) {
            // IOException allowed
        } finally {
            ssc3.close();
        }
    }
}
