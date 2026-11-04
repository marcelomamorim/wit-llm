/*
 * @test
 * @summary Test sun.nio.ch.SelectorImpl.selectNow(Consumer) method behavior
 * @run main SelectorImplWitupTest
 */

import sun.nio.ch.SelectorImpl;
import java.io.IOException;
import java.nio.channels.spi.SelectorProvider;
import java.nio.channels.SelectionKey;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;

public class SelectorImplWitupTest {

    // Minimal concrete subclass to test abstract SelectorImpl
    static class TestSelectorImpl extends SelectorImpl {
        TestSelectorImpl(SelectorProvider sp) {
            super(sp);
        }

        @Override
        protected int doSelect(Consumer<SelectionKey> action, long timeout) throws IOException {
            // For testing, simulate no keys selected and call action zero times
            if (timeout < 0) {
                throw new IOException("Negative timeout not supported");
            }
            // Simulate calling action once if timeout == 0
            if (timeout == 0) {
                // No keys, so no action invocation
                return 0;
            }
            return 0;
        }
    }

    public static void main(String[] args) throws Exception {
        SelectorProvider sp = SelectorProvider.provider();
        TestSelectorImpl selector = new TestSelectorImpl(sp);

        // Test 1: Passing null action should throw NullPointerException
        try {
            selector.selectNow(null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException npe) {
            // Expected
        }

        // Test 2: Passing a valid Consumer should return 0 (no keys selected)
        final AtomicBoolean actionCalled = new AtomicBoolean(false);
        int result = selector.selectNow(new Consumer<SelectionKey>() {
            @Override
            public void accept(SelectionKey key) {
                actionCalled.set(true);
            }
        });

        if (result != 0) {
            throw new RuntimeException("Expected selectNow to return 0 but got " + result);
        }

        if (actionCalled.get()) {
            throw new RuntimeException("Action should not have been called as no keys are selected");
        }

        // Test 3: Close selector and verify selectNow throws ClosedSelectorException
        selector.close();
        try {
            selector.selectNow(new Consumer<SelectionKey>() {
                @Override
                public void accept(SelectionKey key) {
                    // no-op
                }
            });
            throw new RuntimeException("Expected ClosedSelectorException not thrown after close");
        } catch (java.nio.channels.ClosedSelectorException cse) {
            // Expected
        }
    }
}
