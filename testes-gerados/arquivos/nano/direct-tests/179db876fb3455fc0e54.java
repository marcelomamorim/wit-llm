/* @test    @summary Test the keys() method of SelectorImpl for correct behavior and exception handling.    @run main SelectorImplWitupTest */

import sun.nio.ch.SelectorImpl;
import java.nio.channels.SelectionKey;
import java.util.Set;

public class SelectorImplWitupTest {

    public static void main(String[] args) {
        testKeysReturnsUnmodifiableSet();
        testKeysThrowsWhenClosed();
    }

    private static void testKeysReturnsUnmodifiableSet() {
        SelectorImpl selector = null;
        try {
            selector = new SelectorImpl(SelectorProvider.provider());
            Set<SelectionKey> keys = selector.keys();
            if (keys == null) {
                throw new AssertionError("keys() returned null");
            }
            // Verify that the returned set is unmodifiable by attempting to add
            try {
                keys.add(null);
                throw new AssertionError("Expected UnsupportedOperationException when adding to keys set");
            } catch (UnsupportedOperationException expected) {
                // Expected exception
            } catch (Exception e) {
                throw new AssertionError("Unexpected exception when adding to keys set", e);
            }
        } finally {
            if (selector != null) {
                try {
                    selector.close();
                } catch (Exception e) {
                    // ignore
                }
            }
        }
    }

    private static void testKeysThrowsWhenClosed() {
        SelectorImpl selector = null;
        try {
            selector = new SelectorImpl(SelectorProvider.provider());
            selector.close();
            try {
                selector.keys();
                throw new AssertionError("Expected ClosedSelectorException when calling keys() on closed selector");
            } catch (java.nio.channels.ClosedSelectorException expected) {
                // Expected exception
            }
        } finally {
            if (selector != null && !selector.isOpen()) {
                try {
                    selector.close();
                } catch (Exception e) {
                    // ignore
                }
            }
        }
    }
}
