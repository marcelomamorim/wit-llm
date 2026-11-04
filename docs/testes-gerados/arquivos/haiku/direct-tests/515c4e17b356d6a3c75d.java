/*
 * @test
 * @summary Verifies Selector.select(Consumer) with no registered keys after wakeup and on a closed selector
 * @run main SelectorWitupTest
 */

import java.io.IOException;
import java.nio.channels.ClosedSelectorException;
import java.nio.channels.Selector;
import java.util.concurrent.atomic.AtomicInteger;

public class SelectorWitupTest {

    public static void main(String[] args) throws IOException {
        testSelectReturnsZeroAfterWakeupWithoutKeys();
        testActionNotInvokedWhenNoKeysSelected();
        testSelectOnClosedSelectorThrows();
    }

    static void testSelectReturnsZeroAfterWakeupWithoutKeys() throws IOException {
        try (Selector sel = Selector.open()) {
            sel.wakeup();
            int n = sel.select(k -> { });
            if (n != 0) {
                throw new RuntimeException("Expected 0 selected keys, got " + n);
            }
        }
    }

    static void testActionNotInvokedWhenNoKeysSelected() throws IOException {
        try (Selector sel = Selector.open()) {
            AtomicInteger calls = new AtomicInteger();
            sel.wakeup();
            int n = sel.select(k -> calls.incrementAndGet());
            if (n != 0 || calls.get() != 0) {
                throw new RuntimeException("Expected no action invocations, n=" + n
                        + ", calls=" + calls.get());
            }
        }
    }

    static void testSelectOnClosedSelectorThrows() throws IOException {
        Selector sel = Selector.open();
        sel.close();
        boolean thrown = false;
        try {
            sel.select(k -> { });
        } catch (ClosedSelectorException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected ClosedSelectorException");
        }
    }
}
