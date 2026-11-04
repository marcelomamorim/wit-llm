/*
 * @test
 * @summary Test sun.nio.ch.SelectorImpl.keys() method behavior
 * @run main SelectorImplWitupTest
 */

import java.nio.channels.spi.SelectorProvider;
import java.nio.channels.SelectionKey;
import java.util.Set;
import sun.nio.ch.SelectorImpl;
import java.nio.channels.ClosedSelectorException;

public class SelectorImplWitupTest {

    // Minimal concrete subclass to instantiate abstract SelectorImpl
    static class MySelectorImpl extends SelectorImpl {
        protected MySelectorImpl(SelectorProvider sp) {
            super(sp);
        }

        @Override
        protected int doSelect(java.util.function.Consumer<SelectionKey> action, long timeout) {
            return 0; // no-op
        }
    }

    public static void main(String[] args) throws Exception {
        SelectorProvider sp = SelectorProvider.provider();
        MySelectorImpl selector = new MySelectorImpl(sp);

        // Test keys() returns a non-null unmodifiable set initially empty
        Set<SelectionKey> keys = selector.keys();
        if (keys == null) {
            throw new AssertionError("keys() returned null");
        }
        if (!keys.isEmpty()) {
            throw new AssertionError("keys() should be empty initially");
        }

        // keys() returns the same set on multiple calls
        Set<SelectionKey> keys2 = selector.keys();
        if (keys != keys2) {
            throw new AssertionError("keys() should return the same set instance");
        }

        // keys() throws ClosedSelectorException if selector is closed
        selector.close();
        boolean closedExceptionThrown = false;
        try {
            selector.keys();
        } catch (ClosedSelectorException e) {
            closedExceptionThrown = true;
        }
        if (!closedExceptionThrown) {
            throw new AssertionError("keys() did not throw ClosedSelectorException after close");
        }
    }
}
