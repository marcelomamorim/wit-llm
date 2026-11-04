/*
 * @test
 * @summary Witup-derived checks for AbstractPipeline.onClose observed through the public Stream API
 * @run main AbstractPipelineWitupTest
 */

import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

public class AbstractPipelineWitupTest {

    public static void main(String[] args) {
        testNullCloseHandlerThrowsNPE();
        testConsumedStreamThrowsISE();
        testOnCloseReturnsSameStream();
        testCloseHandlerIsInvokedOnClose();
        testMultipleCloseHandlersAreAllInvoked();
    }

    static void testNullCloseHandlerThrowsNPE() {
        Stream<String> s = Stream.of("a");
        try {
            s.onClose(null);
        } catch (NullPointerException e) {
            return;
        }
        throw new AssertionError("expected NullPointerException for null close handler");
    }

    static void testConsumedStreamThrowsISE() {
        Stream<String> s = Stream.of("a");
        Stream<String> mapped = s.map(x -> x);
        try {
            s.onClose(() -> { });
        } catch (IllegalStateException e) {
            mapped.close();
            return;
        }
        throw new AssertionError("expected IllegalStateException on linked stream");
    }

    static void testOnCloseReturnsSameStream() {
        Stream<String> s = Stream.of("a");
        Stream<String> r = s.onClose(() -> { });
        if (r != s) {
            throw new AssertionError("onClose should return the same stream instance");
        }
        s.close();
    }

    static void testCloseHandlerIsInvokedOnClose() {
        AtomicInteger count = new AtomicInteger();
        Stream.of("a").onClose(() -> count.incrementAndGet()).close();
        if (count.get() != 1) {
            throw new AssertionError("close handler should run exactly once, ran " + count.get());
        }
    }

    static void testMultipleCloseHandlersAreAllInvoked() {
        AtomicInteger count = new AtomicInteger();
        Stream.of("a")
              .onClose(() -> count.incrementAndGet())
              .onClose(() -> count.incrementAndGet())
              .close();
        if (count.get() != 2) {
            throw new AssertionError("both close handlers should run, ran " + count.get());
        }
    }
}
