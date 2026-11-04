/*
 * @test
 * @summary Verifies observable behavior of SelectorImpl.select() through the public Selector API
 * @run main SelectorImplWitupTest
 */

import java.nio.channels.ClosedSelectorException;
import java.nio.channels.Pipe;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;

public class SelectorImplWitupTest {

    public static void main(String[] args) throws Exception {
        testSelectOnClosedSelectorThrows();
        testWakeupMakesSelectReturnZero();
        testSelectReturnsReadyCount();
        testSelectedKeysContainsReadyKey();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testSelectOnClosedSelectorThrows() throws Exception {
        Selector sel = Selector.open();
        sel.close();
        try {
            sel.select();
            throw new RuntimeException("expected ClosedSelectorException");
        } catch (ClosedSelectorException e) {
            // expected
        }
    }

    static void testWakeupMakesSelectReturnZero() throws Exception {
        Selector sel = Selector.open();
        try {
            sel.wakeup();
            int n = sel.select();
            check(n == 0, "select after wakeup with no keys should return 0, got " + n);
        } finally {
            sel.close();
        }
    }

    static void testSelectReturnsReadyCount() throws Exception {
        Pipe pipe = Pipe.open();
        Pipe.SinkChannel sink = pipe.sink();
        Pipe.SourceChannel source = pipe.source();
        Selector sel = Selector.open();
        try {
            sink.configureBlocking(false);
            sink.register(sel, SelectionKey.OP_WRITE);
            int n = sel.select();
            check(n == 1, "select should report 1 writable key, got " + n);
        } finally {
            sel.close();
            sink.close();
            source.close();
        }
    }

    static void testSelectedKeysContainsReadyKey() throws Exception {
        Pipe pipe = Pipe.open();
        Pipe.SinkChannel sink = pipe.sink();
        Pipe.SourceChannel source = pipe.source();
        Selector sel = Selector.open();
        try {
            sink.configureBlocking(false);
            SelectionKey key = sink.register(sel, SelectionKey.OP_WRITE);
            sel.select();
            check(sel.selectedKeys().size() == 1, "selectedKeys should have size 1");
            check(sel.selectedKeys().contains(key), "selectedKeys should contain the ready key");
        } finally {
            sel.close();
            sink.close();
            source.close();
        }
    }
}
