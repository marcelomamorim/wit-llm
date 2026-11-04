/*
 * @test
 * @summary Test sun.nio.ch.SelectorImpl.selectNow(Consumer<SelectionKey>) for NPE and normal behavior
 * @run main SelectorImplWitupTest
 */

import sun.nio.ch.SelectorImpl;
import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.spi.SelectorProvider;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;

public class SelectorImplWitupTest {

    // Minimal concrete subclass to instantiate SelectorImpl
    static class MySelectorImpl extends SelectorImpl {
        protected MySelectorImpl(SelectorProvider sp) {
            super(sp);
        }

        @Override
        protected int doSelect(Consumer<SelectionKey> action, long timeout) throws IOException {
            // Simulate no keys selected
            return 0;
        }
    }

    public static void main(String[] args) throws Exception {
        SelectorProvider sp = SelectorProvider.provider();
        MySelectorImpl selector = new MySelectorImpl(sp);

        // Test NPE when action is null
        try {
            selector.selectNow(null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // Expected
        }

        // Test normal behavior with a non-null Consumer
        final AtomicBoolean called = new AtomicBoolean(false);
        Consumer<SelectionKey> consumer = new Consumer<SelectionKey>() {
            public void accept(SelectionKey key) {
                called.set(true);
            }
        };

        int result = selector.selectNow(consumer);

        if (result != 0) {
            throw new RuntimeException("Expected selectNow to return 0 but got " + result);
        }

        if (called.get()) {
            throw new RuntimeException("Consumer.accept should not be called in doSelect stub");
        }
    }
}
