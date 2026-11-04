/*
 * @test
 * @summary Test for Stream.iterate method behavior
 * @run main StreamWitupTest
 */
import java.util.stream.Stream;
import java.util.function.UnaryOperator;

public class StreamWitupTest {
    public static void main(String[] args) {
        testIterateBasic();
        testIterateWithNullSeed();
        testIterateWithNullFunction();
        testIterateMultipleSteps();
    }

    static void testIterateBasic() {
        Stream<Integer> stream = Stream.iterate(0, n -> n + 2);
        Integer[] result = stream.limit(5).toArray(Integer[]::new);
        assert result.length == 5 : "Expected 5 elements";
        for (int i = 0; i < result.length; i++) {
            assert result[i] == i * 2 : "Element mismatch at index " + i;
        }
    }

    static void testIterateWithNullSeed() {
        try {
            Stream<String> stream = Stream.iterate(null, s -> s + "a");
            String[] result = stream.limit(1).toArray(String[]::new);
            assert result[0] == null : "Expected null seed";
        } catch (NullPointerException e) {
            // Expected if null is not allowed
        }
    }

    static void testIterateWithNullFunction() {
        try {
            Stream<Integer> stream = Stream.iterate(0, null);
            stream.limit(1).toArray();
            assert false : "Expected NullPointerException";
        } catch (NullPointerException e) {
            // Expected
        }
    }

    static void testIterateMultipleSteps() {
        Stream<Integer> stream = Stream.iterate(1, n -> n * 3);
        Integer[] result = stream.limit(4).toArray(Integer[]::new);
        Integer[] expected = {1, 3, 9, 27};
        assert java.util.Arrays.equals(result, expected) : "Sequence mismatch";
    }
}
