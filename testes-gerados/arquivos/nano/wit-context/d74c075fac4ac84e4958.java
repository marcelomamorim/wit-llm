/*
 * @test
 * @summary Tests for sun.nio.ch.SelectorImpl.select(long)
 * @run main SelectorImplWitupTest
 */

import java.io.IOException;
import sun.nio.ch.SelectorImpl;

public class SelectorImplWitupTest {

    public static void main(String[] args) throws Exception {
        testSelectNegativeTimeout();
        testSelectZeroTimeout();
        testSelectPositiveTimeout();
    }

    private static void testSelectNegativeTimeout() {
        SelectorImpl selector = createSelector();
        try {
            selector.select(-1);
            throw new AssertionError("Expected IllegalArgumentException for negative timeout");
        } catch (IllegalArgumentException e) {
            // Expected exception
        } catch (IOException e) {
            throw new AssertionError("Unexpected IOException", e);
        }
    }

    private static void testSelectZeroTimeout() {
        SelectorImpl selector = createSelector();
        try {
            int result = selector.select(0);
            if (result != 0) {
                throw new AssertionError("Expected result 0 for zero timeout, got " + result);
            }
        } catch (IOException e) {
            throw new AssertionError("Unexpected IOException", e);
        }
    }

    private static void testSelectPositiveTimeout() {
        SelectorImpl selector = createSelector();
        try {
            int result = selector.select(100);
            if (result < 0) {
                throw new AssertionError("Result should be non-negative, got " + result);
            }
        } catch (IOException e) {
            throw new AssertionError("Unexpected IOException", e);
        }
    }

    private static SelectorImpl createSelector() {
        // SelectorImpl has a protected constructor, so we need a subclass or reflection.
        // For simplicity, assuming a subclass or a factory method is available.
        // Here, we create an anonymous subclass for testing purposes.
        return new SelectorImpl(SelectorProvider.provider()) {};
    }
}
