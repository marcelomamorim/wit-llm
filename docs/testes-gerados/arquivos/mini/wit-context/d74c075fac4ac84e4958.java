/*
 * @test
 * @summary Test sun.nio.ch.SelectorImpl.select(long) method behavior including exception for negative timeout
 * @run main SelectorImplWitupTest
 */

import java.io.IOException;
import java.nio.channels.ClosedSelectorException;
import java.nio.channels.IllegalSelectorException;
import java.nio.channels.SelectionKey;
import java.nio.channels.spi.SelectorProvider;
import sun.nio.ch.SelectorImpl;

public class SelectorImplWitupTest {

    // Minimal concrete subclass to instantiate abstract SelectorImpl
    static class MySelectorImpl extends SelectorImpl {
        protected MySelectorImpl(SelectorProvider sp) {
            super(sp);
        }

        @Override
        protected int doSelect(java.util.function.Consumer<SelectionKey> action, long timeout) throws IOException {
            // For testing, simulate select returning 0
            return 0;
        }
    }

    public static void main(String[] args) throws Exception {
        SelectorProvider sp = SelectorProvider.provider();
        SelectorImpl selector = new MySelectorImpl(sp);

        // Test 1: select with zero timeout returns 0
        int resultZeroTimeout = selector.select(0L);
        if (resultZeroTimeout != 0) {
            throw new AssertionError("select(0) should return 0 but returned " + resultZeroTimeout);
        }

        // Test 2: select with positive timeout returns 0 (simulated)
        int resultPositiveTimeout = selector.select(100L);
        if (resultPositiveTimeout != 0) {
            throw new AssertionError("select(100) should return 0 but returned " + resultPositiveTimeout);
        }

        // Test 3: select with negative timeout throws IllegalArgumentException
        boolean threwIAE = false;
        try {
            selector.select(-1L);
        } catch (IllegalArgumentException e) {
            if (!"Negative timeout".equals(e.getMessage())) {
                throw new AssertionError("Expected IllegalArgumentException with message 'Negative timeout' but got: " + e.getMessage());
            }
            threwIAE = true;
        }
        if (!threwIAE) {
            throw new AssertionError("select(-1) did not throw IllegalArgumentException");
        }

        // Test 4: keys() and selectedKeys() return non-null sets and throw ClosedSelectorException if closed
        // keys() and selectedKeys() require selector to be open, so test normal and closed state
        try {
            selector.keys();
            selector.selectedKeys();
        } catch (ClosedSelectorException e) {
            throw new AssertionError("Selector should be open initially");
        }

        selector.close();

        boolean threwClosedSelectorException = false;
        try {
            selector.keys();
        } catch (ClosedSelectorException e) {
            threwClosedSelectorException = true;
        }
        if (!threwClosedSelectorException) {
            throw new AssertionError("keys() did not throw ClosedSelectorException after close");
        }

        threwClosedSelectorException = false;
        try {
            selector.selectedKeys();
        } catch (ClosedSelectorException e) {
            threwClosedSelectorException = true;
        }
        if (!threwClosedSelectorException) {
            throw new AssertionError("selectedKeys() did not throw ClosedSelectorException after close");
        }
    }
}
