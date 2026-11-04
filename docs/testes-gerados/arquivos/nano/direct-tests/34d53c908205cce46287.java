/*
 * @test
 * @summary Test the selectedKeys() method of SelectorImpl for correct behavior.
 * @run main SelectorImplWitupTest
 */

import sun.nio.ch.SelectorImpl;
import java.nio.channels.SelectionKey;
import java.util.Set;

public class SelectorImplWitupTest {

    public static void main(String[] args) {
        testSelectedKeysReturnsOpenSelector();
        testSelectedKeysThrowsWhenClosed();
    }

    static void testSelectedKeysReturnsOpenSelector() {
        SelectorImpl selector = new SelectorImpl(null);
        Set<SelectionKey> keys = selector.selectedKeys();
        if (keys == null) {
            throw new AssertionError("selectedKeys() returned null");
        }
        if (!keys.isEmpty()) {
            throw new AssertionError("selectedKeys() should be empty initially");
        }
        if (!selector.isOpen()) {
            throw new AssertionError("Selector should be open");
        }
    }

    static void testSelectedKeysThrowsWhenClosed() {
        SelectorImpl selector = new SelectorImpl(null);
        try {
            // Close the selector to simulate closed state
            // Since no public close method is shown, simulate by reflection or assume isOpen() returns false
            // For this test, forcibly set a flag or assume isOpen() returns false (not shown in code)
            // As we cannot access internal state, simulate by subclassing or assume isOpen() returns false
            // For simplicity, assume isOpen() returns false after a hypothetical close method
            // Since no such method is provided, this test is conceptual; in real code, you'd call close()
            // but it's not shown in the provided code. So, we simulate by subclassing.
            selector = new SelectorImpl(null) {
                @Override
                public boolean isOpen() {
                    return false;
                }
            };
            selector.selectedKeys();
            throw new AssertionError("Expected ClosedSelectorException was not thrown");
        } catch (java.nio.channels.ClosedSelectorException e) {
            // Expected exception
        }
    }
}
