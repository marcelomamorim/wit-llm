/*
 * @test
 * @summary Deterministic tests for java.nio.channels.Selector.select(Consumer<SelectionKey>)
 * @run main SelectorWitupTest
 */

import java.io.IOException;
import java.nio.channels.Selector;
import java.nio.channels.SelectionKey;
import java.util.Iterator;
import java.util.Set;
import java.util.function.Consumer;

public class SelectorWitupTest {
    public static void main(String[] args) throws IOException {
        testSelectWithNullConsumer();
        testSelectReturnsNonNegative();
    }

    private static void testSelectWithNullConsumer() throws IOException {
        Selector selector = null;
        try {
            selector = Selector.open();
            // Passing null as Consumer should not throw NullPointerException explicitly here,
            // but behavior depends on implementation; test for observable behavior.
            // According to Selector API, select(Consumer) with null may throw NPE or behave differently.
            // We test that it throws NullPointerException to catch regressions.
            try {
                selector.select(null);
                throw new AssertionError("Expected NullPointerException when passing null Consumer");
            } catch (NullPointerException expected) {
                // Expected path
            }
        } finally {
            if (selector != null) {
                selector.close();
            }
        }
    }

    private static void testSelectReturnsNonNegative() throws IOException {
        Selector selector = null;
        try {
            selector = Selector.open();
            // The select(Consumer) method returns int >= 0
            int selected = selector.select(new Consumer<SelectionKey>() {
                @Override
                public void accept(SelectionKey key) {
                    // no-op
                }
            });
            if (selected < 0) {
                throw new AssertionError("select returned negative value: " + selected);
            }
        } finally {
            if (selector != null) {
                selector.close();
            }
        }
    }
}
