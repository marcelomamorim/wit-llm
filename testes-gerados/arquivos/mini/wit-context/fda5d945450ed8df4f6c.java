/*
 * @test
 * @summary Test sun.nio.ch.SelectionKeyImpl.interestOps() method behavior including CancelledKeyException
 * @run main SelectionKeyImplWitupTest
 */

import java.nio.channels.CancelledKeyException;
import sun.nio.ch.SelectionKeyImpl;
import sun.nio.ch.SelChImpl;
import sun.nio.ch.SelectorImpl;
import java.nio.channels.spi.AbstractSelectionKey;

public class SelectionKeyImplWitupTest {

    // Minimal stub classes to allow construction
    static class DummySelChImpl extends SelChImpl {
        private boolean valid = true;
        DummySelChImpl() {
            super(null, null, 0);
        }
        @Override
        public int validOps() {
            return 1 | 4 | 8; // example ops
        }
        @Override
        public int getFDVal() {
            return 42;
        }
        void setValid(boolean v) {
            valid = v;
        }
        @Override
        public boolean isOpen() {
            return valid;
        }
    }

    static class DummySelectorImpl extends SelectorImpl {
        DummySelectorImpl() {
            super(null);
        }
        @Override
        void setEventOps(SelectionKeyImpl key) {
            // no-op for test
        }
    }

    public static void main(String[] args) throws Exception {
        testInterestOpsValidKey();
        testInterestOpsCancelledKey();
    }

    private static void testInterestOpsValidKey() {
        DummySelChImpl ch = new DummySelChImpl();
        DummySelectorImpl sel = new DummySelectorImpl();
        SelectionKeyImpl key = new SelectionKeyImpl(ch, sel);

        // Initially interestOps is 0
        int initial = key.interestOps();
        if (initial != 0) {
            throw new AssertionError("Expected initial interestOps to be 0 but was " + initial);
        }

        // Set interestOps to a valid ops value
        int validOps = ch.validOps();
        key.interestOps(validOps);
        int afterSet = key.interestOps();
        if (afterSet != validOps) {
            throw new AssertionError("interestOps() after set did not match expected value");
        }
    }

    private static void testInterestOpsCancelledKey() {
        DummySelChImpl ch = new DummySelChImpl();
        DummySelectorImpl sel = new DummySelectorImpl();
        SelectionKeyImpl key = new SelectionKeyImpl(ch, sel);

        // Simulate cancelled key by overriding isValid to false
        SelectionKeyImpl cancelledKey = new SelectionKeyImpl(ch, sel) {
            @Override
            public boolean isValid() {
                return false;
            }
        };

        boolean thrown = false;
        try {
            cancelledKey.interestOps();
        } catch (CancelledKeyException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("CancelledKeyException was not thrown for invalid key");
        }
    }
}
