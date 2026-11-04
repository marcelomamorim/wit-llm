/*
 * @test
 * @summary Tests the map method of IntPipeline for correct behavior.
 * @run main IntPipelineWitupTest
 */
import java.util.stream.IntStream;
import java.util.function.IntUnaryOperator;

public class IntPipelineWitupTest {
    public static void main(String[] args) {
        testMapWithIdentity();
        testMapWithIncrement();
        testMapNullMapper();
    }

    static void testMapWithIdentity() {
        IntStream stream = IntStream.of(1, 2, 3);
        IntStream result = stream.map(x -> x);
        int[] expected = {1, 2, 3};
        int index = 0;
        for (int val : result.toArray()) {
            assert val == expected[index] : "Expected " + expected[index] + " but got " + val;
            index++;
        }
    }

    static void testMapWithIncrement() {
        IntStream stream = IntStream.of(1, 2, 3);
        IntStream result = stream.map(x -> x + 1);
        int[] expected = {2, 3, 4};
        int index = 0;
        for (int val : result.toArray()) {
            assert val == expected[index] : "Expected " + expected[index] + " but got " + val;
            index++;
        }
    }

    static void testMapNullMapper() {
        try {
            IntStream stream = IntStream.of(1, 2, 3);
            stream.map(null);
            assert false : "Expected NullPointerException";
        } catch (NullPointerException e) {
            // expected
        }
    }
}