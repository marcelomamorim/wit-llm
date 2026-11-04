/*
 * @test
 * @summary Verify SelectorImpl.selectedKeys() returns a stable set while open and throws ClosedSelectorException once closed
 * @run main SelectorImplWitupTest
 */

import java.nio.channels.ClosedSelectorException;
import java.nio.channels.Selector;
import java.nio.channels.SelectionKey;
import java.util.Set;

public class SelectorImplWitupTest {

    public static void main(String[] args) throws Exception {
        Selector selector = Selector.open();
        try {
            Set<SelectionKey> first = selector.selectedKeys();
            check(first != null, "selectedKeys() returned null");
            check(first.isEmpty(), "selectedKeys() should be empty before any select");

            Set<SelectionKey> second = selector.selectedKeys();
            check(first == second, "selectedKeys() should return the same view on repeated calls");
        } finally {
            selector.close();
        }

        boolean thrown = false;
        try {
            selector.selectedKeys();
        } catch (ClosedSelectorException e) {
            thrown = true;
        }
        check(thrown, "selectedKeys() on a closed selector must throw ClosedSelectorException");
    }

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
