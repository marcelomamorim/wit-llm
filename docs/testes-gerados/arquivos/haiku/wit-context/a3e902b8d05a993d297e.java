/*
 * @test
 * @summary Witup-generated checks for the public Selector.select(Consumer) path that dispatches to SelectorImpl.select(Consumer)
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
        testClosedSelectorWithNonNullActionThrowsCSE();
        testWakeupReturnsZeroWithoutInvokingAction();
    }

    static void testNullActionThrowsNPE() throws IOException {
        try (Selector selector = Selector.open()) {
            try {
                selector.select((Consumer<SelectionKey>) null);
                throw new RuntimeException("Expected NullPointerException for null action");
            } catch (NullPointerException expected) {
                // expected
            }
        }
    }

    static void testClosedSelectorWithNonNullActionThrowsCSE() throws IOException {
        Selector selector = Selector.open();
        selector.close();
        try {
            selector.select(k -> { });
            throw new RuntimeException("Expected ClosedSelectorException on closed selector");
        } catch (ClosedSelectorException expected) {
            // expected
        }
    }

    static void testWakeupReturnsZeroWithoutInvokingAction() throws IOException {
        try (Selector selector = Selector.open()) {
            selector.wakeup();
            final int[] calls = {0};
            int n = selector.select(k -> calls[0]++);
            if (n != 0) {
                throw new RuntimeException("Expected 0 selected keys after wakeup, got " + n);
            }
            if (calls[0] != 0) {
                throw new RuntimeException("Action must not be invoked when no keys are ready");
            }
        }
    }
}
