/*
 * @test
 * @summary Test interestOps() method of SelectionKeyImpl
 * @run main SelectionKeyImplWitupTest
 */

import sun.nio.ch.SelectionKeyImpl;
import sun.nio.ch.SelChImpl;
import sun.nio.ch.SelectorImpl;
import java.nio.channels.CancelledKeyException;
import java.nio.channels.spi.AbstractSelectableChannel;
import java.nio.channels.spi.AbstractSelector;

public class SelectionKeyImplWitupTest {

    // Minimal stub for SelChImpl to allow construction
    static class StubSelChImpl extends AbstractSelectableChannel implements SelChImpl {
        protected StubSelChImpl() {
            super(null);
        }
        @Override
        public int validOps() {
            return SelectionKeyImpl.OP_READ | SelectionKeyImpl.OP_WRITE;
        }
        @Override
        public int getFDVal() {
            return 42;
        }
        @Override
        protected void implCloseChannel() {
        }
    }

    // Minimal stub for SelectorImpl to allow construction
    static class StubSelectorImpl extends AbstractSelector implements SelectorImpl {
        protected StubSelectorImpl() {
            super(null);
        }
        @Override
        protected void implCloseSelector() {
        }
        @Override
        public void setEventOps(SelectionKeyImpl key) {
            // no-op for test
        }
    }

    public static void main(String[] args) throws Exception {
        StubSelChImpl ch = new StubSelChImpl();
        StubSelectorImpl sel = new StubSelectorImpl();
        SelectionKeyImpl key = new SelectionKeyImpl(ch, sel);

        // Initially, interestOps is 0
        int initial = key.interestOps();
        if (initial != 0) {
            throw new RuntimeException("Expected initial interestOps to be 0 but was " + initial);
        }

        // Set interestOps to a valid ops and verify
        int validOps = ch.validOps();
        key.interestOps(validOps);
        int afterSet = key.interestOps();
        if (afterSet != validOps) {
            throw new RuntimeException("Expected interestOps to be " + validOps + " but was " + afterSet);
        }

        // Test that interestOps() throws CancelledKeyException if key is invalid
        // We simulate invalid by subclassing and overriding isValid to false
        SelectionKeyImpl invalidKey = new SelectionKeyImpl(ch, sel) {
            @Override
            public boolean isValid() {
                return false;
            }
        };
        boolean threw = false;
        try {
            invalidKey.interestOps();
        } catch (CancelledKeyException e) {
            threw = true;
        }
        if (!threw) {
            throw new RuntimeException("Expected CancelledKeyException for invalid key");
        }
    }
}
