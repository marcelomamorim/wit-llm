/*
 * @test
 * @summary Test sun.nio.ch.SelectorImpl.select(Consumer<SelectionKey>) for NPE and normal behavior
 * @run main SelectorImplWitupTest
 */

import sun.nio.ch.SelectorImpl;
import java.io.IOException;
import java.nio.channels.ClosedSelectorException;
import java.nio.channels.SelectionKey;
import java.nio.channels.spi.SelectorProvider;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;

public class SelectorImplWitupTest {

    // Minimal concrete subclass to allow instantiation
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

        // Test 1: Passing null Consumer should throw NullPointerException
        boolean npeThrown = false;
        try {
            selector.select(null);
        } catch (NullPointerException npe) {
            npeThrown = true;
        }
        if (!npeThrown) {
            throw new AssertionError("Expected NullPointerException when passing null Consumer");
        }

        // Test 2: Passing a valid Consumer should return 0 (no keys selected)
        final AtomicBoolean consumerCalled = new AtomicBoolean(false);
        int result = selector.select(new Consumer<SelectionKey>() {
            @Override
            public void accept(SelectionKey key) {
                consumerCalled.set(true);
            }
        });

        if (result != 0) {
            throw new AssertionError("Expected select to return 0 when no keys are selected, got " + result);
        }

        if (consumerCalled.get()) {
            throw new AssertionError("Consumer should not be called when no keys are selected");
        }

        // Test 3: After close, select should throw ClosedSelectorException
        selector.close();
        boolean closedSelectorExceptionThrown = false;
        try {
            selector.select(new Consumer<SelectionKey>() {
                @Override
                public void accept(SelectionKey key) {
                }
            });
        } catch (ClosedSelectorException cse) {
            closedSelectorExceptionThrown = true;
        }
        if (!closedSelectorExceptionThrown) {
            throw new AssertionError("Expected ClosedSelectorException after selector is closed");
        }
    }
}
