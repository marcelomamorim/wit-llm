/*
 * @test
 * @summary Verifies public observable behavior of Selector.select(Consumer) for null action and closed selector
 * @run main SelectorWitupTest
 */

import java.nio.channels.ClosedSelectorException;
import java.nio.channels.Selector;
import java.nio.channels.SelectionKey;
import java.util.function.Consumer;

public class SelectorWitupTest {

    public static void main(String[] args) throws Exception {
        testNullActionThrowsNullPointerException();
        testSelectOnClosedSelectorThrowsClosedSelectorException();
    }

    static void testNullActionThrowsNullPointerException() throws Exception {
        Selector selector = Selector.open();
        try {
            selector.select((Consumer<SelectionKey>) null);
            throw new RuntimeException("Expected NullPointerException for null action");
        } catch (NullPointerException expected) {
        } finally {
            selector.close();
        }
    }

    static void testSelectOnClosedSelectorThrowsClosedSelectorException() throws Exception {
        Selector selector = Selector.open();
        selector.close();
        Consumer<SelectionKey> action = k -> { };
        try {
            selector.select(action);
            throw new RuntimeException("Expected ClosedSelectorException on closed selector");
        } catch (ClosedSelectorException expected) {
        }
    }
}
