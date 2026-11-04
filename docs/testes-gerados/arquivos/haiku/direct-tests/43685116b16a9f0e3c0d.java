/*
 * @test
 * @summary Verifies selectNow(Consumer) through the public Selector API: null action rejection, empty-selector behavior, and closed-selector rejection
 * @run main SelectorImplWitupTest
 */

import java.nio.channels.ClosedSelectorException;
import java.nio.channels.Selector;
import java.nio.channels.SelectionKey;
import java.util.function.Consumer;

public class SelectorImplWitupTest {

    public static void main(String[] args) throws Exception {
        testNullActionRejected();
        testEmptySelectorReturnsZeroWithoutInvokingAction();
        testClosedSelectorRejected();
    }

    static void testNullActionRejected() throws Exception {
        try (Selector sel = Selector.open()) {
            try {
                sel.selectNow(null);
                throw new RuntimeException("expected NullPointerException for null action");
            } catch (NullPointerException expected) {
                // expected: Objects.requireNonNull(action)
            }
        }
    }

    static void testEmptySelectorReturnsZeroWithoutInvokingAction() throws Exception {
        try (Selector sel = Selector.open()) {
            final int[] calls = new int[1];
            Consumer<SelectionKey> action = k -> calls[0]++;
            int n = sel.selectNow(action);
            if (n != 0) {
                throw new RuntimeException("expected 0 ready keys but got " + n);
            }
            if (calls[0] != 0) {
                throw new RuntimeException("action must not be invoked, calls=" + calls[0]);
            }
            if (!sel.selectedKeys().isEmpty()) {
                throw new RuntimeException("selectedKeys must be empty");
            }
        }
    }

    static void testClosedSelectorRejected() throws Exception {
        Selector sel = Selector.open();
        sel.close();
        try {
            sel.selectNow(k -> { });
            throw new RuntimeException("expected ClosedSelectorException on closed selector");
        } catch (ClosedSelectorException expected) {
            // expected: ensureOpen() in lockAndDoSelect
        }
    }
}
