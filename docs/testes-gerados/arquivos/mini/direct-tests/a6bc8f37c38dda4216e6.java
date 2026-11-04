/*
 * @test
 * @summary Test sun.nio.ch.SelectionKeyImpl.readyOps() method behavior
 * @run main SelectionKeyImplWitupTest
 */

import sun.nio.ch.SelectionKeyImpl;
import sun.nio.ch.SelChImpl;
import sun.nio.ch.SelectorImpl;
import java.nio.channels.CancelledKeyException;
import java.nio.channels.spi.AbstractSelectableChannel;
import java.nio.channels.spi.AbstractSelector;
import java.nio.channels.SelectionKey;

public class SelectionKeyImplWitupTest {

    // Minimal stub for SelChImpl to support SelectionKeyImpl
    static class StubSelChImpl extends AbstractSelectableChannel implements SelChImpl {
        protected StubSelChImpl() {
            super(null);
        }

        @Override
        public int validOps() {
            return SelectionKey.OP_READ | SelectionKey.OP_WRITE;
        }

        @Override
        public int getFDVal() {
            return 42; // arbitrary
        }

        @Override
        public boolean isRegistered() {
            return true;
        }

        @Override
        public boolean isBlocking() {
            return false;
        }

        @Override
        protected void implCloseChannel() {
            // no-op
        }
    }

    // Minimal stub for SelectorImpl to support SelectionKeyImpl
    static class StubSelectorImpl extends AbstractSelector implements SelectorImpl {

        boolean eventOpsSetCalled = false;

        protected StubSelectorImpl() {
            super(null);
        }

        @Override
        public void setEventOps(SelectionKeyImpl key) {
            eventOpsSetCalled = true;
        }

        @Override
        protected void implCloseSelector() {
            // no-op
        }

        @Override
        public SelectionKey register(AbstractSelectableChannel ch, int ops, Object att) {
            throw new UnsupportedOperationException();
        }
    }

    public static void main(String[] args) throws Exception {
        testReadyOpsReturnsInitialValue();
        testReadyOpsThrowsIfInvalid();
    }

    private static void testReadyOpsReturnsInitialValue() {
        StubSelChImpl ch = new StubSelChImpl();
        StubSelectorImpl sel = new StubSelectorImpl();
        SelectionKeyImpl key = new SelectionKeyImpl(ch, sel);

        // Initially readyOps is 0
        int ro = key.readyOps();
        if (ro != 0) {
            throw new AssertionError("Expected initial readyOps to be 0 but was " + ro);
        }

        // Set readyOps via reflection since no setter is public (not allowed by instructions)
        // Instead, use reflection only if absolutely necessary
        // Here, we cannot set readyOps directly, so test only initial value
    }

    private static void testReadyOpsThrowsIfInvalid() {
        StubSelChImpl ch = new StubSelChImpl();
        StubSelectorImpl sel = new StubSelectorImpl();
        SelectionKeyImpl key = new SelectionKeyImpl(ch, sel);

        // Invalidate key by overriding isValid to return false via subclass
        SelectionKeyImpl invalidKey = new SelectionKeyImpl(ch, sel) {
            @Override
            public boolean isValid() {
                return false;
            }
        };

        try {
            invalidKey.readyOps();
            throw new AssertionError("Expected CancelledKeyException for invalid key");
        } catch (CancelledKeyException e) {
            // expected
        }
    }
}
