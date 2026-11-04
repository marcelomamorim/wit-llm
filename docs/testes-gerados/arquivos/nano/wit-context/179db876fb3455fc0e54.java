/*
 * @test
 * @summary Test for SelectorImpl.keys() method, focusing on exception path.
 * @run main SelectorImplWitupTest
 */

import sun.nio.ch.SelectorImpl;
import java.nio.channels.ClosedSelectorException;
import java.nio.channels.SelectionKey;
import java.util.Set;

public class SelectorImplWitupTest {

    public static void main(String[] args) {
        testKeysThrowsWhenClosed();
    }

    private static void testKeysThrowsWhenClosed() {
        SelectorImpl selector = null;
        try {
            // Reflection or internal access is required to close the selector properly,
            // but for simplicity, assume we can simulate closed state by subclassing or mock.
            // Here, we simulate by subclassing and overriding ensureOpen to throw.
            selector = new SelectorImpl(SelectorProvider.provider()) {
                @Override
                protected void ensureOpen() {
                    throw new ClosedSelectorException();
                }
            };
            // Call keys(), expecting ClosedSelectorException
            Set<SelectionKey> keys = selector.keys();
            // If no exception, the test fails
            System.err.println("Test failed: Expected ClosedSelectorException was not thrown.");
        } catch (ClosedSelectorException e) {
            System.out.println("Test passed: ClosedSelectorException was correctly thrown.");
        } catch (Exception e) {
            System.err.println("Test failed: Unexpected exception: " + e);
        }
    }
}
