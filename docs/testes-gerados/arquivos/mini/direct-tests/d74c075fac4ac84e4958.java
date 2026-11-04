/*
 * @test
 * @summary Test sun.nio.ch.SelectorImpl.select(long) method behavior
 * @run main SelectorImplWitupTest
 */

import sun.nio.ch.SelectorImpl;
import java.io.IOException;
import java.nio.channels.ClosedSelectorException;
import java.nio.channels.spi.SelectorProvider;

public class SelectorImplWitupTest {

    // Minimal concrete subclass to test abstract SelectorImpl
    static class TestSelectorImpl extends SelectorImpl {
        protected TestSelectorImpl(SelectorProvider sp) {
            super(sp);
        }

        @Override
        protected int doSelect(java.util.function.Consumer<java.nio.channels.SelectionKey> action, long timeout) throws IOException {
            // For testing, simulate no keys selected and no blocking
            return 0;
        }
    }

    public static void main(String[] args) throws Exception {
        SelectorProvider sp = SelectorProvider.provider();
        TestSelectorImpl selector = new TestSelectorImpl(sp);

        // Test 1: select with zero timeout returns 0
        int result = selector.select(0L);
        if (result != 0) {
            throw new AssertionError("Expected select(0) to return 0, got " + result);
        }

        // Test 2: select with positive timeout returns 0 (simulated)
        result = selector.select(100L);
        if (result != 0) {
            throw new AssertionError("Expected select(100) to return 0, got " + result);
        }

        // Test 3: select with negative timeout throws IllegalArgumentException
        boolean threw = false;
        try {
            selector.select(-1L);
        } catch (IllegalArgumentException e) {
            threw = true;
        }
        if (!threw) {
            throw new AssertionError("Expected select(-1) to throw IllegalArgumentException");
        }

        // Test 4: select on closed selector throws ClosedSelectorException
        selector.close();
        threw = false;
        try {
            selector.select(10L);
        } catch (ClosedSelectorException e) {
            threw = true;
        }
        if (!threw) {
            throw new AssertionError("Expected select on closed selector to throw ClosedSelectorException");
        }
    }
}
