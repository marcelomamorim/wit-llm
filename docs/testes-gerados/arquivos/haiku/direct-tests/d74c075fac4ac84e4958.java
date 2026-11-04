/*
 * @test
 * @summary Verifies public Selector.select(long) behavior dispatched to SelectorImpl.select for negative, closed, and empty-selector cases
 * @run main SelectorImplWitupTest
 */

import java.nio.channels.ClosedSelectorException;
import java.nio.channels.Selector;

public class SelectorImplWitupTest {

    public static void main(String[] args) throws Exception {
        testNegativeTimeoutThrows();
        testNegativeMinValueThrows();
        testSelectWithNoKeysReturnsZero();
        testSelectOnClosedSelectorThrows();
    }

    static void testNegativeTimeoutThrows() throws Exception {
        try (Selector s = Selector.open()) {
            try {
                s.select(-1L);
                throw new RuntimeException("expected IllegalArgumentException for timeout -1");
            } catch (IllegalArgumentException expected) {
                // expected
            }
        }
    }

    static void testNegativeMinValueThrows() throws Exception {
        try (Selector s = Selector.open()) {
            try {
                s.select(Long.MIN_VALUE);
                throw new RuntimeException("expected IllegalArgumentException for Long.MIN_VALUE");
            } catch (IllegalArgumentException expected) {
                // expected
            }
        }
    }

    static void testSelectWithNoKeysReturnsZero() throws Exception {
        try (Selector s = Selector.open()) {
            int n = s.select(1L);
            if (n != 0) {
                throw new RuntimeException("expected 0 ready keys, got " + n);
            }
            if (!s.selectedKeys().isEmpty()) {
                throw new RuntimeException("expected empty selectedKeys");
            }
        }
    }

    static void testSelectOnClosedSelectorThrows() throws Exception {
        Selector s = Selector.open();
        s.close();
        try {
            s.select(1L);
            throw new RuntimeException("expected ClosedSelectorException");
        } catch (ClosedSelectorException expected) {
            // expected
        }
    }
}
