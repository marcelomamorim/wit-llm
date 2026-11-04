/*
 * @test
 * @summary Test for interestOps method in SelectionKeyImpl, including exception path.
 * @run main SelectionKeyImplWitupTest
 */

import sun.nio.ch.SelectionKeyImpl;
import java.nio.channels.CancelledKeyException;

public class SelectionKeyImplWitupTest {

    public static void main(String[] args) {
        testInterestOpsValid();
        testInterestOpsInvalid();
    }

    private static void testInterestOpsValid() {
        // Setup: create a SelectionKeyImpl instance with valid state
        // Note: Since constructor is package-private and no public factory is provided,
        // we assume a mock or a minimal subclass for testing purposes.
        // For the purpose of this test, we simulate a valid instance by subclassing.
        class TestSelectionKey extends SelectionKeyImpl {
            TestSelectionKey() {
                super(null, null);
            }
            @Override
            public boolean isValid() {
                return true;
            }
        }
        SelectionKeyImpl key = new TestSelectionKey();
        try {
            int currentOps = key.interestOps();
            System.out.println("interestOps (valid): " + currentOps);
        } catch (Exception e) {
            System.err.println("Unexpected exception in valid interestOps: " + e);
        }
    }

    private static void testInterestOpsInvalid() {
        // Setup: create a SelectionKeyImpl instance in invalid state to trigger exception
        class TestSelectionKey extends SelectionKeyImpl {
            TestSelectionKey() {
                super(null, null);
            }
            @Override
            public boolean isValid() {
                return false;
            }
        }
        SelectionKeyImpl key = new TestSelectionKey();
        try {
            key.interestOps(1);
            System.err.println("Expected CancelledKeyException was not thrown");
        } catch (CancelledKeyException e) {
            System.out.println("Caught expected CancelledKeyException");
        } catch (Exception e) {
            System.err.println("Unexpected exception: " + e);
        }
    }
}
