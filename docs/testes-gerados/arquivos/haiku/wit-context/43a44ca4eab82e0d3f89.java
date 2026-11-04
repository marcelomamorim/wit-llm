/*
 * @test
 * @summary Verifies that accept() returns a non-null Future for a bound channel and that closing the channel completes the pending accept exceptionally
 * @run main AsynchronousServerSocketChannelImplWitupTest
 */

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.channels.AsynchronousCloseException;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class AsynchronousServerSocketChannelImplWitupTest {

    public static void main(String[] args) throws Exception {
        try (AsynchronousServerSocketChannel ch = AsynchronousServerSocketChannel.open()) {
            ch.bind(new InetSocketAddress(InetAddress.getLoopbackAddress(), 0));
            Future<AsynchronousSocketChannel> f = ch.accept();
            if (f == null) {
                throw new RuntimeException("accept() returned null");
            }
            if (f.isDone()) {
                throw new RuntimeException("accept() completed without a connection");
            }
            ch.close();
            try {
                f.get(10, TimeUnit.SECONDS);
                throw new RuntimeException("pending accept completed normally after close");
            } catch (ExecutionException e) {
                if (!(e.getCause() instanceof AsynchronousCloseException)) {
                    throw new RuntimeException("unexpected cause: " + e.getCause(), e);
                }
            } catch (CancellationException e) {
                // acceptable: pending operation cancelled on close
            }
        }
    }
}
