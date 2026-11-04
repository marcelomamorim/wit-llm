/*
 * @test
 * @summary Verifies ReferencePipeline.toArray() behavior through the public Stream API
 * @run main ReferencePipelineWitupTest
 */
import java.util.stream.Stream;

public class ReferencePipelineWitupTest {

    public static void main(String[] args) {
        testOrderedElements();
        testEmptyStream();
        testResultRuntimeType();
        testFilteredStream();
        testNullElements();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testOrderedElements() {
        Object[] result = Stream.of("a", "b", "c").toArray();
        check(result.length == 3, "expected length 3");
        check("a".equals(result[0]), "expected 'a' at index 0");
        check("b".equals(result[1]), "expected 'b' at index 1");
        check("c".equals(result[2]), "expected 'c' at index 2");
    }

    static void testEmptyStream() {
        Object[] result = Stream.empty().toArray();
        check(result.length == 0, "expected empty array");
    }

    static void testResultRuntimeType() {
        Object[] result = Stream.of(1, 2).toArray();
        check(result.getClass() == Object[].class, "expected runtime type Object[]");
    }

    static void testFilteredStream() {
        Object[] result = Stream.of(1, 2, 3, 4)
                .filter(i -> i % 2 == 0)
                .toArray();
        check(result.length == 2, "expected two even elements");
        check(((Integer) result[0]).intValue() == 2, "expected 2 at index 0");
        check(((Integer) result[1]).intValue() == 4, "expected 4 at index 1");
    }

    static void testNullElements() {
        Object[] result = Stream.of("x", null).toArray();
        check(result.length == 2, "expected length 2");
        check("x".equals(result[0]), "expected 'x' at index 0");
        check(result[1] == null, "expected null at index 1");
    }
}
