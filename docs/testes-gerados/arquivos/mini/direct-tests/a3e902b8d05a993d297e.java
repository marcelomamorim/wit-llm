/*
 * @test
 * @summary Test sun.nio.ch.SelectorImpl.select(Consumer) method behavior
 * @run main SelectorImplWitupTest
 */

import sun.nio.ch.SelectorImpl;
import java.io.IOException;
import java.nio.channels.ClosedSelectorException;
import java.nio.channels.spi.SelectorProvider;
import java.nio.channels.SelectionKey;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;

public class SelectorImplWitupTest {

    // Minimal concrete subclass to test abstract SelectorImpl
    static class TestSelectorImpl extends SelectorImpl {
        boolean doSelectCalled = false;
        Consumer<SelectionKey> lastAction = null;
        long lastTimeout = 0L;

        protected TestSelectorImpl(SelectorProvider sp) {
            super(sp);
        }

        @Override
        protected int doSelect(Consumer<SelectionKey> action, long timeout) throws IOException {
            doSelectCalled = true;
            lastAction = action;
            lastTimeout = timeout;
            // Simulate no keys selected
            return 0;
        }

        // Expose protected method for testing
        int invokeSelect(Consumer<SelectionKey> action) throws IOException {
            return select(action);
        }
    }

    public static void main(String[] args) throws Exception {
        TestSelectorImpl selector = new TestSelectorImpl(SelectorProvider.provider());

        // Test 1: Passing null action should throw NullPointerException
        try {
            selector.invokeSelect(null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException npe) {
            // Expected
        }

        // Test 2: Passing a valid Consumer should call doSelect with timeout -1
        final AtomicBoolean consumerCalled = new AtomicBoolean(false);
        Consumer<SelectionKey> consumer = new Consumer<SelectionKey>() {
            public void accept(SelectionKey key) {
                consumerCalled.set(true);
            }
        };

        int result = selector.invokeSelect(consumer);

        if (!selector.doSelectCalled) {
            throw new RuntimeException("doSelect was not called");
        }

        if (selector.lastAction != consumer) {
            throw new RuntimeException("doSelect was called with wrong Consumer");
        }

        if (selector.lastTimeout != -1L) {
            throw new RuntimeException("doSelect was called with wrong timeout");
        }

        if (result != 0) {
            throw new RuntimeException("select returned unexpected result");
        }

        // Test 3: Closed selector throws ClosedSelectorException
        selector.close();
        try {
            selector.invokeSelect(consumer);
            throw new RuntimeException("Expected ClosedSelectorException not thrown");
        } catch (ClosedSelectorException cse) {
            // Expected
        }

        System.out.println("All tests passed.");
    }
}
