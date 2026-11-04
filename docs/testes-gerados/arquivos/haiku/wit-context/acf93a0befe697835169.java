/*
 * @test
 * @summary Verifies mapToDouble null-mapper rejection and observable double mapping through the public Stream API
 * @run main ReferencePipelineWitupTest
 */

import java.util.stream.Stream;

public class ReferencePipelineWitupTest {

    public static void main(String[] args) {
        testNullMapperThrowsNpe();
        testNullMapperThrowsEagerly();
        testMapToDoubleValues();
        testEmptyStream();
        testMapperIsLazy();
    }

    static void testNullMapperThrowsNpe() {
        boolean thrown = false;
        try {
            Stream.of("a").mapToDouble(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("expected NullPointerException for null mapper");
        }
    }

    static void testNullMapperThrowsEagerly() {
        boolean thrown = false;
        try {
            Stream<String> s = Stream.of("a", "b");
            s.mapToDouble(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("null mapper must be rejected at call time");
        }
    }

    static void testMapToDoubleValues() {
        double sum = Stream.of("1", "22", "333").mapToDouble(String::length).sum();
        if (sum != 6.0) {
            throw new RuntimeException("expected sum 6.0 but was " + sum);
        }
    }

    static void testEmptyStream() {
        long count = Stream.<String>empty().mapToDouble(String::length).count();
        if (count != 0L) {
            throw new RuntimeException("expected count 0 but was " + count);
        }
    }

    static void testMapperIsLazy() {
        final int[] calls = new int[1];
        Stream<String> s = Stream.of("a", "b");
        Stream<String> ignored = s;
        ignored.mapToDouble(x -> {
            calls[0]++;
            return 1.0;
        });
        if (calls[0] != 0) {
            throw new RuntimeException("mapper must not run before a terminal operation");
        }
    }
}
