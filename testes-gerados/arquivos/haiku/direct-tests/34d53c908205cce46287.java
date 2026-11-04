/*
 * @test
 * @summary Verifies selectedKeys() behavior of SelectorImpl through the public Selector API
 * @run main SelectorImplWitupTest
 */

import java.nio.channels.ClosedSelectorException;
import java.nio.channels.Selector;
import java.nio.channels.SelectionKey;
import java.util.Set;

public class SelectorImplWitupTest {

    public static void main(String[] args) throws Exception {
        testOpenSelectorReturnsEmptyView();
        testSameViewReturnedOnRepeatedCalls();
        testSelectedKeysViewIsUngrowable();
        testClosedSelectorThrows();
    }

    static void testOpenSelectorReturnsEmptyView() throws Exception {
        try (Selector sel = Selector.open()) {
            Set<SelectionKey> s = sel.selectedKeys();
            if (s == null) {
                throw new RuntimeException("selectedKeys() returned null");
            }
            if (!s.isEmpty()) {
                throw new RuntimeException("expected empty selectedKeys on new selector");
            }
        }
    }

    static void testSameViewReturnedOnRepeatedCalls() throws Exception {
        try (Selector sel = Selector.open()) {
            Set<SelectionKey> a = sel.selectedKeys();
            Set<SelectionKey> b = sel.selectedKeys();
            if (a != b) {
                throw new RuntimeException("selectedKeys() should return the same view");
            }
        }
    }

    static void testSelectedKeysViewIsUngrowable() throws Exception {
        try (Selector sel = Selector.open()) {
            Set<SelectionKey> s = sel.selectedKeys();
            boolean thrown = false;
            try {
                s.add(null);
            } catch (UnsupportedOperationException e) {
                thrown = true;
            }
            if (!thrown) {
                throw new RuntimeException("selectedKeys view should not allow addition");
            }
        }
    }

    static void testClosedSelectorThrows() throws Exception {
        Selector sel = Selector.open();
        sel.close();
        boolean thrown = false;
        try {
            sel.selectedKeys();
        } catch (ClosedSelectorException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("selectedKeys() on closed selector should throw ClosedSelectorException");
        }
    }
}
