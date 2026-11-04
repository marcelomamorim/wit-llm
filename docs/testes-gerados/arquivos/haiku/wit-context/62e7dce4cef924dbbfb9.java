/*
 * @test
 * @summary Public Selector.select() must throw ClosedSelectorException after close and return without selecting keys after wakeup
 * @run main SelectorImplWitupTest
 */

import java.io.IOException;
import java.nio.channels.ClosedSelectorException;
import java.nio.channels.Selector;

public class SelectorImplWitupTest {

    public static void main(String[] args) throws IOException {
        testSelectOnClosedSelectorThrows();
        testSelectAfterWakeupReturnsZeroAndNoSelectedKeys();
        System.out.println("PASSED");
    }

    private static void testSelectOnClosedSelectorThrows() throws IOException {
        Selector sel = Selector.open();
        sel.close();
        try {
            sel.select();
            throw new RuntimeException("select() on closed selector did not throw ClosedSelectorException");
        } catch (ClosedSelectorException expected) {
            // expected
        }
    }

    private static void testSelectAfterWakeupReturnsZeroAndNoSelectedKeys() throws IOException {
        try (Selector sel = Selector.open()) {
            sel.wakeup();
            int n = sel.select();
            if (n != 0) {
                throw new RuntimeException("expected 0 selected keys but got " + n);
            }
            if (!sel.selectedKeys().isEmpty()) {
                throw new RuntimeException("selectedKeys should be empty");
            }
            if (!sel.keys().isEmpty()) {
                throw new RuntimeException("keys should be empty for a new selector");
            }
        }
    }
}
