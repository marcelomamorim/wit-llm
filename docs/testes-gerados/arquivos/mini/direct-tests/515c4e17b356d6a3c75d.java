/*
 * @test
 * @summary Test Selector.select(Consumer) method basic behavior
 * @run main SelectorWitupTest
 */

import java.nio.channels.Selector;
import java.nio.channels.SelectionKey;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

public class SelectorWitupTest {
    public static void main(String[] args) throws IOException {
        Selector selector = null;
        try {
            selector = Selector.open();

            // Test select with null Consumer - should throw NullPointerException
            boolean npeThrown = false;
            try {
                selector.select(null);
            } catch (NullPointerException npe) {
                npeThrown = true;
            }
            if (!npeThrown) {
                throw new AssertionError("Expected NullPointerException when passing null Consumer");
            }

            // Test select with a Consumer that counts invocations
            final AtomicInteger count = new AtomicInteger(0);
            Consumer<SelectionKey> consumer = new Consumer<SelectionKey>() {
                public void accept(SelectionKey key) {
                    count.incrementAndGet();
                }
            };

            // Since no channels are registered, select should return 0 and consumer not called
            int selected = selector.select(consumer);
            if (selected != 0) {
                throw new AssertionError("Expected select to return 0 when no channels registered, got " + selected);
            }
            if (count.get() != 0) {
                throw new AssertionError("Consumer should not be called when no keys selected");
            }

        } finally {
            if (selector != null) {
                selector.close();
            }
        }
    }
}
