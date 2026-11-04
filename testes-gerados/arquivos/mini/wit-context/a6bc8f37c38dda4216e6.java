/*
 * @test
 * @summary Test sun.nio.ch.SelectionKeyImpl.readyOps() method behavior including CancelledKeyException
 * @run main SelectionKeyImplWitupTest
 */

import sun.nio.ch.SelectionKeyImpl;
import sun.nio.ch.SelChImpl;
import sun.nio.ch.SelectorImpl;
import java.nio.channels.CancelledKeyException;

public class SelectionKeyImplWitupTest {

    static class DummySelChImpl extends SelChImpl {
        private boolean valid = true;
        DummySelChImpl() throws Exception {
            super(null, false);
        }
        @Override
        public boolean isOpen() {
            return valid;
        }
        @Override
        public int validOps() {
            return 0;
        }
        @Override
        public int getFDVal() {
            return 0;
        }
        void setValid(boolean v) {
            valid = v;
        }
    }

    static class DummySelectorImpl extends SelectorImpl {
        DummySelectorImpl() throws Exception {
            super(null);
        }
    }

    public static void main(String[] args) throws Exception {
        DummySelChImpl ch = new DummySelChImpl();
        DummySelectorImpl sel = new DummySelectorImpl();
        SelectionKeyImpl key = new SelectionKeyImpl(ch, sel);

        // Initially valid, readyOps should return 0 (default int value)
        int ro = key.readyOps();
        if (ro != 0) {
            throw new RuntimeException("Expected readyOps() to return 0 initially, got " + ro);
        }

        // Invalidate the channel to simulate cancelled key
        ch.setValid(false);

        boolean threw = false;
        try {
            key.readyOps();
        } catch (CancelledKeyException e) {
            threw = true;
        }
        if (!threw) {
            throw new RuntimeException("Expected CancelledKeyException when calling readyOps() on invalid key");
        }

        System.out.println("Test passed");
    }
}
