/*
 * @test
 * @summary Test ReferencePipeline.forEach method behavior
 * @run main ReferencePipelineWitupTest
 */

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
import java.util.function.Consumer;

public class ReferencePipelineWitupTest {

    public static void main(String[] args) {
        testForEachCollectsElements();
        testForEachWithEmptyStream();
        testForEachWithNullConsumerThrowsNPE();
    }

    private static void testForEachCollectsElements() {
        List<String> collected = new ArrayList<>();
        Stream<String> stream = Stream.of("a", "b", "c");
        stream.forEach(new Consumer<String>() {
            public void accept(String s) {
                collected.add(s);
            }
        });
        if (collected.size() != 3 || !collected.contains("a") || !collected.contains("b") || !collected.contains("c")) {
            throw new AssertionError("forEach did not process all elements correctly");
        }
    }

    private static void testForEachWithEmptyStream() {
        List<String> collected = new ArrayList<>();
        Stream<String> stream = Stream.empty();
        stream.forEach(new Consumer<String>() {
            public void accept(String s) {
                collected.add(s);
            }
        });
        if (!collected.isEmpty()) {
            throw new AssertionError("forEach on empty stream should not call accept");
        }
    }

    private static void testForEachWithNullConsumerThrowsNPE() {
        Stream<String> stream = Stream.of("x");
        try {
            stream.forEach(null);
            throw new AssertionError("forEach with null consumer should throw NullPointerException");
        } catch (NullPointerException expected) {
            // expected
        }
    }
}
