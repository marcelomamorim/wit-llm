/*
 * @test
 * @summary Public-path checks for the spliterator forEachRemaining contract of Stream.builder() streams (Streams.StreamBuilderImpl)
 * @run main StreamsWitupTest
 */

import java.util.ArrayList;
import java.util.List;
import java.util.Spliterator;
import java.util.stream.Stream;

public class StreamsWitupTest {

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testNullActionThrowsNpe() {
        Spliterator<String> sp = Stream.<String>builder().add("a").build().spliterator();
        boolean thrown = false;
        try {
            sp.forEachRemaining(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "forEachRemaining(null) must throw NullPointerException");
    }

    static void testSingleElementConsumedOnce() {
        Spliterator<String> sp = Stream.<String>builder().add("a").build().spliterator();
        List<String> seen = new ArrayList<>();
        sp.forEachRemaining(seen::add);
        check(seen.size() == 1 && seen.get(0).equals("a"), "single element must be delivered once: " + seen);
    }

    static void testSecondCallIsNoOp() {
        Spliterator<String> sp = Stream.<String>builder().add("a").build().spliterator();
        List<String> seen = new ArrayList<>();
        sp.forEachRemaining(seen::add);
        sp.forEachRemaining(seen::add);
        check(seen.size() == 1, "second forEachRemaining must not redeliver the element: " + seen);
    }

    static void testEmptyBuilderDeliversNothing() {
        Spliterator<String> sp = Stream.<String>builder().build().spliterator();
        List<String> seen = new ArrayList<>();
        sp.forEachRemaining(seen::add);
        check(seen.isEmpty(), "empty builder must deliver no elements: " + seen);
    }

    public static void main(String[] args) {
        testNullActionThrowsNpe();
        testSingleElementConsumedOnce();
        testSecondCallIsNoOp();
        testEmptyBuilderDeliversNothing();
    }
}
