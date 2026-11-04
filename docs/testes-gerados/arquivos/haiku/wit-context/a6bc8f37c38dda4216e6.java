/*
 * @test
 * @summary Verify SelectionKey.readyOps() through public API: valid key returns 0 before select, cancelled key throws CancelledKeyException
 * @run main SelectionKeyImplWitupTest
 */

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.channels.CancelledKeyException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;

public class SelectionKeyImplWitupTest {

    public static void main(String[] args) throws IOException {
        try (Selector selector = Selector.open();
             ServerSocketChannel ssc = ServerSocketChannel.open()) {
            ssc.bind(new InetSocketAddress(InetAddress.getLoopbackAddress(), 0));
            ssc.configureBlocking(false);
            SelectionKey key = ssc.register(selector, SelectionKey.OP_ACCEPT);

            if (!key.isValid()) {
                throw new RuntimeException("Newly registered key must be valid");
            }
            if (key.readyOps() != 0) {
                throw new RuntimeException("readyOps() must be 0 before any select operation");
            }

            key.cancel();
            if (key.isValid()) {
                throw new RuntimeException("Cancelled key must not be valid");
            }
            try {
                int ops = key.readyOps();
                throw new RuntimeException("readyOps() on cancelled key must throw CancelledKeyException, returned " + ops);
            } catch (CancelledKeyException expected) {
                // expected
            }
        }
    }
}
