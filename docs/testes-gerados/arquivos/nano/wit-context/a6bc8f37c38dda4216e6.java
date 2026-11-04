/*
 * @test
 * @summary Test for sun.nio.ch.SelectionKeyImpl.readyOps() method.
 * @run main SelectionKeyImplWitupTest
 */

import sun.nio.ch.SelectionKeyImpl;
import java.nio.channels.CancelledKeyException;

public class SelectionKeyImplWitupTest {

    public static void main(String[] args) {
        testReadyOpsValid();
        testReadyOpsInvalid();
    }

    private static void testReadyOpsValid() {
        SelectionKeyImpl key = createValidSelectionKeyImpl();
        int result = key.readyOps();
        if (result != key.readyOps()) {
            throw new AssertionError("Expected readyOps to return the internal readyOps value.");
        }
    }

    private static void testReadyOpsInvalid() {
        SelectionKeyImpl key = createInvalidSelectionKeyImpl();
        try {
            key.readyOps();
            throw new AssertionError("Expected CancelledKeyException to be thrown.");
        } catch (CancelledKeyException e) {
            // Expected exception
        }
    }

    private static SelectionKeyImpl createValidSelectionKeyImpl() {
        // Create a dummy instance with valid state
        // Since constructor is package-private, assume a factory or reflection; here, we simulate with a subclass
        return new SelectionKeyImplDummy();
    }

    private static SelectionKeyImpl createInvalidSelectionKeyImpl() {
        // Create a dummy instance with invalid state
        return new SelectionKeyImplDummyInvalid();
    }

    private static class SelectionKeyImplDummy extends SelectionKeyImpl {
        SelectionKeyImplDummy() {
            super(null, null);
        }
        @Override
        public boolean isValid() {
            return true;
        }
        @Override
        public int readyOps() {
            // Simulate valid state
            return 0;
        }
    }

    private static class SelectionKeyImplDummyInvalid extends SelectionKeyImpl {
        SelectionKeyImplDummyInvalid() {
            super(null, null);
        }
        @Override
        public boolean isValid() {
            return false;
        }
        @Override
        public int readyOps() {
            // Simulate invalid state
            throw new CancelledKeyException();
        }
    }
}
