/*
 * @test
 * @summary Verifies the observable behavior of SelectorImpl.keys() through the public Selector API
 * @run main SelectorImplWitupTest
 */

import java.io.IOException;
import java.nio.channels.ClosedSelectorException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.util.Set;

public class SelectorImplWitupTest {

    public static void main(String[] args) throws Exception {
        testKeysEmptyOnNewSelector();
        testKeysIsUnmodifiable();
        testKeysReturnsSameView();
        testKeysContainsRegisteredKey();
        testKeysAfterCloseThrows();
    }

    static void testKeysEmptyOnNewSelector() throws IOException {
        try (Selector sel = Selector.open()) {
            Set<SelectionKey> keys = sel.keys();
            if (keys == null) {
                throw new RuntimeException("keys() returned null");
            }
            if (!keys.isEmpty()) {
                throw new RuntimeException("Expected empty key set, size=" + keys.size());
            }
        }
    }

    static void testKeysIsUnmodifiable() throws IOException {
        try (Selector sel = Selector.open()) {
            Set<SelectionKey> keys = sel.keys();
            boolean threw = false;
            try {
                keys.add(null);
            } catch (UnsupportedOperationException e) {
                threw = true;
            }
            if (!threw) {
                throw new RuntimeException("keys() view must not support addition");
            }
        }
    }

    static void testKeysReturnsSameView() throws IOException {
        try (Selector sel = Selector.open()) {
            Set<SelectionKey> first = sel.keys();
            Set<SelectionKey> second = sel.keys();
            if (first != second) {
                throw new RuntimeException("keys() should return the same public view");
            }
        }
    }

    static void testKeysContainsRegisteredKey() throws IOException {
        try (Selector sel = Selector.open();
             ServerSocketChannel ssc = ServerSocketChannel.open()) {
            ssc.configureBlocking(false);
            SelectionKey key = ssc.register(sel, SelectionKey.OP_ACCEPT);
            Set<SelectionKey> keys = sel.keys();
            if (keys.size() != 1) {
                throw new RuntimeException("Expected 1 key, size=" + keys.size());
            }
            if (!keys.contains(key)) {
                throw new RuntimeException("Registered key missing from keys()");
            }
        }
    }

    static void testKeysAfterCloseThrows() throws IOException {
        Selector sel = Selector.open();
        sel.close();
        boolean threw = false;
        try {
            sel.keys();
        } catch (ClosedSelectorException e) {
            threw = true;
        }
        if (!threw) {
            throw new RuntimeException("keys() on closed selector must throw ClosedSelectorException");
        }
    }
}
