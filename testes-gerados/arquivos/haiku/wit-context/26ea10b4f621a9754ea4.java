/*
 * @test
 * @summary Observable behavior of ReferencePipeline mapToInt stage via public Stream API
 * @run main ReferencePipelineWitupTest
 */
import java.util.stream.Stream;

public class ReferencePipelineWitupTest {

    public static void main(String[] args) {
        testMapToIntSum();
        testMapToIntToArray();
        testMapToIntEmptyStream();
        testMapperInvokedOncePerElement();
        testNullMapperRejected();
    }

    static void testMapToIntSum() {
        int sum = Stream.of("a", "bb", "ccc").mapToInt(String::length).sum();
        check(sum == 6, "expected sum 6 but was " + sum);
    }

    static void testMapToIntToArray() {
        int[] arr = Stream.of("x", "yy", "zzz").mapToInt(String::length).toArray();
        check(arr.length == 3, "expected length 3 but was " + arr.length);
        check(arr[0] == 1 && arr[1] == 2 && arr[2] == 3, "unexpected values");
    }

    static void testMapToIntEmptyStream() {
        int sum = Stream.<String>empty().mapToInt(String::length).sum();
        check(sum == 0, "expected sum 0 for empty stream but was " + sum);
    }

    static void testMapperInvokedOncePerElement() {
        final int[] calls = new int[1];
        long count = Stream.of("a", "b", "c", "d")
                .mapToInt(s -> { calls[0]++; return s.charAt(0); })
                .count();
        check(count == 4, "expected count 4 but was " + count);
        check(calls[0] == 4, "expected mapper calls 4 but was " + calls[0]);
    }

    static void testNullMapperRejected() {
        boolean thrown = false;
        try {
            Stream.of("a").mapToInt(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "expected NullPointerException for null mapper");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError(message);
        }
    }
}
