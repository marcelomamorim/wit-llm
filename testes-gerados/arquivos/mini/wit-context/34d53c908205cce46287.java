/*
 * @test
 * @summary Test sun.nio.ch.SelectorImpl.selectedKeys() for ClosedSelectorException and normal behavior
 * @run main SelectorImplWitupTest
 */

import java.nio.channels.ClosedSelectorException;
import java.nio.channels.SelectionKey;
import java.nio.channels.spi.SelectorProvider;
import java.util.Set;

public class SelectorImplWitupTest extends sun.nio.ch.SelectorImpl {

    // Constructor forwarding to protected super
    protected SelectorImplWitupTest(SelectorProvider sp) {
        super(sp);
    }

    // Implement abstract method with minimal stub
    @Override
    protected int doSelect(java.util.function.Consumer<SelectionKey> action, long timeout) {
        // No-op implementation for testing
        return 0;
    }

    public static void main(String[] args) throws Exception {
        SelectorImplWitupTest selector = new SelectorImplWitupTest(SelectorProvider.provider());

        // Test normal behavior: selectedKeys() returns a non-null Set
        Set<SelectionKey> selected = selector.selectedKeys();
        if (selected == null) {
            throw new AssertionError("selectedKeys() returned null");
        }

        // Test that selectedKeys() returns the unmodifiable set (removal allowed, addition not allowed)
        try {
            selected.add(null);
            throw new AssertionError("selectedKeys() allowed addition, but should not");
        } catch (UnsupportedOperationException expected) {
            // expected
        }

        // Test ClosedSelectorException thrown if selector is closed
        selector.close();
        boolean closedSelectorExceptionThrown = false;
        try {
            selector.selectedKeys();
        } catch (ClosedSelectorException e) {
            closedSelectorExceptionThrown = true;
        }
        if (!closedSelectorExceptionThrown) {
            throw new AssertionError("selectedKeys() did not throw ClosedSelectorException after close");
        }

        System.out.println("Test passed");
    }
}
