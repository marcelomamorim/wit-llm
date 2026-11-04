/*
 * @test
 * @summary Test ReferencePipeline.forEachOrdered method behavior
 * @run main ReferencePipelineWitupTest
 */

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
import java.util.function.Consumer;

public class ReferencePipelineWitupTest {
    public static void main(String[] args) {
        testForEachOrderedWithNonEmptyStream();
        testForEachOrderedWithEmptyStream();
        testForEachOrderedWithNullConsumer();
    }

    private static void testForEachOrderedWithNonEmptyStream() {
        List<String> collected = new ArrayList<>();
        Stream<String> stream = Stream.of("a", "b", "c");
        stream.forEachOrdered(new Consumer<String>() {
            public void accept(String s) {
                collected.add(s);
            }
        });
        if (collected.size() != 3) {
            throw new AssertionError("Expected 3 elements collected, got " + collected.size());
        }
        if (!"a".equals(collected.get(0)) || !"b".equals(collected.get(1)) || !"c".equals(collected.get(2))) {
            throw new AssertionError("Elements collected out of order: " + collected);
        }
    }

    private static void testForEachOrderedWithEmptyStream() {
        List<String> collected = new ArrayList<>();
        Stream<String> stream = Stream.empty();
        stream.forEachOrdered(new Consumer<String>() {
            public void accept(String s) {
                collected.add(s);
            }
        });
        if (!collected.isEmpty()) {
            throw new AssertionError("Expected no elements collected for empty stream");
        }
    }

    private static void testForEachOrderedWithNullConsumer() {
        Stream<String> stream = Stream.of("x");
        try {
            stream.forEachOrdered(null);
            throw new AssertionError("Expected NullPointerException for null Consumer");
        } catch (NullPointerException expected) {
            // expected
        }
    }
}
