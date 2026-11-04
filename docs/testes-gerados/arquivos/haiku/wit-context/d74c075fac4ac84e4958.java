/*
 * @test
 * @summary Verifies negative timeout handling of Selector.select(long) on the public Selector API, which dispatches to SelectorImpl.select
 * @run main SelectorImplWitupTest
 */

import java.io.IOException;
import java.nio.channels.ClosedSelectorException;
import java.nio.channels.Selector;

public class SelectorImplWitupTest {

    public static void main(String[] args) throws IOException {
        testNegativeTimeoutThrowsIllegalArgument();
        testMinusOneTimeoutThrowsIllegalArgument();
        testZeroNegativeBoundaryNotRejected();
        testNegativeTimeoutOnClosedSelectorThrowsIllegalArgument();
        testSelectOnClosedSelectorThrowsClosedSelector();
    }

    static void testNegativeTimeoutThrowsIllegalArgument() throws IOException {
        try (Selector sel = Selector.open()) {
            try {
                sel.select(-5L);
                throw new AssertionError("Expected IllegalArgumentException for timeout -5");
            } catch (IllegalArgumentException expected) {
            }
        }
    }

    static void testMinusOneTimeoutThrowsIllegalArgument() throws IOException {
        try (Selector sel = Selector.open()) {
            try {
                sel.select(-1L);
                throw new AssertionError("Expected IllegalArgumentException for timeout -1");
            } catch (IllegalArgumentException expected) {
            }
        }
    }

    static void testZeroNegativeBoundaryNotRejected() throws IOException {
        try (Selector sel = Selector.open()) {
            int n = sel.select(1L);
            if (n != 0) {
                throw new AssertionError("Expected 0 ready keys with no registrations, got " + n);
            }
        }
    }

    static void testNegativeTimeoutOnClosedSelectorThrowsIllegalArgument() throws IOException {
        Selector sel = Selector.open();
        sel.close();
        try {
            sel.select(-1L);
            throw new AssertionError("Expected IllegalArgumentException on closed selector with negative timeout");
        } catch (IllegalArgumentException expected) {
        }
    }

    static void testSelectOnClosedSelectorThrowsClosedSelector() throws IOException {
        Selector sel = Selector.open();
        sel.close();
        try {
            sel.select(1L);
            throw new AssertionError("Expected ClosedSelectorException on closed selector");
        } catch (ClosedSelectorException expected) {
        }
    }
}
