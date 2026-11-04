/*
 * @test
 * @summary Checks public behavior of keys() on open and closed selectors (SelectorImpl.keys via Selector.open)
 * @run main SelectorImplWitupTest
 */

import java.io.IOException;
import java.nio.channels.ClosedSelectorException;
import java.nio.channels.Selector;
import java.nio.channels.SelectionKey;
import java.util.Set;

public class SelectorImplWitupTest {

    public static void main(String[] args) throws Exception {
        testKeysOnOpenSelectorIsEmptyAndUnmodifiable();
        testKeysOnClosedSelectorThrowsClosedSelectorException();
    }

    private static void testKeysOnOpenSelectorIsEmptyAndUnmodifiable() throws IOException {
        try (Selector sel = Selector.open()) {
            Set<SelectionKey> keys = sel.keys();
            if (keys == null) {
                throw new RuntimeException("keys() returned null");
            }
            if (!keys.isEmpty()) {
                throw new RuntimeException("expected empty key set on new selector");
            }
            try {
                keys.add(null);
                throw new RuntimeException("keys() view must be immutable");
            } catch (UnsupportedOperationException expected) {
                // expected
            }
        }
    }

    private static void testKeysOnClosedSelectorThrowsClosedSelectorException() throws IOException {
        Selector sel = Selector.open();
        sel.close();
        try {
            sel.keys();
            throw new RuntimeException("keys() on closed selector must throw ClosedSelectorException");
        } catch (ClosedSelectorException expected) {
            // expected
        }
    }
}
