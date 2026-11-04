/*
 * @test
 * @summary Verifies SelectionKeyImpl.interestOps() through the public SelectionKey API, including cancelled-key behavior
 * @run main SelectionKeyImplWitupTest
 */

import java.io.IOException;
import java.nio.channels.CancelledKeyException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;

public class SelectionKeyImplWitupTest {

    public static void main(String[] args) throws IOException {
        testInitialInterestOps();
        testInterestOpsAfterSet();
        testInterestOpsZero();
        testInterestOpsOnCancelledKeyThrows();
    }

    static void check(boolean cond, String msg) {
        if (!cond) {
            throw new RuntimeException(msg);
        }
    }

    static void testInitialInterestOps() throws IOException {
        try (Selector selector = Selector.open();
             SocketChannel ch = SocketChannel.open()) {
            ch.configureBlocking(false);
            SelectionKey key = ch.register(selector, SelectionKey.OP_READ);
            check(key.interestOps() == SelectionKey.OP_READ,
                  "initial interestOps should be OP_READ but was " + key.interestOps());
        }
    }

    static void testInterestOpsAfterSet() throws IOException {
        try (Selector selector = Selector.open();
             SocketChannel ch = SocketChannel.open()) {
            ch.configureBlocking(false);
            SelectionKey key = ch.register(selector, SelectionKey.OP_READ);
            key.interestOps(SelectionKey.OP_WRITE);
            check(key.interestOps() == SelectionKey.OP_WRITE,
                  "interestOps should be OP_WRITE after set but was " + key.interestOps());
        }
    }

    static void testInterestOpsZero() throws IOException {
        try (Selector selector = Selector.open();
             SocketChannel ch = SocketChannel.open()) {
            ch.configureBlocking(false);
            SelectionKey key = ch.register(selector, SelectionKey.OP_READ);
            key.interestOps(0);
            check(key.interestOps() == 0,
                  "interestOps should be 0 but was " + key.interestOps());
        }
    }

    static void testInterestOpsOnCancelledKeyThrows() throws IOException {
        try (Selector selector = Selector.open();
             SocketChannel ch = SocketChannel.open()) {
            ch.configureBlocking(false);
            SelectionKey key = ch.register(selector, SelectionKey.OP_READ);
            key.cancel();
            boolean thrown = false;
            try {
                key.interestOps();
            } catch (CancelledKeyException e) {
                thrown = true;
            }
            check(thrown, "interestOps() on cancelled key should throw CancelledKeyException");
        }
    }
}
