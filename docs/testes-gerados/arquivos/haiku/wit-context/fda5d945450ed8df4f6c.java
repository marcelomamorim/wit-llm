/*
 * @test
 * @summary Verify SelectionKey.interestOps() returns the current interest set and throws CancelledKeyException after the key is cancelled
 * @run main SelectionKeyImplWitupTest
 */

import java.io.IOException;
import java.nio.channels.CancelledKeyException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;

public class SelectionKeyImplWitupTest {

    public static void main(String[] args) throws IOException {
        try (Selector sel = Selector.open();
             ServerSocketChannel ssc = ServerSocketChannel.open()) {
            ssc.configureBlocking(false);
            SelectionKey key = ssc.register(sel, SelectionKey.OP_ACCEPT);

            if (key.interestOps() != SelectionKey.OP_ACCEPT) {
                throw new RuntimeException();
            }

            key.interestOps(0);
            if (key.interestOps() != 0) {
                throw new RuntimeException();
            }

            key.cancel();
            try {
                key.interestOps();
                throw new RuntimeException();
            } catch (CancelledKeyException expected) {
                // expected path: ensureValid() rejects a cancelled key
            }
        }
    }
}
