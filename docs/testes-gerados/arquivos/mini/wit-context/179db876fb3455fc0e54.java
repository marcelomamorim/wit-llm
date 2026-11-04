/*
 * @test
 * @summary Test sun.nio.ch.SelectorImpl.keys() method behavior including ClosedSelectorException when closed
 * @run main SelectorImplWitupTest
 */

import java.nio.channels.ClosedSelectorException;
import java.nio.channels.SelectionKey;
import java.nio.channels.spi.SelectorProvider;
import java.util.Set;

public class SelectorImplWitupTest {

    // Minimal concrete subclass to instantiate abstract SelectorImpl
    static class MySelectorImpl extends sun.nio.ch.SelectorImpl {
        protected MySelectorImpl(SelectorProvider sp) {
            super(sp);
        }

        @Override
        protected int doSelect(java.util.function.Consumer<SelectionKey> action, long timeout) {
            // Dummy implementation for abstract method
            return 0;
        }
    }

    public static void main(String[] args) throws Exception {
        SelectorProvider sp = SelectorProvider.provider();
        MySelectorImpl selector = new MySelectorImpl(sp);

        // Test keys() returns the publicKeys set and is initially empty
        Set<SelectionKey> keys = selector.keys();
        if (keys == null) {
            throw new AssertionError("keys() returned null");
        }

        // The returned set should be unmodifiable (immutable) for additions
        try {
            keys.add(null);
            throw new AssertionError("keys() set should be unmodifiable for add");
        } catch (UnsupportedOperationException expected) {
            // expected
        }

        // Close the selector and verify keys() throws ClosedSelectorException
        selector.close();
        try {
            selector.keys();
            throw new AssertionError("keys() should throw ClosedSelectorException when selector is closed");
        } catch (ClosedSelectorException expected) {
            // expected
        }
    }
}
