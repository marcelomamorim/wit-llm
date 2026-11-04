/*
 * @test
 * @summary Verifies observable behavior of ReferencePipeline.max(Comparator) through the public Stream API
 * @run main ReferencePipelineWitupTest
 */

import java.util.Comparator;
import java.util.Optional;
import java.util.stream.Stream;

public class ReferencePipelineWitupTest {

    public static void main(String[] args) {
        testEmptyStreamReturnsEmptyOptional();
        testNaturalOrderReturnsGreatest();
        testReverseOrderReturnsSmallest();
        testSingleElement();
        testTieReturnsFirstEncounteredElement();
    }

    static void testEmptyStreamReturnsEmptyOptional() {
        Optional<String> result = Stream.<String>empty().max(Comparator.naturalOrder());
        check(result != null, "result must not be null");
        check(!result.isPresent(), "max of empty stream must be empty");
    }

    static void testNaturalOrderReturnsGreatest() {
        Optional<Integer> result = Stream.of(3, 1, 2).max(Comparator.naturalOrder());
        check(result.isPresent(), "max must be present for non-empty stream");
        check(result.get().intValue() == 3, "expected 3 but was " + result.get());
    }

    static void testReverseOrderReturnsSmallest() {
        Optional<Integer> result = Stream.of(3, 1, 2).max(Comparator.reverseOrder());
        check(result.isPresent(), "max must be present for non-empty stream");
        check(result.get().intValue() == 1, "expected 1 but was " + result.get());
    }

    static void testSingleElement() {
        Optional<String> result = Stream.of("only").max(Comparator.naturalOrder());
        check(result.isPresent(), "max must be present for single element");
        check("only".equals(result.get()), "expected 'only' but was " + result.get());
    }

    static void testTieReturnsFirstEncounteredElement() {
        Optional<String> result = Stream.of("bb", "cc")
                .max(Comparator.comparingInt(String::length));
        check(result.isPresent(), "max must be present for tied elements");
        check("bb".equals(result.get()), "expected first tied element 'bb' but was " + result.get());
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
