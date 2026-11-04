/*
 * @test
 * @summary Test sun.nio.ch.SelectorImpl.selectedKeys() method
 * @run main SelectorImplWitupTest
 */

import sun.nio.ch.SelectorImpl;
import java.nio.channels.spi.SelectorProvider;
import java.nio.channels.SelectionKey;
import java.nio.channels.ClosedSelectorException;
import java.util.Set;

public class SelectorImplWitupTest {

    // Minimal concrete subclass to test SelectorImpl
    static class MySelectorImpl extends SelectorImpl {
        protected MySelectorImpl(SelectorProvider sp) {
            super(sp);
        }

        @Override
        protected int doSelect(java.util.function.Consumer<SelectionKey> action, long timeout) {
            // no-op for test
            return 0;
        }
    }

    public static void main(String[] args) throws Exception {
        SelectorProvider sp = SelectorProvider.provider();
        MySelectorImpl selector = new MySelectorImpl(sp);

        // Test selectedKeys() returns a non-null Set
        Set<SelectionKey> selected = selector.selectedKeys();
        if (selected == null) {
            throw new AssertionError("selectedKeys() returned null");
        }

        // The returned set should be unmodifiable for addition
        boolean addFailed = false;
        try {
            selected.add(null);
        } catch (UnsupportedOperationException e) {
            addFailed = true;
        }
        if (!addFailed) {
            throw new AssertionError("selectedKeys() set should not allow add");
        }

        // The returned set should allow removal (remove returns false for null)
        boolean removeFailed = false;
        try {
            selected.remove(null); // should not throw
        } catch (UnsupportedOperationException e) {
            removeFailed = true;
        }
        if (removeFailed) {
            throw new AssertionError("selectedKeys() set should allow remove");
        }

        // Close selector and verify selectedKeys() throws ClosedSelectorException
        selector.close();
        boolean closedExceptionThrown = false;
        try {
            selector.selectedKeys();
        } catch (ClosedSelectorException e) {
            closedExceptionThrown = true;
        }
        if (!closedExceptionThrown) {
            throw new AssertionError("selectedKeys() should throw ClosedSelectorException when closed");
        }
    }
}
