/*
 * @test
 * @summary Deterministic jtreg tests for sun.nio.ch.SelectorImpl.select()
 * @run main SelectorImplWitupTest
 */

import sun.nio.ch.SelectorImpl;
import java.nio.channels.spi.SelectorProvider;
import java.nio.channels.ClosedSelectorException;
import java.io.IOException;

public class SelectorImplWitupTest {

    // Minimal concrete subclass to instantiate abstract SelectorImpl
    static class MySelectorImpl extends SelectorImpl {
        protected MySelectorImpl(SelectorProvider sp) {
            super(sp);
        }

        @Override
        protected int doSelect(java.util.function.Consumer<java.nio.channels.SelectionKey> action, long timeout) throws IOException {
            // For testing, simulate no keys selected and return 0
            return 0;
        }
    }

    public static void main(String[] args) throws Exception {
        SelectorProvider sp = SelectorProvider.provider();
        MySelectorImpl selector = new MySelectorImpl(sp);

        // Test 1: select() returns 0 (no keys selected) without exception
        int selected = selector.select();
        if (selected != 0) {
            throw new AssertionError("Expected select() to return 0 but got " + selected);
        }

        // Test 2: keys() and selectedKeys() return non-null sets
        if (selector.keys() == null) {
            throw new AssertionError("keys() returned null");
        }
        if (selector.selectedKeys() == null) {
            throw new AssertionError("selectedKeys() returned null");
        }

        // Test 3: ClosedSelectorException thrown if selector is closed
        selector.close();
        boolean closedSelectorExceptionThrown = false;
        try {
            selector.select();
        } catch (ClosedSelectorException e) {
            closedSelectorExceptionThrown = true;
        }
        if (!closedSelectorExceptionThrown) {
            throw new AssertionError("Expected ClosedSelectorException when selecting on closed selector");
        }
    }
}
