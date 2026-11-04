/*
 * @test
 * @summary Checks public Selector.select(Consumer) behavior that is implemented by SelectorImpl.
 * @run main SelectorImplWitupTest
 */

import java.io.IOException;
import java.nio.channels.ClosedSelectorException;
import java.nio.channels.Selector;
import java.nio.channels.SelectionKey;
import java.util.function.Consumer;

public class SelectorImplWitupTest {

    public static void main(String[] args) throws Exception {
        testNullActionThrowsNPE();
        testNullActionOnClosedSelectorThrowsNPE();
        testClosedSelectorThrows();
        testWakeupReturnsZeroWithoutInvokingAction();
    }

    static void testNullActionThrowsNPE() throws IOException {
        try (Selector s = Selector.open()) {
            try {
                s.select((Consumer<SelectionKey>) null);
                throw new RuntimeException("expected NullPointerException");
            } catch (NullPointerException expected) {
                // expected
            }
        }
    }

    static void testNullActionOnClosedSelectorThrowsNPE() throws IOException {
        Selector s = Selector.open();
        s.close();
        try {
            s.select((Consumer<SelectionKey>) null);
            throw new RuntimeException("expected NullPointerException");
        } catch (NullPointerException expected) {
            // requireNonNull runs before ensureOpen
        }
    }

    static void testClosedSelectorThrows() throws IOException {
        Selector s = Selector.open();
        s.close();
        try {
            s.select(k -> { });
            throw new RuntimeException("expected ClosedSelectorException");
        } catch (ClosedSelectorException expected) {
            // expected
        }
    }

    static void testWakeupReturnsZeroWithoutInvokingAction() throws IOException {
        try (Selector s = Selector.open()) {
            int[] calls = {0};
            s.wakeup();
            int n = s.select(k -> calls[0]++);
            if (n != 0) {
                throw new RuntimeException("expected 0 updated keys but got " + n);
            }
            if (calls[0] != 0) {
                throw new RuntimeException("action must not be invoked but was called " + calls[0] + " times");
            }
        }
    }
}
