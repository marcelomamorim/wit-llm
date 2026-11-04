/*
 * @test
 * @summary Checks the public accept() behavior of AsynchronousServerSocketChannelImpl through AsynchronousServerSocketChannel.
 * @run main AsynchronousServerSocketChannelImplWitupTest
 */

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.ClosedChannelException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class AsynchronousServerSocketChannelImplWitupTest {

    public static void main(String[] args) throws Exception {
        testAcceptReturnsPendingFutureWhenBound();
        testAcceptOnClosedChannelFailsWithClosedChannelException();
    }

    static void testAcceptReturnsPendingFutureWhenBound() throws Exception {
        AsynchronousServerSocketChannel server = AsynchronousServerSocketChannel.open();
        try {
            server.bind(new InetSocketAddress(InetAddress.getLoopbackAddress(), 0));
            Future<AsynchronousSocketChannel> f = server.accept();
            if (f == null) {
                throw new RuntimeException("accept() returned null");
            }
            if (f.isDone()) {
                throw new RuntimeException("accept() future completed without a client connection");
            }
        } finally {
            server.close();
        }
    }

    static void testAcceptOnClosedChannelFailsWithClosedChannelException() throws Exception {
        AsynchronousServerSocketChannel server = AsynchronousServerSocketChannel.open();
        server.close();
        Future<AsynchronousSocketChannel> f = server.accept();
        if (f == null) {
            throw new RuntimeException("accept() returned null on closed channel");
        }
        try {
            f.get(10, TimeUnit.SECONDS);
            throw new RuntimeException("accept() on closed channel did not fail");
        } catch (ExecutionException e) {
            Throwable cause = e.getCause();
            if (!(cause instanceof ClosedChannelException)) {
                throw new RuntimeException("unexpected cause: " + cause, e);
            }
        }
    }
}
