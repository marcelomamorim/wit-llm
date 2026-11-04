/*
 * @test
 * @summary Test StackWalker.forEach with a simple Consumer
 * @run main StackWalkerWitupTest
 */

import java.lang.StackWalker;
import java.lang.StackWalker.StackFrame;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

public class StackWalkerWitupTest {
    public static void main(String[] args) {
        testForEachWithValidConsumer();
        testForEachWithNullConsumer();
    }

    private static void testForEachWithValidConsumer() {
        final AtomicInteger count = new AtomicInteger(0);
        StackWalker walker = StackWalker.getInstance();
        Consumer<StackFrame> consumer = new Consumer<StackFrame>() {
            public void accept(StackFrame frame) {
                Objects.requireNonNull(frame);
                count.incrementAndGet();
            }
        };
        walker.forEach(consumer);
        if (count.get() == 0) {
            throw new AssertionError("Expected at least one stack frame to be consumed");
        }
    }

    private static void testForEachWithNullConsumer() {
        StackWalker walker = StackWalker.getInstance();
        try {
            walker.forEach(null);
            throw new AssertionError("Expected NullPointerException when passing null consumer");
        } catch (NullPointerException expected) {
            // expected
        }
    }
}
